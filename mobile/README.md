# Mobile Application

React Native mobile application for Naturaz e-commerce platform.

## Overview

Native mobile apps for iOS and Android built with React Native, providing a seamless shopping experience with unique eco-friendly features.

## Features

### Core Features
- 🏠 **Home Feed** - Featured products, deals, eco-highlights
- 🔍 **Product Search** - Advanced search with filters
- 📱 **Product Details** - Rich product information with images
- 🛒 **Shopping Cart** - Add, remove, update cart items
- 💳 **Checkout** - Streamlined checkout process
- 👤 **User Account** - Profile, orders, addresses
- 📦 **Order Tracking** - Real-time order status updates
- ⭐ **Reviews** - Read and write product reviews
- 🔔 **Notifications** - Push notifications for orders, deals

### Unique Features
- 🌱 **Eco Dashboard** - Track environmental impact
- 🌳 **Tree Counter** - See trees planted through purchases
- 📊 **Carbon Footprint** - Calculate purchase impact
- 🇧🇩 **Local Products** - Discover Bangladesh-made items
- 🏘️ **Artisan Stories** - Learn about local makers

## Technology Stack

- **Framework**: React Native 0.73+
- **Language**: TypeScript
- **Navigation**: React Navigation 6
- **State Management**: Redux Toolkit
- **HTTP Client**: Axios
- **Forms**: React Hook Form
- **Icons**: React Native Vector Icons
- **Maps**: React Native Maps
- **Payments**: React Native In-App Purchase (for bKash, Nagad)
- **Push Notifications**: Firebase Cloud Messaging
- **Analytics**: Firebase Analytics
- **Crash Reporting**: Firebase Crashlytics

## Project Structure

```
mobile/
├── src/
│   ├── components/
│   │   ├── common/           # Reusable components
│   │   │   ├── Button.tsx
│   │   │   ├── Input.tsx
│   │   │   ├── Card.tsx
│   │   │   └── Badge.tsx
│   │   └── screens/          # Screen-specific components
│   │       ├── ProductCard.tsx
│   │       ├── CartItem.tsx
│   │       └── OrderCard.tsx
│   ├── screens/              # App screens
│   │   ├── Home/
│   │   ├── Products/
│   │   ├── ProductDetails/
│   │   ├── Cart/
│   │   ├── Checkout/
│   │   ├── Profile/
│   │   ├── Orders/
│   │   └── EcoDashboard/
│   ├── navigation/           # Navigation configuration
│   │   ├── AppNavigator.tsx
│   │   ├── AuthNavigator.tsx
│   │   └── TabNavigator.tsx
│   ├── services/             # API services
│   │   ├── api.ts
│   │   ├── auth.ts
│   │   ├── products.ts
│   │   ├── cart.ts
│   │   └── orders.ts
│   ├── store/                # Redux store
│   │   ├── index.ts
│   │   ├── slices/
│   │   │   ├── authSlice.ts
│   │   │   ├── cartSlice.ts
│   │   │   └── userSlice.ts
│   ├── hooks/                # Custom hooks
│   │   ├── useAuth.ts
│   │   ├── useCart.ts
│   │   └── useProducts.ts
│   ├── utils/                # Utility functions
│   │   ├── formatters.ts
│   │   ├── validators.ts
│   │   └── helpers.ts
│   ├── constants/            # Constants
│   │   ├── colors.ts
│   │   ├── dimensions.ts
│   │   └── api.ts
│   ├── assets/               # Static assets
│   │   ├── images/
│   │   ├── fonts/
│   │   └── icons/
│   ├── config/               # App configuration
│   │   ├── firebase.ts
│   │   └── env.ts
│   └── App.tsx               # Root component
├── android/                  # Android native code
├── ios/                      # iOS native code
├── __tests__/                # Tests
├── package.json
└── tsconfig.json
```

## Screens

### Authentication
- **Login** - Email/phone login
- **Register** - New user registration
- **Forgot Password** - Password recovery

### Main Screens
- **Home** - Dashboard with featured products
- **Categories** - Browse product categories
- **Products** - Product listing with filters
- **Product Details** - Detailed product view
- **Cart** - Shopping cart management
- **Checkout** - Payment and shipping
- **Orders** - Order history
- **Order Details** - Track specific order
- **Profile** - User profile settings
- **Addresses** - Manage delivery addresses
- **Wishlist** - Saved products

### Eco Features
- **Eco Dashboard** - Environmental impact summary
- **Tree Tracker** - Trees planted visualization
- **Carbon Calculator** - Footprint calculator
- **Local Products** - Local artisan marketplace

## Setup

### Prerequisites
- Node.js 18+
- React Native CLI
- Xcode (for iOS)
- Android Studio (for Android)
- CocoaPods (for iOS)

### Installation

1. **Clone and navigate**
```bash
cd mobile
```

2. **Install dependencies**
```bash
npm install
# or
yarn install
```

3. **Install iOS dependencies**
```bash
cd ios
pod install
cd ..
```

4. **Setup environment**
```bash
cp .env.example .env
# Edit .env with your configuration
```

### Running

#### iOS
```bash
# Start Metro bundler
npm start

# Run on iOS simulator
npm run ios

# Run on specific device
npm run ios -- --device "iPhone Name"
```

