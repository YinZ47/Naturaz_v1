# API Specification

## Overview
This document describes the REST API endpoints for the Naturaz e-commerce platform. All APIs follow RESTful conventions and use JSON for request/response bodies.

## Base URLs
- **Production**: `https://api.naturaz.com.bd`
- **Staging**: `https://api-staging.naturaz.com.bd`
- **Development**: `http://localhost:3000`

## API Versioning
All API endpoints are versioned using URL path versioning:
```
/api/v1/{resource}
```

## Authentication
Most endpoints require authentication using JWT tokens.

### Authentication Methods
1. **JWT Bearer Token**: Include in Authorization header
2. **API Key**: For service-to-service communication

### Headers
```
Authorization: Bearer {access_token}
Content-Type: application/json
Accept: application/json
X-API-Key: {api_key} (for service-to-service)
```

## Common Response Format

### Success Response
```json
{
    "success": true,
    "data": {},
    "message": "Operation successful"
}
```

### Error Response
```json
{
    "success": false,
    "error": {
        "code": "ERROR_CODE",
        "message": "Human readable error message",
        "details": {}
    }
}
```

### Pagination Response
```json
{
    "success": true,
    "data": [],
    "pagination": {
        "page": 1,
        "perPage": 20,
        "total": 100,
        "totalPages": 5
    }
}
```

## HTTP Status Codes
- `200 OK`: Successful request
- `201 Created`: Resource created successfully
- `204 No Content`: Successful request with no response body
- `400 Bad Request`: Invalid request parameters
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource conflict
- `422 Unprocessable Entity`: Validation error
- `429 Too Many Requests`: Rate limit exceeded
- `500 Internal Server Error`: Server error
- `503 Service Unavailable`: Service temporarily unavailable

## Rate Limiting
- **Anonymous users**: 100 requests/hour
- **Authenticated users**: 1000 requests/hour
- **Vendors**: 5000 requests/hour

Rate limit headers:
```
X-RateLimit-Limit: 1000
X-RateLimit-Remaining: 999
X-RateLimit-Reset: 1609459200
```

## API Endpoints

### 1. Authentication & User Management

#### POST /api/v1/auth/register
Register a new user account.

**Request:**
```json
{
    "email": "user@example.com",
    "password": "SecurePassword123!",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "+8801712345678"
}
```

**Response:** `201 Created`
```json
{
    "success": true,
    "data": {
        "user": {
            "id": "uuid",
            "email": "user@example.com",
            "firstName": "John",
            "lastName": "Doe",
            "phone": "+8801712345678",
            "emailVerified": false
        },
        "accessToken": "jwt_token",
        "refreshToken": "refresh_token"
    }
}
```

#### POST /api/v1/auth/login
Login to existing account.

**Request:**
```json
{
    "email": "user@example.com",
    "password": "SecurePassword123!"
}
```

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "user": { /* user object */ },
        "accessToken": "jwt_token",
        "refreshToken": "refresh_token"
    }
}
```

#### POST /api/v1/auth/refresh
Refresh access token.

**Request:**
```json
{
    "refreshToken": "refresh_token"
}
```

#### POST /api/v1/auth/logout
Logout current session.

#### GET /api/v1/users/me
Get current user profile.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "id": "uuid",
        "email": "user@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "phone": "+8801712345678",
        "profileImage": "url",
        "emailVerified": true,
        "createdAt": "2024-01-01T00:00:00Z"
    }
}
```

#### PUT /api/v1/users/me
Update current user profile.

#### GET /api/v1/users/me/addresses
Get user addresses.

#### POST /api/v1/users/me/addresses
Add new address.

**Request:**
```json
{
    "type": "shipping",
    "fullName": "John Doe",
    "phone": "+8801712345678",
    "addressLine1": "123 Main Street",
    "city": "Dhaka",
    "district": "Dhaka",
    "division": "Dhaka",
    "postalCode": "1000",
    "isDefault": true
}
```

### 2. Product Management

#### GET /api/v1/products
Get list of products with pagination and filters.

**Query Parameters:**
- `page`: Page number (default: 1)
- `perPage`: Items per page (default: 20, max: 100)
- `category`: Category ID or slug
- `minPrice`: Minimum price
- `maxPrice`: Maximum price
- `sort`: Sort by (price, rating, newest, eco_score)
- `order`: asc or desc
- `search`: Search query
- `isLocal`: Filter local products (true/false)
- `minEcoScore`: Minimum eco score (0-100)

