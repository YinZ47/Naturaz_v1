# Frontend Applications

This directory contains all frontend applications for the Naturaz platform.

## Applications

### 1. Web Application (`/web`)
- **Framework**: Next.js 14
- **Language**: TypeScript
- **Styling**: TailwindCSS
- **Purpose**: Main customer-facing e-commerce website

### 2. Admin Dashboard (`/admin`)
- **Framework**: React 18
- **Language**: TypeScript
- **Styling**: Material-UI
- **Purpose**: Administration and management interface

## Web Application

### Features
- 🏠 Homepage with featured products
- 🔍 Product search and filtering
- 📱 Product detail pages
- 🛒 Shopping cart
- 💳 Checkout flow
- 👤 User authentication and profile
- 📦 Order history and tracking
- ⭐ Product reviews
- 🌱 Eco-features dashboard
- 🇧🇩 Bangla language support

### Pages Structure
```
web/src/pages/
├── index.tsx                 # Homepage
├── products/
│   ├── index.tsx            # Product listing
│   ├── [slug].tsx           # Product detail
│   └── categories/
│       └── [slug].tsx       # Category page
├── cart.tsx                 # Shopping cart
├── checkout/
│   ├── index.tsx            # Checkout
│   ├── shipping.tsx         # Shipping info
│   └── payment.tsx          # Payment
├── account/
│   ├── profile.tsx          # User profile
│   ├── orders.tsx           # Order history
│   ├── addresses.tsx        # Saved addresses
│   └── wishlist.tsx         # Wishlist
├── auth/
│   ├── login.tsx            # Login
│   ├── register.tsx         # Registration
│   └── forgot-password.tsx  # Password reset
├── vendor/
│   └── dashboard.tsx        # Vendor dashboard
└── eco/
    ├── impact.tsx           # Environmental impact
    └── local-artisans.tsx   # Local artisan marketplace
```

### Components Structure
```
web/src/components/
├── common/
│   ├── Button.tsx
│   ├── Input.tsx
│   ├── Modal.tsx
│   ├── Card.tsx
│   └── Badge.tsx
├── layout/
│   ├── Header.tsx
│   ├── Footer.tsx
│   ├── Navigation.tsx
│   └── Sidebar.tsx
├── products/
│   ├── ProductCard.tsx
│   ├── ProductGrid.tsx
│   ├── ProductFilter.tsx
│   └── ProductDetails.tsx
├── cart/
│   ├── CartItem.tsx
│   ├── CartSummary.tsx
│   └── MiniCart.tsx
├── checkout/
│   ├── CheckoutSteps.tsx
│   ├── ShippingForm.tsx
│   └── PaymentMethods.tsx
├── user/
│   ├── ProfileForm.tsx
│   ├── OrderCard.tsx
│   └── AddressCard.tsx
└── eco-features/
    ├── EcoScore.tsx
    ├── CarbonCalculator.tsx
    ├── TreeCounter.tsx
    └── LocalBadge.tsx
```

### Setup

1. **Navigate to web directory**
```bash
cd frontend/web
```

2. **Install dependencies**
```bash
npm install
```

3. **Setup environment**
```bash
cp .env.example .env.local
```

4. **Run development server**
```bash
npm run dev
```

5. **Build for production**
```bash
npm run build
npm start
```

### Technology Stack
- **Framework**: Next.js 14 (App Router)
- **UI Library**: React 18
- **Language**: TypeScript
- **Styling**: TailwindCSS
- **State Management**: Redux Toolkit / Zustand
- **Forms**: React Hook Form
- **Validation**: Zod
- **HTTP Client**: Axios
- **Data Fetching**: TanStack Query (React Query)
- **Icons**: Heroicons / Lucide React
- **Date Handling**: date-fns
- **Image Optimization**: Next.js Image

## Admin Dashboard

### Features
- 📊 Dashboard with analytics
- 👥 User management
- 🏪 Vendor management
- 📦 Product management
- 🛍️ Order management
- 💰 Payment management
- 📈 Analytics and reports
- ⚙️ System settings
- 🌱 Eco-certification management

