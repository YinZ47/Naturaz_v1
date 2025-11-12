# Naturaz Project Structure

## Overview
This document describes the complete folder structure for the Naturaz e-commerce platform.

## Root Structure
```
naturaz_v1/
├── backend/              # Backend microservices
├── frontend/             # Frontend applications
├── mobile/               # Mobile applications
├── infrastructure/       # DevOps and infrastructure
├── docs/                 # Documentation
├── tests/                # Integration and E2E tests
├── .github/              # GitHub workflows and templates
├── ARCHITECTURE.md       # System architecture overview
├── DATABASE_SCHEMA.md    # Database design
├── API_SPECIFICATION.md  # API documentation
└── README.md             # Project overview
```

## Backend Structure

### Microservices Architecture
Each service follows a consistent structure:

```
services/{service-name}/
├── src/
│   ├── controllers/      # Request handlers
│   ├── models/           # Database models
│   ├── routes/           # API routes
│   ├── middlewares/      # Custom middlewares
│   ├── utils/            # Utility functions
│   ├── config/           # Configuration files
│   └── index.ts          # Entry point
├── tests/                # Service tests
├── Dockerfile            # Docker configuration
├── package.json          # Dependencies
└── README.md             # Service documentation
```

### Services List
1. **user-service**: User authentication and management
2. **product-service**: Product catalog management
3. **order-service**: Order processing and management
4. **payment-service**: Payment gateway integration
5. **inventory-service**: Stock and warehouse management
6. **cart-service**: Shopping cart operations
7. **notification-service**: Email, SMS, push notifications
8. **review-service**: Product reviews and ratings
9. **vendor-service**: Vendor/seller management
10. **shipping-service**: Shipping and delivery
11. **analytics-service**: Data analytics and reporting
12. **search-service**: Elasticsearch integration
13. **recommendation-service**: AI-based recommendations
14. **chat-service**: Customer support and chat

### Supporting Directories
- **api-gateway**: API Gateway configuration
- **shared**: Shared models, types, and utilities
- **scripts**: Database migrations, seed data, deployment scripts

## Frontend Structure

### Web Application
```
frontend/web/
├── src/
│   ├── components/
│   │   ├── common/           # Reusable components
│   │   ├── layout/           # Layout components
│   │   ├── products/         # Product-related components
│   │   ├── cart/             # Cart components
│   │   ├── checkout/         # Checkout flow
│   │   ├── user/             # User profile, auth
│   │   ├── vendor/           # Vendor dashboard
│   │   └── eco-features/     # Nature-themed features
│   ├── pages/                # Next.js pages
│   ├── hooks/                # Custom React hooks
│   ├── services/             # API service layer
│   ├── utils/                # Utility functions
│   ├── store/                # State management
│   ├── styles/               # Global styles, themes
│   ├── assets/               # Images, icons, fonts
│   └── config/               # Configuration
├── public/                   # Static files
├── tests/                    # Component tests
├── package.json
├── next.config.js
└── tailwind.config.js
```

### Admin Dashboard
```
frontend/admin/
├── src/
│   ├── components/
│   │   ├── common/
│   │   ├── layout/
│   │   ├── dashboard/        # Dashboard widgets
│   │   ├── products/         # Product management
│   │   ├── orders/           # Order management
│   │   ├── users/            # User management
│   │   ├── vendors/          # Vendor management
│   │   └── analytics/        # Analytics views
│   ├── pages/
│   ├── hooks/
│   ├── services/
│   ├── utils/
│   ├── store/
│   └── styles/
├── public/
└── tests/
```

## Mobile Structure

### React Native App
```
mobile/
├── src/
│   ├── components/
│   │   ├── common/           # Reusable components
│   │   └── screens/          # Screen components
│   ├── navigation/           # Navigation configuration
│   ├── services/             # API services
│   ├── hooks/                # Custom hooks
│   ├── utils/                # Utilities
│   ├── store/                # State management
│   ├── assets/               # Images, fonts
│   └── config/               # App configuration
├── android/                  # Android native code
├── ios/                      # iOS native code
├── tests/                    # Tests
└── package.json
```

## Infrastructure Structure