**Response:** `200 OK`
```json
{
    "success": true,
    "data": [
        {
            "id": "uuid",
            "name": "Organic Cotton T-Shirt",
            "slug": "organic-cotton-tshirt",
            "description": "Eco-friendly cotton t-shirt",
            "price": 1200.00,
            "comparePrice": 1500.00,
            "images": ["url1", "url2"],
            "rating": 4.5,
            "reviewCount": 150,
            "ecoScore": 85,
            "isLocal": true,
            "inStock": true,
            "vendor": {
                "id": "uuid",
                "name": "Green Shop BD",
                "isEcoCertified": true
            }
        }
    ],
    "pagination": {
        "page": 1,
        "perPage": 20,
        "total": 500,
        "totalPages": 25
    }
}
```

#### GET /api/v1/products/:id
Get product details by ID.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "id": "uuid",
        "name": "Organic Cotton T-Shirt",
        "slug": "organic-cotton-tshirt",
        "description": "Full description...",
        "shortDescription": "Brief description",
        "price": 1200.00,
        "comparePrice": 1500.00,
        "sku": "OCS-001",
        "images": [
            {
                "url": "image_url",
                "altText": "Front view",
                "isPrimary": true
            }
        ],
        "category": {
            "id": "uuid",
            "name": "Clothing",
            "slug": "clothing"
        },
        "variants": [
            {
                "id": "uuid",
                "name": "Small - Red",
                "sku": "OCS-001-SM-RED",
                "price": 1200.00,
                "stockQuantity": 50,
                "options": {
                    "size": "Small",
                    "color": "Red"
                }
            }
        ],
        "rating": 4.5,
        "reviewCount": 150,
        "ecoScore": 85,
        "carbonFootprint": 2.5,
        "isLocal": true,
        "specifications": {
            "material": "100% Organic Cotton",
            "origin": "Bangladesh",
            "weight": "200g"
        },
        "vendor": {
            "id": "uuid",
            "name": "Green Shop BD",
            "rating": 4.7,
            "isEcoCertified": true
        }
    }
}
```

#### GET /api/v1/products/:id/reviews
Get product reviews.

**Query Parameters:**
- `page`, `perPage`
- `rating`: Filter by rating (1-5)
- `verified`: Show only verified purchases

#### GET /api/v1/categories
Get product categories.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": [
        {
            "id": "uuid",
            "name": "Clothing",
            "slug": "clothing",
            "image": "url",
            "productCount": 1500,
            "children": [
                {
                    "id": "uuid",
                    "name": "T-Shirts",
                    "slug": "tshirts"
                }
            ]
        }
    ]
}
```

### 3. Shopping Cart

#### GET /api/v1/cart
Get user's shopping cart.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "id": "uuid",
        "items": [
            {
                "id": "uuid",
                "product": {
                    "id": "uuid",
                    "name": "Product name",
                    "image": "url",
                    "price": 1200.00
                },
                "variant": {
                    "id": "uuid",
                    "name": "Small - Red",
                    "options": {"size": "Small", "color": "Red"}
                },
                "quantity": 2,
                "subtotal": 2400.00
            }
        ],
        "subtotal": 2400.00,
        "discount": 0,
        "total": 2400.00,
        "itemCount": 2
    }
}
```

#### POST /api/v1/cart/items
Add item to cart.

**Request:**
```json
{
    "productId": "uuid",
    "variantId": "uuid",
    "quantity": 1
}
```

#### PUT /api/v1/cart/items/:id
Update cart item quantity.

#### DELETE /api/v1/cart/items/:id
Remove item from cart.

#### DELETE /api/v1/cart
Clear cart.

### 4. Orders

#### POST /api/v1/orders
Create new order.

**Request:**
```json
{
    "items": [
        {
            "productId": "uuid",
            "variantId": "uuid",
            "quantity": 2
        }
    ],
    "shippingAddressId": "uuid",
    "billingAddressId": "uuid",
    "paymentMethod": "bKash",
    "shippingMethod": "standard",
    "couponCode": "SAVE10",
    "sustainablePackaging": true,
    "customerNote": "Please deliver in the morning"
}
```

**Response:** `201 Created`
```json
{
    "success": true,
    "data": {
        "id": "uuid",
        "orderNumber": "NAT-2024-00001",
        "status": "pending",
        "paymentStatus": "pending",
        "items": [ /* order items */ ],
        "subtotal": 2400.00,
        "tax": 0,
        "shippingCost": 100.00,
        "discount": 240.00,
        "total": 2260.00,
        "treesPlanted": 2,
        "carbonOffset": 1.5,
        "createdAt": "2024-01-01T00:00:00Z"
    }
}
```

#### GET /api/v1/orders
Get user's orders.

**Query Parameters:**
- `page`, `perPage`
- `status`: Filter by status
- `fromDate`, `toDate`: Date range

#### GET /api/v1/orders/:id
Get order details.

#### GET /api/v1/orders/:id/tracking
Get order tracking information.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "orderId": "uuid",
        "status": "shipped",
        "carrier": "Pathao",
        "trackingNumber": "PTH123456",
        "estimatedDelivery": "2024-01-10",
        "history": [
            {
                "status": "confirmed",
                "location": "Dhaka",
                "description": "Order confirmed",
                "timestamp": "2024-01-01T10:00:00Z"
            },
            {
                "status": "shipped",
                "location": "Dhaka Hub",
                "description": "Package shipped",
                "timestamp": "2024-01-02T14:00:00Z"
            }
        ]
    }
}
```

