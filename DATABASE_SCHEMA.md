# Database Schema Design

## Overview
This document outlines the database schema for the Naturaz e-commerce platform. The system uses a polyglot persistence approach with PostgreSQL for transactional data, MongoDB for flexible schemas, and Redis for caching.

## PostgreSQL Databases

### 1. User Database

#### users
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    gender VARCHAR(20),
    date_of_birth DATE,
    profile_image_url TEXT,
    email_verified BOOLEAN DEFAULT FALSE,
    phone_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    role VARCHAR(50) DEFAULT 'customer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone ON users(phone);
CREATE INDEX idx_users_role ON users(role);
```

#### user_addresses
```sql
CREATE TABLE user_addresses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    address_type VARCHAR(50), -- 'shipping', 'billing'
    full_name VARCHAR(200),
    phone VARCHAR(20),
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    district VARCHAR(100),
    division VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100) DEFAULT 'Bangladesh',
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_addresses_user_id ON user_addresses(user_id);
```

#### user_sessions
```sql
CREATE TABLE user_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    token_hash VARCHAR(255) NOT NULL,
    refresh_token_hash VARCHAR(255),
    device_info JSONB,
    ip_address VARCHAR(45),
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_user_sessions_user_id ON user_sessions(user_id);
CREATE INDEX idx_user_sessions_token_hash ON user_sessions(token_hash);
```

### 2. Product Database

#### categories
```sql
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(200) UNIQUE NOT NULL,
    description TEXT,
    parent_id UUID REFERENCES categories(id) ON DELETE SET NULL,
    image_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    display_order INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_categories_parent_id ON categories(parent_id);
CREATE INDEX idx_categories_slug ON categories(slug);
```

#### products
```sql
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vendor_id UUID REFERENCES vendors(id) ON DELETE CASCADE,
    category_id UUID REFERENCES categories(id),
    name VARCHAR(300) NOT NULL,
    slug VARCHAR(300) UNIQUE NOT NULL,
    description TEXT,
    short_description TEXT,
    sku VARCHAR(100) UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    compare_price DECIMAL(10, 2),
    cost_price DECIMAL(10, 2),
    weight DECIMAL(10, 2),
    dimensions JSONB, -- {length, width, height}
    status VARCHAR(50) DEFAULT 'draft', -- draft, active, archived
    is_featured BOOLEAN DEFAULT FALSE,
    eco_score INTEGER CHECK (eco_score >= 0 AND eco_score <= 100),
    is_local_product BOOLEAN DEFAULT FALSE,
    carbon_footprint DECIMAL(10, 2), -- in kg CO2
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP
);

CREATE INDEX idx_products_vendor_id ON products(vendor_id);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_slug ON products(slug);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_products_eco_score ON products(eco_score);
```

#### product_variants
```sql
CREATE TABLE product_variants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    sku VARCHAR(100) UNIQUE,
    name VARCHAR(200),
    price DECIMAL(10, 2),
    compare_price DECIMAL(10, 2),
    stock_quantity INTEGER DEFAULT 0,
    options JSONB, -- {color: "Red", size: "L"}
    image_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_variants_product_id ON product_variants(product_id);
CREATE INDEX idx_product_variants_sku ON product_variants(sku);
```

#### product_images
```sql
CREATE TABLE product_images (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    alt_text VARCHAR(255),
    display_order INTEGER,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_images_product_id ON product_images(product_id);
```

#### inventory
```sql
CREATE TABLE inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    variant_id UUID REFERENCES product_variants(id) ON DELETE CASCADE,
    warehouse_id UUID REFERENCES warehouses(id),
    quantity INTEGER DEFAULT 0,
    reserved_quantity INTEGER DEFAULT 0,
    available_quantity INTEGER GENERATED ALWAYS AS (quantity - reserved_quantity) STORED,
    low_stock_threshold INTEGER DEFAULT 10,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_inventory_product_id ON inventory(product_id);
CREATE INDEX idx_inventory_warehouse_id ON inventory(warehouse_id);
```

### 3. Order Database

#### orders
```sql
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    user_id UUID REFERENCES users(id),
    vendor_id UUID REFERENCES vendors(id),
    status VARCHAR(50) DEFAULT 'pending', -- pending, confirmed, processing, shipped, delivered, cancelled, refunded
    payment_status VARCHAR(50) DEFAULT 'pending', -- pending, paid, failed, refunded
    fulfillment_status VARCHAR(50) DEFAULT 'unfulfilled', -- unfulfilled, partial, fulfilled
    subtotal DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) DEFAULT 0,
    shipping_cost DECIMAL(10, 2) DEFAULT 0,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    total_amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'BDT',
    shipping_address JSONB,
    billing_address JSONB,
    customer_note TEXT,
    admin_note TEXT,
    trees_planted INTEGER DEFAULT 0,
    carbon_offset DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP
);

CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_vendor_id ON orders(vendor_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_order_number ON orders(order_number);
CREATE INDEX idx_orders_created_at ON orders(created_at);
```

#### order_items
```sql
CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id),
    variant_id UUID REFERENCES product_variants(id),
    product_name VARCHAR(300),
    variant_name VARCHAR(200),
    sku VARCHAR(100),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) DEFAULT 0,
    discount_amount DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);
```

#### order_tracking
```sql
CREATE TABLE order_tracking (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL,
    location VARCHAR(255),
    description TEXT,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_tracking_order_id ON order_tracking(order_id);
```

### 4. Payment Database

#### transactions
```sql
CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders(id),
    user_id UUID REFERENCES users(id),
    transaction_id VARCHAR(255) UNIQUE,
    payment_method VARCHAR(50), -- bKash, Nagad, Rocket, SSL_Commerz, COD
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'BDT',
    status VARCHAR(50), -- pending, success, failed, refunded
    gateway_response JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transactions_order_id ON transactions(order_id);
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_status ON transactions(status);
```

### 5. Vendor Database

#### vendors
```sql
CREATE TABLE vendors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    business_name VARCHAR(255) NOT NULL,
    business_email VARCHAR(255),
    business_phone VARCHAR(20),
    description TEXT,
    logo_url TEXT,
    banner_url TEXT,
    tax_id VARCHAR(100),
    is_eco_certified BOOLEAN DEFAULT FALSE,
    eco_certification_date DATE,
    certification_documents JSONB,
    status VARCHAR(50) DEFAULT 'pending', -- pending, approved, suspended, rejected
    rating DECIMAL(3, 2) DEFAULT 0,
    total_sales DECIMAL(12, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP
);

CREATE INDEX idx_vendors_user_id ON vendors(user_id);
CREATE INDEX idx_vendors_status ON vendors(status);
CREATE INDEX idx_vendors_rating ON vendors(rating);
```

#### vendor_bank_accounts
```sql
CREATE TABLE vendor_bank_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vendor_id UUID REFERENCES vendors(id) ON DELETE CASCADE,
    account_type VARCHAR(50), -- bank, mobile_banking
    account_holder_name VARCHAR(255),
    account_number VARCHAR(100),
    bank_name VARCHAR(255),
    branch_name VARCHAR(255),
    routing_number VARCHAR(50),
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vendor_bank_accounts_vendor_id ON vendor_bank_accounts(vendor_id);
```

### 6. Review Database

#### reviews
```sql
CREATE TABLE reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    order_id UUID REFERENCES orders(id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(255),
    comment TEXT,
    is_verified_purchase BOOLEAN DEFAULT FALSE,
    is_approved BOOLEAN DEFAULT FALSE,
    helpful_count INTEGER DEFAULT 0,
    images JSONB, -- array of image URLs
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reviews_product_id ON reviews(product_id);
CREATE INDEX idx_reviews_user_id ON reviews(user_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);
```

### 7. Shipping Database

#### warehouses
```sql
CREATE TABLE warehouses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE,
    address JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### shipments
```sql
CREATE TABLE shipments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    carrier VARCHAR(100), -- Pathao, Sundarban, SA Paribahan
    tracking_number VARCHAR(255),
    shipping_method VARCHAR(100),
    estimated_delivery DATE,
    actual_delivery TIMESTAMP,
    shipping_cost DECIMAL(10, 2),
    status VARCHAR(50), -- pending, in_transit, delivered, returned
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_shipments_order_id ON shipments(order_id);
CREATE INDEX idx_shipments_tracking_number ON shipments(tracking_number);
```

## MongoDB Collections

### Product Catalog (Flexible Schema)
```javascript
// products_extended
{
    _id: ObjectId,
    productId: UUID,
    tags: [String],
    metadata: {
        brand: String,
        material: String,
        origin: String,
        certifications: [String]
    },
    specifications: {
        key: value
    },
    seo: {
        metaTitle: String,
        metaDescription: String,
        keywords: [String]
    },
    ecoDetails: {
        sustainableMaterials: Boolean,
        biodegradable: Boolean,
        recycledContent: Number,
        locallySourced: Boolean,
        organicCertified: Boolean
    }
}
```

### User Preferences
```javascript
// user_preferences
{
    _id: ObjectId,
    userId: UUID,
    language: String,
    currency: String,
    notifications: {
        email: Boolean,
        sms: Boolean,
        push: Boolean
    },
    interests: [String],
    viewedProducts: [UUID],
    searchHistory: [String],
    wishlist: [UUID]
}
```

### Shopping Cart (Session-based)
```javascript
// carts
{
    _id: ObjectId,
    userId: UUID,
    sessionId: String,
    items: [
        {
            productId: UUID,
            variantId: UUID,
            quantity: Number,
            price: Number,
            addedAt: Date
        }
    ],
    couponCode: String,
    expiresAt: Date,
    updatedAt: Date
}
```

### Reviews Extended
```javascript
// reviews_extended
{
    _id: ObjectId,
    reviewId: UUID,
    productId: UUID,
    media: [
        {
            type: String, // image, video
            url: String,
            thumbnail: String
        }
    ],
    helpfulVotes: [UUID], // user IDs who found it helpful
    replies: [
        {
            userId: UUID,
            comment: String,
            createdAt: Date
        }
    ]
}
```

### Analytics Events
```javascript
// analytics_events
{
    _id: ObjectId,
    eventType: String, // page_view, product_view, add_to_cart, purchase
    userId: UUID,
    sessionId: String,
    timestamp: Date,
    data: {
        productId: UUID,
        category: String,
        price: Number,
        // ... other event-specific data
    },
    metadata: {
        userAgent: String,
        ipAddress: String,
        referrer: String
    }
}
```

## Redis Data Structures

### Session Storage
```
Key: session:{sessionId}
Type: Hash
TTL: 24 hours
Fields: userId, cartId, preferences, etc.
```

### Product Cache
```
Key: product:{productId}
Type: String (JSON)
TTL: 1 hour
```

### Cart Cache
```
Key: cart:{userId}
Type: Hash
TTL: 7 days
```

### Rate Limiting
```
Key: rate_limit:{userId}:{endpoint}
Type: String (counter)
TTL: 1 minute
```

### Real-time Inventory
```
Key: inventory:{productId}:{variantId}
Type: String (quantity)
TTL: 5 minutes
```

## Relationships

### One-to-Many
- User → Addresses
- User → Orders
- Product → Images
- Product → Variants
- Order → Order Items
- Vendor → Products

### Many-to-Many
- Products ↔ Categories (through junction table if needed)
- Orders ↔ Products (through order_items)

### One-to-One
- User ↔ Vendor
- Order ↔ Transaction

## Indexing Strategy

### Primary Indexes
- All primary keys (UUID)
- Foreign keys for relationships
- Unique constraints on email, phone, SKU

### Secondary Indexes
- Search fields (name, slug)
- Status fields for filtering
- Date fields for sorting
- Rating and price for sorting

### Composite Indexes
```sql
-- For product search and filtering
CREATE INDEX idx_products_category_status ON products(category_id, status);
CREATE INDEX idx_products_price_eco ON products(price, eco_score);

-- For order queries
CREATE INDEX idx_orders_user_status ON orders(user_id, status);
CREATE INDEX idx_orders_date_status ON orders(created_at DESC, status);
```

## Data Retention

### Active Data
- User accounts: Indefinite (until deletion request)
- Products: Indefinite
- Orders: 7 years (compliance)
- Reviews: Indefinite

### Archived Data
- Old sessions: 90 days
- Analytics events: 2 years (then aggregated)
- Logs: 30 days (application), 1 year (audit)

## Backup Strategy

### PostgreSQL
- Daily full backups
- Hourly incremental backups
- 30-day retention
- Point-in-time recovery enabled

### MongoDB
- Daily backups
- 14-day retention
- Replica set for high availability

### Redis
- RDB snapshots every 6 hours
- AOF persistence enabled
- Backup to S3 daily

## Scaling Considerations

### Sharding Strategy
- Products: Shard by category or vendor
- Users: Shard by region/division
- Orders: Shard by date range

### Read Replicas
- PostgreSQL read replicas for analytics
- MongoDB read preference for queries
- Redis cluster for high throughput

### Partitioning
- Orders table partitioned by date
- Analytics events partitioned by month
- Logs partitioned by date

## Security Measures

### Data Protection
- Encryption at rest for sensitive data
- Password hashing with bcrypt
- PII data encryption
- PCI DSS compliance for payment data

### Access Control
- Row-level security for multi-tenant data
- Database user roles and permissions
- Connection pooling with auth
- IP whitelisting for admin access

## Migration Strategy

### Version Control
- Use migration tools (Flyway, Liquibase)
- Version all schema changes
- Rollback scripts for each migration
- Test migrations in staging first

### Zero-Downtime Migrations
- Backward compatible changes
- Blue-green deployment support
- Feature flags for schema changes
- Gradual data migration
