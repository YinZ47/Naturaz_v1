package com.naturaz.bd.data.mapper

import com.naturaz.bd.data.local.db.entity.ProductEntity
import com.naturaz.bd.data.remote.dto.ProductDto
import com.naturaz.bd.domain.model.Category
import com.naturaz.bd.domain.model.Currency
import com.naturaz.bd.domain.model.EcoScore
import com.naturaz.bd.domain.model.Product

fun ProductDto.toEntity(): ProductEntity = ProductEntity(
    id = id,
    name = name,
    description = description,
    brand = brand,
    price = price,
    discountedPrice = discountedPrice,
    currencyCode = currency,
    imageUrls = imageUrls,
    ecoScore = ecoScore.score,
    ecoCertifications = ecoScore.certifications,
    carbonFootprintKg = ecoScore.carbonFootprintKg,
    ecoSummary = ecoScore.summary,
    rating = rating,
    totalReviews = totalReviews,
    inStock = inStock,
    stockCount = stockCount,
    categoryId = category.id,
    categoryName = category.name,
    vendorId = vendorId,
    vendorName = vendorName,
    badges = badges
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    brand = brand,
    price = price,
    discountedPrice = discountedPrice,
    currency = currencyCode.toCurrency(),
    images = imageUrls,
    ecoScore = EcoScore(
        score = ecoScore,
        certificationBadges = ecoCertifications,
        carbonFootprintKg = carbonFootprintKg,
        sustainabilitySummary = ecoSummary
    ),
    rating = rating,
    totalReviews = totalReviews,
    inStock = inStock,
    stockCount = stockCount,
    category = Category(
        id = categoryId,
        name = categoryName,
        iconUrl = null,
        parentId = null,
        ecoFriendly = ecoScore >= 70
    ),
    vendorId = vendorId,
    vendorName = vendorName,
    badges = badges
)

fun ProductDto.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    brand = brand,
    price = price,
    discountedPrice = discountedPrice,
    currency = currency.toCurrency(),
    images = imageUrls,
    ecoScore = ecoScore.toDomain(),
    rating = rating,
    totalReviews = totalReviews,
    inStock = inStock,
    stockCount = stockCount,
    category = category.toDomain(),
    vendorId = vendorId,
    vendorName = vendorName,
    badges = badges
)

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    name = name,
    description = description,
    brand = brand,
    price = price,
    discountedPrice = discountedPrice,
    currencyCode = currency.code,
    imageUrls = images,
    ecoScore = ecoScore.score,
    ecoCertifications = ecoScore.certificationBadges,
    carbonFootprintKg = ecoScore.carbonFootprintKg,
    ecoSummary = ecoScore.sustainabilitySummary,
    rating = rating,
    totalReviews = totalReviews,
    inStock = inStock,
    stockCount = stockCount,
    categoryId = category.id,
    categoryName = category.name,
    vendorId = vendorId,
    vendorName = vendorName,
    badges = badges
)