#### POST /api/v1/orders/:id/cancel
Cancel order.

### 5. Reviews & Ratings

#### POST /api/v1/reviews
Create product review.

**Request:**
```json
{
    "productId": "uuid",
    "orderId": "uuid",
    "rating": 5,
    "title": "Great product!",
    "comment": "Really happy with this purchase...",
    "images": ["url1", "url2"]
}
```

#### GET /api/v1/reviews/:id
Get review details.

#### PUT /api/v1/reviews/:id
Update review.

#### DELETE /api/v1/reviews/:id
Delete review.

#### POST /api/v1/reviews/:id/helpful
Mark review as helpful.

### 6. Wishlist

#### GET /api/v1/wishlist
Get user's wishlist.

#### POST /api/v1/wishlist/items
Add item to wishlist.

**Request:**
```json
{
    "productId": "uuid"
}
```

#### DELETE /api/v1/wishlist/items/:productId
Remove item from wishlist.

### 7. Search

#### GET /api/v1/search
Search products, categories, vendors.

**Query Parameters:**
- `q`: Search query (required)
- `type`: Search type (products, categories, vendors)
- `page`, `perPage`
- Additional filters based on type

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "products": [ /* product results */ ],
        "categories": [ /* category results */ ],
        "vendors": [ /* vendor results */ ],
        "suggestions": ["suggestion1", "suggestion2"]
    },
    "pagination": { /* pagination info */ }
}
```

#### GET /api/v1/search/suggestions
Get search suggestions/autocomplete.

**Query Parameters:**
- `q`: Search query

### 8. Vendor APIs

#### POST /api/v1/vendors/register
Register as vendor.

#### GET /api/v1/vendors/me
Get vendor profile.

#### PUT /api/v1/vendors/me
Update vendor profile.

#### GET /api/v1/vendors/me/products
Get vendor's products.

#### POST /api/v1/vendors/me/products
Create new product.

#### PUT /api/v1/vendors/me/products/:id
Update product.

#### GET /api/v1/vendors/me/orders
Get vendor's orders.

#### GET /api/v1/vendors/me/analytics
Get vendor analytics.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "sales": {
            "today": 15000.00,
            "thisWeek": 85000.00,
            "thisMonth": 320000.00,
            "total": 2500000.00
        },
        "orders": {
            "pending": 12,
            "processing": 25,
            "shipped": 45,
            "delivered": 180
        },
        "products": {
            "total": 150,
            "active": 140,
            "outOfStock": 10
        },
        "topProducts": [ /* top selling products */ ],
        "recentOrders": [ /* recent orders */ ]
    }
}
```

### 9. Payment

#### POST /api/v1/payments/initiate
Initiate payment.

**Request:**
```json
{
    "orderId": "uuid",
    "paymentMethod": "bKash",
    "returnUrl": "https://naturaz.com.bd/payment/success",
    "cancelUrl": "https://naturaz.com.bd/payment/cancel"
}
```

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "transactionId": "TXN123456",
        "paymentUrl": "https://payment-gateway.com/pay/...",
        "expiresAt": "2024-01-01T12:00:00Z"
    }
}
```

#### POST /api/v1/payments/callback
Payment gateway callback (webhook).

#### GET /api/v1/payments/:transactionId
Get payment status.

### 10. Notifications

#### GET /api/v1/notifications
Get user notifications.

**Query Parameters:**
- `page`, `perPage`
- `unreadOnly`: boolean

#### PUT /api/v1/notifications/:id/read
Mark notification as read.

#### PUT /api/v1/notifications/read-all
Mark all notifications as read.

### 11. Eco Features (Unique to Naturaz)

#### POST /api/v1/eco/carbon-calculator
Calculate carbon footprint for cart/order.

**Request:**
```json
{
    "items": [
        {
            "productId": "uuid",
            "quantity": 2
        }
    ],
    "shippingMethod": "standard",
    "division": "Dhaka"
}
```

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "totalCarbonFootprint": 5.5,
        "breakdown": {
            "products": 4.5,
            "packaging": 0.5,
            "shipping": 0.5
        },
        "offsetCost": 55.00,
        "treesToPlant": 3,
        "comparison": "Equivalent to 25km car journey"
    }
}
```

