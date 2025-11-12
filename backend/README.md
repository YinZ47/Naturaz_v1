# Backend Services

This directory contains all microservices for the Naturaz platform.

## Services Overview

### Core Services

#### 1. User Service
- **Port**: 3001
- **Purpose**: User authentication, authorization, and profile management
- **Database**: PostgreSQL
- **Key Features**:
  - User registration and login
  - JWT authentication
  - Social login (Google, Facebook)
  - Profile management
  - Address management

#### 2. Product Service
- **Port**: 3002
- **Purpose**: Product catalog and management
- **Database**: PostgreSQL + MongoDB
- **Key Features**:
  - Product CRUD operations
  - Category management
  - Product variants
  - Eco-score calculation
  - Image management

#### 3. Order Service
- **Port**: 3003
- **Purpose**: Order processing and management
- **Database**: PostgreSQL
- **Key Features**:
  - Order creation
  - Order tracking
  - Order status updates
  - Order history
  - Tree planting integration

#### 4. Payment Service
- **Port**: 3004
- **Purpose**: Payment processing
- **Database**: PostgreSQL
- **Key Features**:
  - bKash integration
  - Nagad integration
  - Rocket integration
  - SSL Commerz integration
  - Transaction management
  - Refund processing

#### 5. Inventory Service
- **Port**: 3005
- **Purpose**: Stock and warehouse management
- **Database**: PostgreSQL
- **Key Features**:
  - Stock tracking
  - Warehouse management
  - Low stock alerts
  - Inventory updates

#### 6. Cart Service
- **Port**: 3006
- **Purpose**: Shopping cart operations
- **Database**: Redis + MongoDB
- **Key Features**:
  - Add/remove items
  - Update quantities
  - Cart persistence
  - Cart sharing

#### 7. Notification Service
- **Port**: 3007
- **Purpose**: Notifications delivery
- **Database**: MongoDB
- **Key Features**:
  - Email notifications
  - SMS notifications
  - Push notifications
  - Notification templates

#### 8. Review Service
- **Port**: 3008
- **Purpose**: Product reviews and ratings
- **Database**: PostgreSQL + MongoDB
- **Key Features**:
  - Review submission
  - Rating calculation
  - Review moderation
  - Verified purchase badges

#### 9. Vendor Service
- **Port**: 3009
- **Purpose**: Vendor/seller management
- **Database**: PostgreSQL
- **Key Features**:
  - Vendor registration
  - Eco-certification
  - Vendor dashboard data
  - Sales analytics

#### 10. Shipping Service
- **Port**: 3010
- **Purpose**: Shipping and delivery
- **Database**: PostgreSQL
- **Key Features**:
  - Shipping partner integration
  - Tracking numbers
  - Delivery estimation
  - Shipping cost calculation

#### 11. Analytics Service
- **Port**: 3011
- **Purpose**: Data analytics and reporting
- **Database**: MongoDB + Elasticsearch
- **Key Features**:
  - User behavior tracking
  - Sales analytics
  - Product performance
  - Dashboard metrics

#### 12. Search Service
- **Port**: 3012
- **Purpose**: Product search
- **Database**: Elasticsearch
- **Key Features**:
  - Full-text search
  - Filters and facets
  - Auto-suggestions
  - Search ranking

#### 13. Recommendation Service
- **Port**: 3013
- **Purpose**: AI-powered recommendations
- **Database**: MongoDB
- **Key Features**:
  - Personalized recommendations
  - Similar products
  - Trending products
  - Collaborative filtering

#### 14. Chat Service
- **Port**: 3014
- **Purpose**: Customer support
- **Database**: MongoDB
- **Key Features**:
  - Live chat
  - Chatbot
  - Ticket management
  - Chat history

### Infrastructure Components

#### API Gateway
- **Port**: 3000
- **Purpose**: Single entry point for all services
- **Key Features**:
  - Request routing
  - Authentication
  - Rate limiting
  - Load balancing
  - API versioning

## Running Services Locally

### Start All Services
```bash
# Using docker-compose
docker-compose up -d

# Or start individual services
cd services/user-service
npm run dev
```

### Development Workflow

1. **Install dependencies**
```bash
npm install
```

2. **Setup environment**
```bash
cp .env.example .env
# Edit .env with your configuration
```

3. **Run migrations**
```bash
npm run migrate
```

4. **Start service**
```bash
npm run dev
```

## Service Communication

Services communicate through:
- **Synchronous**: REST API calls via API Gateway
- **Asynchronous**: RabbitMQ message queue
- **Cache**: Redis for shared data

## Technology Stack

- **Runtime**: Node.js 20
- **Language**: TypeScript
- **Frameworks**: Express.js, NestJS
- **ORM**: Prisma (PostgreSQL), Mongoose (MongoDB)
- **Validation**: Joi / Zod
- **Testing**: Jest, Supertest
- **Documentation**: Swagger / OpenAPI

## Project Structure (per service)

```
service-name/
├── src/
│   ├── controllers/      # Request handlers
│   ├── models/           # Database models
│   ├── routes/           # API routes
│   ├── middlewares/      # Custom middlewares
│   ├── utils/            # Utility functions
│   ├── config/           # Configuration
│   ├── validators/       # Input validation
│   └── index.ts          # Entry point
├── tests/
│   ├── unit/
│   └── integration/
├── Dockerfile
├── package.json
├── tsconfig.json
└── README.md
```

## Common Middleware

- **Authentication**: JWT verification
- **Authorization**: Role-based access control
- **Validation**: Request validation
- **Error Handling**: Centralized error handler
- **Logging**: Winston logger
- **Rate Limiting**: Express rate limit

## Database Conventions

- Use UUIDs for primary keys
- Snake_case for database columns
- camelCase for TypeScript/JavaScript
- Timestamps on all tables (created_at, updated_at)
- Soft deletes where applicable

## API Conventions

- RESTful endpoints
- JSON request/response
- Standard HTTP status codes
- Consistent error format
- Pagination for lists
- Filtering and sorting

## Testing

```bash
# Run all tests
npm test

# Run unit tests
npm run test:unit

# Run integration tests
npm run test:integration

# Coverage
npm run test:coverage
```

## Building

```bash
# Build TypeScript
npm run build

# Build Docker image
docker build -t naturaz/service-name .
```

## Deployment

Services are deployed as Docker containers to Kubernetes.

```bash
# Build and push
docker build -t naturaz/service-name:latest .
docker push naturaz/service-name:latest

# Deploy to K8s
kubectl apply -f kubernetes/service-name/
```

## Monitoring

- **Logs**: Centralized logging with ELK
- **Metrics**: Prometheus metrics endpoint at /metrics
- **Health**: Health check endpoint at /health
- **Tracing**: Distributed tracing with Jaeger (optional)

## Security

- Input validation on all endpoints
- SQL injection prevention
- XSS protection
- CSRF tokens where applicable
- Rate limiting
- API key authentication for service-to-service

## Best Practices

1. Keep services small and focused
2. Use environment variables for configuration
3. Implement proper error handling
4. Write comprehensive tests
5. Document APIs with Swagger
6. Use TypeScript for type safety
7. Follow SOLID principles
8. Implement circuit breakers for external calls
9. Use caching strategically
10. Monitor and log everything
