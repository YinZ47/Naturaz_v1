# Deployment Guide

This guide covers deploying Naturaz to production and staging environments.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Environment Setup](#environment-setup)
- [Database Setup](#database-setup)
- [Kubernetes Deployment](#kubernetes-deployment)
- [CI/CD Pipeline](#cicd-pipeline)
- [Monitoring Setup](#monitoring-setup)
- [Backup Strategy](#backup-strategy)
- [Scaling](#scaling)
- [Rollback Procedures](#rollback-procedures)

## Prerequisites

### Required Tools
- kubectl (Kubernetes CLI)
- Docker
- AWS CLI
- Terraform
- Helm 3

### Required Access
- AWS account with appropriate permissions
- Container registry access (ECR)
- Kubernetes cluster access
- Domain name and DNS management

## Environment Setup

### 1. Cloud Infrastructure

#### AWS Resources Needed
- **EKS Cluster**: Kubernetes orchestration
- **RDS PostgreSQL**: Database
- **DocumentDB**: MongoDB-compatible database
- **ElastiCache**: Redis caching
- **S3**: Object storage
- **CloudFront**: CDN
- **Route53**: DNS management
- **ECR**: Container registry
- **ALB**: Load balancing

#### Create Infrastructure with Terraform

```bash
cd infrastructure/terraform/environments/prod

# Initialize Terraform
terraform init

# Review planned changes
terraform plan

# Apply infrastructure
terraform apply
```

### 2. Environment Variables

Create environment-specific configurations:

**Production (.env.production)**
```bash
NODE_ENV=production
API_URL=https://api.naturaz.com.bd
DATABASE_URL=postgresql://user:pass@prod-db.aws.com:5432/naturaz
REDIS_URL=redis://prod-redis.aws.com:6379
MONGODB_URI=mongodb://prod-mongo.aws.com:27017/naturaz
AWS_S3_BUCKET=naturaz-prod-uploads
```

**Staging (.env.staging)**
```bash
NODE_ENV=staging
API_URL=https://api-staging.naturaz.com.bd
DATABASE_URL=postgresql://user:pass@staging-db.aws.com:5432/naturaz
# ... other staging configs
```

## Database Setup

### 1. PostgreSQL (RDS)

```bash
# Create database
createdb naturaz_production

# Run migrations
npm run migrate:prod

# Seed initial data (if needed)
npm run seed:prod
```

### 2. MongoDB (DocumentDB)

```bash
# Connect to DocumentDB
mongo --ssl --host prod-mongo.aws.com:27017 \
  --username admin --password <password>

# Create database and collections
use naturaz
db.createCollection("products")
db.createCollection("reviews")
```

### 3. Redis (ElastiCache)

No manual setup required for managed Redis.

## Kubernetes Deployment

### 1. Create Kubernetes Secrets

```bash
# Database credentials
kubectl create secret generic db-credentials \
  --from-literal=postgres-url=postgresql://... \
  --from-literal=mongodb-uri=mongodb://... \
  --from-literal=redis-url=redis://...

# API keys
kubectl create secret generic api-keys \
  --from-literal=jwt-secret=... \
  --from-literal=aws-access-key=... \
  --from-literal=aws-secret-key=...

# Payment gateway credentials
kubectl create secret generic payment-keys \
  --from-literal=bkash-app-key=... \
  --from-literal=nagad-merchant-id=...
```

### 2. Deploy Services

```bash
# Deploy all services
kubectl apply -f infrastructure/kubernetes/

# Or deploy individual services
kubectl apply -f infrastructure/kubernetes/deployments/user-service.yaml
kubectl apply -f infrastructure/kubernetes/services/user-service.yaml
```

### 3. Setup Ingress

```bash
# Install nginx ingress controller
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install nginx-ingress ingress-nginx/ingress-nginx

# Apply ingress rules
kubectl apply -f infrastructure/kubernetes/ingress/
```

## Docker Build & Push

### 1. Build Images

```bash
# Build all services
./scripts/build-all.sh

# Or build individual service
cd backend/services/user-service
docker build -t naturaz/user-service:v1.0.0 .
```

### 2. Push to Registry

```bash
# Login to ECR
aws ecr get-login-password --region ap-south-1 | \
  docker login --username AWS --password-stdin <account-id>.dkr.ecr.ap-south-1.amazonaws.com

# Tag and push
docker tag naturaz/user-service:v1.0.0 \
  <account-id>.dkr.ecr.ap-south-1.amazonaws.com/user-service:v1.0.0

docker push <account-id>.dkr.ecr.ap-south-1.amazonaws.com/user-service:v1.0.0
```

## CI/CD Pipeline

### GitHub Actions Workflow

**.github/workflows/deploy-production.yml**

```yaml
name: Deploy to Production

on:
  push:
    branches: [main]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Configure AWS credentials
        uses: aws-actions/configure-aws-credentials@v2
        with:
          aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
          aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
          aws-region: ap-south-1
      
      - name: Login to ECR
        run: |
          aws ecr get-login-password | docker login --username AWS --password-stdin $ECR_REGISTRY
      
      - name: Build and push images
        run: |
          ./scripts/build-and-push.sh
      
      - name: Deploy to Kubernetes
        run: |
          kubectl apply -f infrastructure/kubernetes/
      
      - name: Verify deployment
        run: |
          kubectl rollout status deployment/user-service
```

## Monitoring Setup

### 1. Prometheus

```bash
# Install Prometheus with Helm
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm install prometheus prometheus-community/prometheus

# Apply custom configuration
kubectl apply -f infrastructure/monitoring/prometheus/
```

### 2. Grafana

```bash
# Install Grafana
helm repo add grafana https://grafana.github.io/helm-charts
helm install grafana grafana/grafana

# Get admin password
kubectl get secret grafana -o jsonpath="{.data.admin-password}" | base64 --decode

# Import dashboards
kubectl apply -f infrastructure/monitoring/grafana/dashboards/
```

### 3. ELK Stack

```bash
# Install Elasticsearch
helm repo add elastic https://helm.elastic.co
helm install elasticsearch elastic/elasticsearch

# Install Logstash
helm install logstash elastic/logstash

# Install Kibana
helm install kibana elastic/kibana
```

## SSL/TLS Setup

### 1. Install cert-manager

```bash
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.13.0/cert-manager.yaml
```

### 2. Create Certificate

```yaml
# infrastructure/kubernetes/certificates/naturaz-cert.yaml
apiVersion: cert-manager.io/v1
kind: Certificate
metadata:
  name: naturaz-tls
spec:
  secretName: naturaz-tls-secret
  issuerRef:
    name: letsencrypt-prod
    kind: ClusterIssuer
  dnsNames:
    - naturaz.com.bd
    - www.naturaz.com.bd
    - api.naturaz.com.bd
```

## Backup Strategy

### 1. Database Backups

**PostgreSQL (RDS)**
```bash
# Automated backups enabled in RDS
# Manual snapshot
aws rds create-db-snapshot \
  --db-instance-identifier naturaz-prod \
  --db-snapshot-identifier naturaz-backup-$(date +%Y%m%d)
```

**MongoDB (DocumentDB)**
```bash
# Automated backups via DocumentDB
# Manual backup
mongodump --uri="mongodb://..." --out=/backup/$(date +%Y%m%d)
aws s3 cp /backup/$(date +%Y%m%d) s3://naturaz-backups/mongodb/ --recursive
```

### 2. File Backups

```bash
# Backup S3 bucket to another region
aws s3 sync s3://naturaz-uploads s3://naturaz-uploads-backup --region ap-south-1
```

### 3. Kubernetes Backups

```bash
# Install Velero for K8s backups
helm repo add vmware-tanzu https://vmware-tanzu.github.io/helm-charts
helm install velero vmware-tanzu/velero \
  --set configuration.provider=aws \
  --set configuration.backupStorageLocation.bucket=naturaz-k8s-backups

# Create backup
velero backup create naturaz-backup --include-namespaces naturaz
```

## Scaling

### 1. Horizontal Pod Autoscaling

```yaml
# infrastructure/kubernetes/hpa/user-service-hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: user-service-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: user-service
  minReplicas: 2
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
```

### 2. Cluster Autoscaling

```bash
# Enable cluster autoscaler on EKS
kubectl apply -f infrastructure/kubernetes/cluster-autoscaler.yaml
```

### 3. Database Scaling

**Read Replicas**
```bash
# Create read replica in RDS
aws rds create-db-instance-read-replica \
  --db-instance-identifier naturaz-prod-read-1 \
  --source-db-instance-identifier naturaz-prod
```

## Health Checks

### Kubernetes Liveness & Readiness Probes

```yaml
livenessProbe:
  httpGet:
    path: /health
    port: 3000
  initialDelaySeconds: 30
  periodSeconds: 10

readinessProbe:
  httpGet:
    path: /ready
    port: 3000
  initialDelaySeconds: 10
  periodSeconds: 5
```

## Rollback Procedures

### 1. Kubernetes Rollback

```bash
# Check rollout history
kubectl rollout history deployment/user-service

# Rollback to previous version
kubectl rollout undo deployment/user-service

# Rollback to specific revision
kubectl rollout undo deployment/user-service --to-revision=2
```

### 2. Database Rollback

```bash
# Restore from RDS snapshot
aws rds restore-db-instance-from-db-snapshot \
  --db-instance-identifier naturaz-prod-restored \
  --db-snapshot-identifier naturaz-backup-20240101
```

## Zero-Downtime Deployment

### Blue-Green Deployment

```bash
# Deploy to green environment
kubectl apply -f infrastructure/kubernetes/deployments/green/

# Test green environment
curl https://green.naturaz.com.bd/health

# Switch traffic to green
kubectl patch service api-gateway -p '{"spec":{"selector":{"version":"green"}}}'

# Monitor and rollback if needed
```

### Canary Deployment

```yaml
# Use Istio or Flagger for canary deployments
apiVersion: flagger.app/v1beta1
kind: Canary
metadata:
  name: user-service
spec:
  targetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: user-service
  progressDeadlineSeconds: 60
  service:
    port: 3000
  analysis:
    interval: 1m
    threshold: 5
    maxWeight: 50
    stepWeight: 10
```

## Performance Optimization

### 1. CDN Configuration

```bash
# Configure CloudFront for static assets
aws cloudfront create-distribution \
  --origin-domain-name naturaz-uploads.s3.amazonaws.com \
  --default-root-object index.html
```

### 2. Caching Strategy

- **Browser Cache**: 1 year for static assets
- **CDN Cache**: 1 week for images
- **API Cache**: Redis with TTL based on endpoint

### 3. Database Optimization

- Connection pooling (max 100 connections)
- Query optimization and indexing
- Read replicas for read-heavy operations

## Security Checklist

- [ ] SSL/TLS certificates configured
- [ ] Secrets stored in Kubernetes secrets or AWS Secrets Manager
- [ ] Network policies applied
- [ ] WAF (Web Application Firewall) enabled
- [ ] DDoS protection configured
- [ ] Security groups properly configured
- [ ] Regular security updates applied
- [ ] Penetration testing completed

## Post-Deployment Verification

```bash
# Check all pods are running
kubectl get pods -n naturaz

# Check services
kubectl get services -n naturaz

# Check ingress
kubectl get ingress -n naturaz

# Test API endpoints
curl https://api.naturaz.com.bd/health
curl https://api.naturaz.com.bd/api/v1/products

# Check logs
kubectl logs -f deployment/user-service

# Monitor metrics
kubectl top pods
kubectl top nodes
```

## Troubleshooting

### Common Issues

**Pods not starting**
```bash
kubectl describe pod <pod-name>
kubectl logs <pod-name>
```

**Service not accessible**
```bash
kubectl describe service <service-name>
kubectl get endpoints <service-name>
```

**Database connection issues**
```bash
# Test database connectivity from pod
kubectl run -it --rm debug --image=postgres:15 --restart=Never -- psql $DATABASE_URL
```

## Support

For deployment issues:
- **DevOps Team**: devops@naturaz.com.bd
- **Documentation**: https://docs.naturaz.com.bd/deployment
- **Slack**: #deployment channel
