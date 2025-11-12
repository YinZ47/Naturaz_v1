package com.naturaz.bd.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.naturaz.bd.data.local.db.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun observeCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(items: List<CartItemEntity>)

    @Query("DELETE FROM cart_items")
    suspend fun clear()

    @Transaction
    suspend fun replaceCart(items: List<CartItemEntity>) {
        clear()
        insertOrReplace(items)
    }
}
