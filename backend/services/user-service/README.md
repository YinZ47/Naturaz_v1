# User Service

Authentication and user management service for Naturaz.

## Overview

Handles user registration, authentication, profile management, and authorization.

## Features

- User registration
- Email/Phone verification
- JWT authentication
- Social login (Google, Facebook)
- Profile management
- Address management
- Password reset
- Session management

## API Endpoints

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout
- `POST /api/v1/auth/refresh` - Refresh access token
- `POST /api/v1/auth/verify-email` - Verify email
- `POST /api/v1/auth/forgot-password` - Request password reset
- `POST /api/v1/auth/reset-password` - Reset password

### Profile
- `GET /api/v1/users/me` - Get current user
- `PUT /api/v1/users/me` - Update profile
- `GET /api/v1/users/me/addresses` - Get addresses
- `POST /api/v1/users/me/addresses` - Add address
- `PUT /api/v1/users/me/addresses/:id` - Update address
- `DELETE /api/v1/users/me/addresses/:id` - Delete address

## Environment Variables

```bash
PORT=3001
DATABASE_URL=postgresql://...
JWT_SECRET=your-secret
JWT_REFRESH_SECRET=your-refresh-secret
GOOGLE_CLIENT_ID=...
FACEBOOK_APP_ID=...
```

## Database Schema

See [DATABASE_SCHEMA.md](../../../DATABASE_SCHEMA.md) for complete schema.

## Development

```bash
# Install dependencies
npm install

# Run in development
npm run dev

# Run tests
npm test

# Build
npm run build
```

## Docker

```bash
# Build image
docker build -t naturaz/user-service .

# Run container
docker run -p 3001:3001 naturaz/user-service
```

## Technology Stack

- Node.js 20
- TypeScript
- Express.js
- Prisma (PostgreSQL ORM)
- JWT for authentication
- bcrypt for password hashing