#### GET /api/v1/eco/user-impact
Get user's environmental impact.

**Response:** `200 OK`
```json
{
    "success": true,
    "data": {
        "treesPlanted": 25,
        "carbonOffset": 125.5,
        "localProductsPurchased": 45,
        "sustainablePackagesChosen": 30,
        "ecoScore": 87,
        "badges": ["Tree Warrior", "Local Supporter"]
    }
}
```

#### GET /api/v1/eco/products/top-rated
Get products with highest eco scores.

### 12. Admin APIs

#### GET /api/v1/admin/dashboard
Get admin dashboard statistics.

#### GET /api/v1/admin/users
Get all users (paginated, with filters).

#### GET /api/v1/admin/vendors
Get all vendors.

#### PUT /api/v1/admin/vendors/:id/approve
Approve vendor.

#### GET /api/v1/admin/orders
Get all orders.

#### PUT /api/v1/admin/orders/:id/status
Update order status.

#### GET /api/v1/admin/products
Get all products.

#### PUT /api/v1/admin/products/:id/status
Update product status.

#### GET /api/v1/admin/analytics
Get platform analytics.

## Webhooks

### Order Status Update
```json
POST {webhook_url}
{
    "event": "order.status_updated",
    "timestamp": "2024-01-01T00:00:00Z",
    "data": {
        "orderId": "uuid",
        "status": "shipped",
        "previousStatus": "confirmed"
    }
}
```

### Payment Success
```json
POST {webhook_url}
{
    "event": "payment.success",
    "timestamp": "2024-01-01T00:00:00Z",
    "data": {
        "transactionId": "TXN123456",
        "orderId": "uuid",
        "amount": 2260.00
    }
}
```

## GraphQL API (Optional)

GraphQL endpoint available at:
```
POST /api/v1/graphql
```

Example Query:
```graphql
query GetProduct($id: ID!) {
    product(id: $id) {
        id
        name
        price
        ecoScore
        vendor {
            name
            isEcoCertified
        }
        reviews(first: 5) {
            edges {
                node {
                    rating
                    comment
                }
            }
        }
    }
}
```

## SDK Examples

### JavaScript/TypeScript
```javascript
import { NaturazClient } from '@naturaz/sdk';

const client = new NaturazClient({
    apiKey: 'your_api_key',
    environment: 'production'
});

// Get products
const products = await client.products.list({
    category: 'clothing',
    minEcoScore: 80
});

// Create order
const order = await client.orders.create({
    items: [{ productId: 'uuid', quantity: 2 }],
    shippingAddressId: 'uuid'
});
```

### Python
```python
from naturaz import NaturazClient

client = NaturazClient(api_key='your_api_key')

# Get products
products = client.products.list(category='clothing', min_eco_score=80)

# Create order
order = client.orders.create(
    items=[{'productId': 'uuid', 'quantity': 2}],
    shipping_address_id='uuid'
)
```

## Error Codes

| Code | Description |
|------|-------------|
| `INVALID_REQUEST` | Invalid request parameters |
| `UNAUTHORIZED` | Authentication required |
| `FORBIDDEN` | Insufficient permissions |
| `NOT_FOUND` | Resource not found |
| `DUPLICATE_ENTRY` | Resource already exists |
| `VALIDATION_ERROR` | Input validation failed |
| `PAYMENT_FAILED` | Payment processing failed |
| `OUT_OF_STOCK` | Product out of stock |
| `RATE_LIMIT_EXCEEDED` | Too many requests |
| `SERVER_ERROR` | Internal server error |

## Testing

### Sandbox Environment
- Base URL: `https://api-sandbox.naturaz.com.bd`
- Test credentials provided in developer portal
- Test payment gateway credentials available

### Postman Collection
- Available at: `/docs/postman/naturaz-api.json`
- Import into Postman for easy testing

## Support

- **Documentation**: https://docs.naturaz.com.bd
- **API Status**: https://status.naturaz.com.bd
- **Developer Portal**: https://developers.naturaz.com.bd
- **Support Email**: api-support@naturaz.com.bd
