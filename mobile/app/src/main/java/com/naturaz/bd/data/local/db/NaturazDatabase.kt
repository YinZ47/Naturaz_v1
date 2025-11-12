package com.naturaz.bd.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naturaz.bd.data.local.db.dao.CartDao
import com.naturaz.bd.data.local.db.dao.CategoryDao
import com.naturaz.bd.data.local.db.dao.HomeFeedDao
import com.naturaz.bd.data.local.db.dao.ProductDao
import com.naturaz.bd.data.local.db.entity.CartItemEntity
import com.naturaz.bd.data.local.db.entity.CategoryEntity
import com.naturaz.bd.data.local.db.entity.HomeFeedEntity
import com.naturaz.bd.data.local.db.entity.ProductEntity

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        HomeFeedEntity::class,
        CartItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NaturazDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun homeFeedDao(): HomeFeedDao
    abstract fun cartDao(): CartDao
}