#### Android
```bash
# Start Metro bundler
npm start

# Run on Android emulator/device
npm run android
```

## Development

### Creating New Screens

1. Create screen component in `src/screens/`
2. Add navigation route in `src/navigation/`
3. Connect to Redux store if needed
4. Add API service calls

Example:
```typescript
// src/screens/ProductDetails/ProductDetailsScreen.tsx
import React, { useEffect } from 'react';
import { View, Text, ScrollView, Image } from 'react-native';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProductDetails } from '../../services/products';

const ProductDetailsScreen = ({ route }) => {
  const { productId } = route.params;
  const dispatch = useDispatch();
  
  useEffect(() => {
    fetchProductDetails(productId);
  }, [productId]);

  return (
    <ScrollView>
      {/* Product details UI */}
    </ScrollView>
  );
};

export default ProductDetailsScreen;
```

### Styling

Use StyleSheet for consistent styling:
```typescript
import { StyleSheet } from 'react-native';
import { COLORS, SIZES } from '../../constants';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
    padding: SIZES.padding,
  },
  title: {
    fontSize: SIZES.h1,
    fontWeight: 'bold',
    color: COLORS.primary,
  },
});
```

### State Management

Using Redux Toolkit:
```typescript
// store/slices/cartSlice.ts
import { createSlice } from '@reduxjs/toolkit';

const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    items: [],
    total: 0,
  },
  reducers: {
    addToCart: (state, action) => {
      state.items.push(action.payload);
      // Calculate total
    },
    removeFromCart: (state, action) => {
      // Remove item logic
    },
  },
});

export const { addToCart, removeFromCart } = cartSlice.actions;
export default cartSlice.reducer;
```

### Navigation

Using React Navigation:
```typescript
// navigation/AppNavigator.tsx
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Stack = createNativeStackNavigator();

const AppNavigator = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="ProductDetails" component={ProductDetailsScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};
```

## Testing

```bash
# Run Jest tests
npm test

# Run with coverage
npm run test:coverage

# Run E2E tests (Detox)
npm run test:e2e:ios
npm run test:e2e:android
```

## Building

### iOS

```bash
# Build for testing
npm run build:ios

# Archive for App Store
# Open Xcode and use Product > Archive
```

### Android

```bash
# Generate release APK
cd android
./gradlew assembleRelease

# Generate release AAB (Play Store)
./gradlew bundleRelease
```

## Deployment

### iOS (App Store)

1. Configure app in App Store Connect
2. Archive app in Xcode
3. Upload to App Store Connect
4. Submit for review

### Android (Play Store)

1. Generate signed AAB
2. Upload to Play Console
3. Create release
4. Submit for review

## Push Notifications

### Firebase Setup

1. Add `google-services.json` (Android)
2. Add `GoogleService-Info.plist` (iOS)
3. Configure Firebase Cloud Messaging

```typescript
// config/firebase.ts
import messaging from '@react-native-firebase/messaging';

export const requestNotificationPermission = async () => {
  const authStatus = await messaging().requestPermission();
  const enabled =
    authStatus === messaging.AuthorizationStatus.AUTHORIZED ||
    authStatus === messaging.AuthorizationStatus.PROVISIONAL;

  if (enabled) {
    console.log('Notification permission granted');
  }
};

export const getDeviceToken = async () => {
  const token = await messaging().getToken();
  return token;
};
```

## Performance Optimization

- Use `React.memo` for expensive components
- Implement `FlatList` for long lists
- Optimize images with proper sizing
- Use Hermes JavaScript engine
- Enable ProGuard (Android)
- Implement code splitting

## Best Practices

1. **Follow React Native best practices**
2. **Use TypeScript for type safety**
3. **Implement error boundaries**
4. **Handle offline scenarios**
5. **Optimize bundle size**
6. **Test on real devices**
7. **Follow platform-specific design guidelines**
8. **Implement proper analytics**
9. **Handle deep linking**
10. **Secure sensitive data**

## Troubleshooting

### Common Issues

**Metro bundler issues**
```bash
npm start -- --reset-cache
```

**iOS build issues**
```bash
cd ios
pod deintegrate
pod install
```

**Android build issues**
```bash
cd android
./gradlew clean
```

## Environment Variables

```bash
API_URL=https://api.naturaz.com.bd
GOOGLE_MAPS_API_KEY=your_key
FIREBASE_PROJECT_ID=naturaz-app
```

## Dependencies

### Core
- react-native
- react-navigation
- redux
- redux-toolkit
- axios

### UI Components
- react-native-vector-icons
- react-native-elements
- react-native-paper

### Utilities
- react-hook-form
- date-fns
- lodash

### Native Modules
- @react-native-firebase/app
- @react-native-firebase/messaging
- react-native-maps
- react-native-image-picker

## Resources

- [React Native Docs](https://reactnative.dev)
- [React Navigation](https://reactnavigation.org)
- [Redux Toolkit](https://redux-toolkit.js.org)
- [Firebase for React Native](https://rnfirebase.io)

## Support

For issues and questions:
- GitHub Issues
- Email: mobile-support@naturaz.com.bd
