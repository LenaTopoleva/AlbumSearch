package com.lenatopoleva.albumsearch.model.datasource.db.dao

import androidx.room.*
import com.lenatopoleva.albumsearch.model.datasource.db.entity.AlbumDbEntity

@Dao
interface AlbumsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(albumEntities: List<AlbumDbEntity>)

    @Query("SELECT * FROM albums WHERE collectionName LIKE '%' || :title || '%'")
    suspend fun getAlbumsByTitle(title: String): List<AlbumDbEntity>?

    @Query("SELECT * FROM albums WHERE collectionId LIKE :id")
    suspend fun getAlbumById(id: Int): AlbumDbEntity?
}