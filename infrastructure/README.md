# Infrastructure

DevOps and infrastructure configuration for Naturaz.

## Overview

This directory contains all infrastructure-as-code, containerization, orchestration, and monitoring configurations.

## Directory Structure

```
infrastructure/
├── kubernetes/           # Kubernetes manifests
├── docker/              # Dockerfiles
├── terraform/           # Infrastructure as Code
├── monitoring/          # Monitoring configurations
└── ci-cd/              # CI/CD pipelines
```

## Kubernetes

### Deployments
- Service deployments
- ConfigMaps
- Secrets management
- Ingress rules

### Usage
```bash
# Apply all configurations
kubectl apply -f kubernetes/

# Apply specific service
kubectl apply -f kubernetes/deployments/user-service.yaml
```

## Docker

Dockerfiles for all services following best practices:
- Multi-stage builds
- Minimal base images (Alpine)
- Non-root user
- Health checks

## Terraform

Infrastructure provisioning for:
- AWS EKS cluster
- RDS databases
- ElastiCache Redis
- S3 buckets
- VPC and networking
- IAM roles and policies

### Usage
```bash
cd terraform/environments/prod
terraform init
terraform plan
terraform apply
```

## Monitoring

### Prometheus
- Metrics collection
- Alerting rules
- Service discovery

### Grafana
- Dashboards
- Visualization
- Alerting

### ELK Stack
- Log aggregation
- Log analysis
- Kibana dashboards

## CI/CD

GitHub Actions workflows for:
- Automated testing
- Docker image building
- Deployment to Kubernetes
- Database migrations

## Security

- Secrets management with Kubernetes Secrets
- Network policies
- RBAC configuration
- Pod security policies

## Scaling

- Horizontal Pod Autoscaler (HPA)
- Cluster Autoscaler
- Database read replicas
- CDN configuration
