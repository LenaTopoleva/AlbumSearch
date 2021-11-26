package com.lenatopoleva.albumsearch.model.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lenatopoleva.albumsearch.model.datasource.db.dao.AlbumsDao
import com.lenatopoleva.albumsearch.model.datasource.db.dao.TracksDao
import com.lenatopoleva.albumsearch.model.datasource.db.entity.AlbumDbEntity
import com.lenatopoleva.albumsearch.model.datasource.db.entity.TrackDbEntity

@Database(entities = arrayOf(AlbumDbEntity::class, TrackDbEntity::class),
        version = 1,
        exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun tracksDao(): TracksDao
}