### DevOps Configuration
```
infrastructure/
├── kubernetes/
│   ├── deployments/          # K8s deployments
│   ├── services/             # K8s services
│   ├── ingress/              # Ingress rules
│   ├── configmaps/           # Configuration maps
│   └── secrets/              # Secrets (encrypted)
├── docker/                   # Dockerfiles
├── terraform/                # Infrastructure as Code
│   ├── modules/              # Reusable modules
│   └── environments/         # Dev, staging, prod
├── monitoring/
│   ├── prometheus/           # Prometheus config
│   ├── grafana/              # Grafana dashboards
│   └── elk/                  # ELK stack config
└── ci-cd/
    ├── github-actions/       # GitHub Actions workflows
    └── scripts/              # Deployment scripts
```

## Documentation Structure

```
docs/
├── api/                      # API documentation
│   ├── user/
│   ├── product/
│   ├── order/
│   ├── payment/
│   └── vendor/
├── architecture/             # Architecture docs
│   ├── diagrams/             # Architecture diagrams
│   └── decisions/            # ADRs (Architecture Decision Records)
├── deployment/               # Deployment guides
├── user-guides/              # End-user documentation
└── developer-guides/         # Developer documentation
```

## Tests Structure

```
tests/
├── integration/              # Integration tests
├── e2e/                      # End-to-end tests
├── performance/              # Performance tests
└── security/                 # Security tests
```

## Key Files

### Root Level Configuration
- **.gitignore**: Git ignore patterns
- **.env.example**: Environment variables template
- **docker-compose.yml**: Local development setup
- **.eslintrc.js**: ESLint configuration
- **.prettierrc**: Prettier configuration
- **package.json**: Root package file (monorepo)
- **lerna.json** or **pnpm-workspace.yaml**: Monorepo configuration

### Backend Service Files
Each microservice contains:
- **Dockerfile**: Container definition
- **package.json**: Node.js dependencies
- **tsconfig.json**: TypeScript configuration
- **.env.example**: Environment variables
- **README.md**: Service documentation

### Frontend Application Files
- **package.json**: Dependencies
- **tsconfig.json**: TypeScript configuration
- **next.config.js**: Next.js configuration
- **tailwind.config.js**: Tailwind CSS configuration
- **.env.local.example**: Environment variables

## Nature-Themed Features Directories

### Eco-Features Components
Located in `frontend/web/src/components/eco-features/`:
- **CarbonCalculator**: Calculate carbon footprint
- **TreePlantingTracker**: Track tree planting progress
- **EcoScore**: Display product eco-ratings
- **LocalProductBadge**: Highlight local products
- **SustainablePackaging**: Packaging options

### Eco-Services
Located in various backend services:
- **product-service**: Eco-rating calculations
- **order-service**: Tree planting integration
- **vendor-service**: Eco-certification management
- **analytics-service**: Sustainability metrics

## Development Workflow

### Local Development
1. Clone repository
2. Install dependencies: `npm install` or `pnpm install`
3. Setup environment variables
4. Run docker-compose for local services
5. Start development servers

### Service Communication
- Services communicate via REST APIs and message queues
- API Gateway routes external requests
- Internal services use service mesh (Istio) or direct calls

### Database Organization
Each service can have its own database or share with related services:
- **User DB**: User data, authentication
- **Product DB**: Product catalog, categories
- **Order DB**: Orders, transactions
- **Analytics DB**: Metrics, logs

## Comparison with Leading E-commerce Platforms

### Feature Parity with Daraz/Amazon
✅ Multi-vendor marketplace
✅ Product catalog and search
✅ Shopping cart and wishlist
✅ Secure checkout and payments
✅ Order tracking
✅ User reviews and ratings
✅ Mobile applications
✅ Admin dashboard
✅ Vendor dashboard
✅ Analytics and reporting

### Unique Naturaz Features
🌿 Eco-score for products
🌿 Carbon footprint calculator
🌿 Tree planting program
🌿 Local artisan marketplace
🌿 Sustainable packaging options
🌿 Nature-inspired UI/UX
🌿 Bangladesh-specific payment methods
🌿 Regional language support (Bangla/English)

## Next Steps

1. **Phase 1**: Setup development environment
2. **Phase 2**: Implement user and product services
3. **Phase 3**: Build frontend applications
4. **Phase 4**: Integrate payment and shipping
5. **Phase 5**: Deploy to staging environment
6. **Phase 6**: Testing and optimization
7. **Phase 7**: Production launch