### Pages Structure
```
admin/src/pages/
├── dashboard.tsx            # Main dashboard
├── users/
│   ├── index.tsx           # User list
│   └── [id].tsx            # User details
├── vendors/
│   ├── index.tsx           # Vendor list
│   ├── [id].tsx            # Vendor details
│   └── pending.tsx         # Pending approvals
├── products/
│   ├── index.tsx           # Product list
│   ├── [id].tsx            # Product details
│   └── create.tsx          # Create product
├── orders/
│   ├── index.tsx           # Order list
│   └── [id].tsx            # Order details
├── analytics/
│   ├── sales.tsx           # Sales analytics
│   ├── products.tsx        # Product analytics
│   └── users.tsx           # User analytics
└── settings/
    ├── general.tsx         # General settings
    ├── payments.tsx        # Payment settings
    └── shipping.tsx        # Shipping settings
```

### Setup

1. **Navigate to admin directory**
```bash
cd frontend/admin
```

2. **Install dependencies**
```bash
npm install
```

3. **Run development server**
```bash
npm run dev
```

### Technology Stack
- **Framework**: React 18 with Vite
- **UI Library**: Material-UI (MUI)
- **Language**: TypeScript
- **Styling**: Material-UI System
- **State Management**: Redux Toolkit
- **Charts**: Recharts / Chart.js
- **Data Tables**: MUI DataGrid
- **Forms**: React Hook Form
- **HTTP Client**: Axios

## Shared Features

### Theme
Both applications use a nature-inspired theme:
- **Primary Color**: Green (#2D5016, #4A7C59)
- **Secondary Color**: Earth tones
- **Accent**: Natural browns and blues
- **Typography**: Clean, readable fonts

### i18n (Internationalization)
- **Library**: next-i18next / react-i18next
- **Languages**: English (en), Bangla (bn)
- **Translation files**: Located in `/public/locales/`

### Responsive Design
- Mobile-first approach
- Breakpoints:
  - Mobile: < 640px
  - Tablet: 640px - 1024px
  - Desktop: > 1024px

## Development Guidelines

### Code Style
- Use functional components with hooks
- Follow TypeScript best practices
- Use ESLint and Prettier
- Write meaningful component names
- Keep components small and focused

### State Management
- Use local state for component-specific data
- Use global state (Redux/Zustand) for shared data
- Use React Query for server state

### API Integration
```typescript
// services/api/products.ts
import axios from 'axios';

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export const getProducts = async (params) => {
  const { data } = await axios.get(`${API_URL}/products`, { params });
  return data;
};
```

### Component Example
```typescript
// components/products/ProductCard.tsx
import React from 'react';
import Image from 'next/image';
import Link from 'next/link';

interface ProductCardProps {
  product: {
    id: string;
    name: string;
    price: number;
    image: string;
    ecoScore: number;
  };
}

export const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  return (
    <div className="border rounded-lg overflow-hidden hover:shadow-lg transition">
      <Link href={`/products/${product.id}`}>
        <Image
          src={product.image}
          alt={product.name}
          width={300}
          height={300}
          className="w-full h-48 object-cover"
        />
        <div className="p-4">
          <h3 className="font-semibold">{product.name}</h3>
          <p className="text-lg text-green-600">৳{product.price}</p>
          <div className="flex items-center gap-2">
            <span className="text-sm">Eco Score:</span>
            <span className="font-bold text-green-700">{product.ecoScore}</span>
          </div>
        </div>
      </Link>
    </div>
  );
};
```

## Testing

```bash
# Run tests
npm test

# Run with coverage
npm run test:coverage

# E2E tests (Playwright/Cypress)
npm run test:e2e
```

## Building

```bash
# Build for production
npm run build

# Analyze bundle
npm run analyze
```

## Deployment

### Vercel (Recommended for Next.js)
```bash
vercel deploy
```

### Docker
```bash
docker build -t naturaz/web .
docker run -p 3000:3000 naturaz/web
```

### Static Export
```bash
npm run build
npm run export
# Deploy /out directory to any static host
```

## Performance Optimization

- Use Next.js Image component for automatic optimization
- Implement code splitting
- Lazy load components
- Use React.memo for expensive renders
- Implement virtual scrolling for long lists
- Optimize bundle size

## Accessibility

- Use semantic HTML
- Implement keyboard navigation
- Add ARIA labels
- Ensure color contrast
- Support screen readers

## SEO

- Meta tags on all pages
- Open Graph tags
- Structured data (JSON-LD)
- Sitemap generation
- robots.txt

## Browser Support

- Chrome (latest 2 versions)
- Firefox (latest 2 versions)
- Safari (latest 2 versions)
- Edge (latest 2 versions)
- Mobile browsers
