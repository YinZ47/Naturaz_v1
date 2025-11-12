package com.naturaz.bd.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naturaz.bd.data.local.db.entity.HomeFeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeFeedDao {
    @Query("SELECT * FROM home_feed WHERE id = 0 LIMIT 1")
    fun observeHomeFeed(): Flow<HomeFeedEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeFeed(entity: HomeFeedEntity)

    @Query("DELETE FROM home_feed")
    suspend fun clear()
}
