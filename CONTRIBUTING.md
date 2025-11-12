# Contributing to Naturaz

Thank you for your interest in contributing to Naturaz! We welcome contributions from the community.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Commit Messages](#commit-messages)
- [Pull Request Process](#pull-request-process)
- [Testing](#testing)
- [Documentation](#documentation)

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive environment for all contributors.

### Our Standards

- Be respectful and inclusive
- Accept constructive criticism
- Focus on what's best for the community
- Show empathy towards others

## Getting Started

### Prerequisites

- Node.js 18+
- Docker & Docker Compose
- Git
- PostgreSQL 15+ (or use Docker)
- MongoDB 6+ (or use Docker)
- Redis 7+ (or use Docker)

### Setup Development Environment

1. **Fork the repository**
```bash
# Click "Fork" on GitHub, then clone your fork
git clone https://github.com/YOUR_USERNAME/Naturaz_v1.git
cd Naturaz_v1
```

2. **Add upstream remote**
```bash
git remote add upstream https://github.com/YinZ47/Naturaz_v1.git
```

3. **Install dependencies**
```bash
npm install
```

4. **Setup environment variables**
```bash
cp .env.example .env
# Edit .env with your configuration
```

5. **Start local services**
```bash
docker-compose up -d
```

6. **Run migrations**
```bash
npm run migrate
```

7. **Start development servers**
```bash
npm run dev
```

## Development Workflow

### Branching Strategy

We use a simplified Git Flow:

- `main`: Production-ready code
- `develop`: Integration branch for features
- `feature/*`: New features
- `bugfix/*`: Bug fixes
- `hotfix/*`: Urgent production fixes

### Creating a Feature Branch

```bash
# Update your local repository
git checkout develop
git pull upstream develop

# Create feature branch
git checkout -b feature/your-feature-name
```

### Making Changes

1. Make your changes
2. Write/update tests
3. Run tests: `npm test`
4. Lint your code: `npm run lint`
5. Format code: `npm run format`

### Keeping Your Branch Updated

```bash
# Fetch latest changes
git fetch upstream

# Rebase your branch
git rebase upstream/develop
```

## Coding Standards

### TypeScript/JavaScript

- Use TypeScript for all new code
- Follow ESLint configuration
- Use functional components and hooks in React
- Prefer `const` over `let`, avoid `var`
- Use meaningful variable and function names
- Add JSDoc comments for public APIs

### Example

```typescript
/**
 * Calculate the eco score for a product
 * @param product - Product details
 * @returns Eco score between 0-100
 */
export const calculateEcoScore = (product: Product): number => {
  // Implementation
  return score;
};
```

### React Components

```typescript
import React from 'react';

interface ProductCardProps {
  product: Product;
  onAddToCart: (id: string) => void;
}

export const ProductCard: React.FC<ProductCardProps> = ({ 
  product, 
  onAddToCart 
}) => {
  return (
    <div className="product-card">
      {/* Component content */}
    </div>
  );
};
```

### CSS/Styling

- Use TailwindCSS utility classes
- Follow mobile-first approach
- Use semantic class names when custom CSS is needed
- Maintain consistent spacing and colors from design system

### File Naming

- React components: `PascalCase.tsx` (e.g., `ProductCard.tsx`)
- Utilities/helpers: `camelCase.ts` (e.g., `formatPrice.ts`)
- Constants: `UPPER_SNAKE_CASE.ts` (e.g., `API_ENDPOINTS.ts`)
- Directories: `kebab-case` (e.g., `user-service`)

## Commit Messages

We follow [Conventional Commits](https://www.conventionalcommits.org/).

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, no logic change)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks
- `perf`: Performance improvements

### Examples

```
feat(product): add eco score calculation

Implement algorithm to calculate eco score based on:
- Material sustainability
- Production location
- Carbon footprint

Closes #123
```

```
fix(cart): resolve quantity update bug

Fixed issue where cart quantity wasn't updating
in real-time when user changed quantity.

Fixes #456
```

## Pull Request Process

### Before Submitting

1. ✅ All tests pass: `npm test`
2. ✅ Code is linted: `npm run lint`
3. ✅ Code is formatted: `npm run format`
4. ✅ Documentation is updated
5. ✅ Commit messages follow conventions
6. ✅ Branch is up to date with `develop`

### Submitting a Pull Request

1. **Push your branch**
```bash
git push origin feature/your-feature-name
```

2. **Create Pull Request on GitHub**
   - Use descriptive title
   - Reference related issues
   - Fill out PR template completely
   - Add screenshots for UI changes

3. **PR Template**

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Related Issues
Closes #123

## Screenshots (if applicable)
[Add screenshots]

## Checklist
- [ ] Tests pass
- [ ] Code is linted
- [ ] Documentation updated
- [ ] Reviewed my own code
```

### Review Process

- At least one approval required
- All comments must be addressed
- CI/CD checks must pass
- No merge conflicts

### After Approval

- Squash and merge (default)
- Delete feature branch after merge

## Testing

### Unit Tests

```bash
npm run test:unit
```

Write unit tests for:
- Utility functions
- React components
- Business logic
- API endpoints

Example:
```typescript
describe('calculateEcoScore', () => {
  it('should return 100 for fully sustainable product', () => {
    const product = {
      sustainableMaterial: true,
      locallySourced: true,
      carbonNeutral: true,
    };
    expect(calculateEcoScore(product)).toBe(100);
  });
});
```

### Integration Tests

```bash
npm run test:integration
```

Test interactions between services and databases.

### E2E Tests

```bash
npm run test:e2e
```

Test complete user workflows.

## Documentation

### Code Documentation

- Add JSDoc comments for public APIs
- Include examples in comments
- Document complex logic
- Update README files

### API Documentation

- Document all endpoints in API_SPECIFICATION.md
- Include request/response examples
- Document error codes
- Keep Swagger/OpenAPI specs updated

### Architecture Documentation

- Update ARCHITECTURE.md for architectural changes
- Document decision rationale in TECHNOLOGY_DECISIONS.md
- Keep diagrams current

## Reporting Bugs

### Before Reporting

1. Check existing issues
2. Verify it's reproducible
3. Test on latest version

### Bug Report Template

```markdown
## Bug Description
Clear description of the bug

## Steps to Reproduce
1. Go to '...'
2. Click on '...'
3. See error

## Expected Behavior
What should happen

## Actual Behavior
What actually happens

## Environment
- OS: [e.g., macOS 13]
- Browser: [e.g., Chrome 120]
- Node version: [e.g., 18.17.0]

## Screenshots
[If applicable]

## Additional Context
[Any other relevant information]
```

## Feature Requests

### Feature Request Template

```markdown
## Feature Description
Clear description of the feature

## Problem Statement
What problem does this solve?

## Proposed Solution
How should this work?

## Alternatives Considered
Other approaches you've thought about

## Additional Context
[Screenshots, mockups, etc.]
```

## Questions?

- **General Questions**: Open a GitHub Discussion
- **Bug Reports**: Open an issue
- **Security Issues**: Email security@naturaz.com.bd
- **Feature Requests**: Open an issue

## Recognition

Contributors will be recognized in:
- CONTRIBUTORS.md file
- Release notes
- Project README

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to Naturaz! 🌿
