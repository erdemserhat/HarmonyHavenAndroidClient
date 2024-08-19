package com.erdemserhat.harmonyhaven.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.local.dao.ArticleDao
import com.erdemserhat.harmonyhaven.data.local.dao.CategoryDao
import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity

/**
 * Abstract class representing the Room database for the application.
 *
 * This class defines the database configuration and serves as the main access point for the underlying SQLite database.
 * It provides abstract methods to access the DAOs (Data Access Objects) for managing different entities.
 *
 * @property version The database version number. Increment this value when the database schema is changed.
 * @constructor Creates an instance of [AppDatabase] with the provided configuration.
 */
@Database(
    entities = [
        JwtTokenEntity::class,
        NotificationEntity::class,
        ArticleEntity::class,
        CategoryEntity::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the [JwtTokenDao] for performing database operations on JWT tokens.
     *
     * @return An instance of [JwtTokenDao] for managing JWT token records.
     */
    abstract fun jwtTokenDao(): JwtTokenDao

    /**
     * Provides access to the [NotificationDao] for performing database operations on notifications.
     *
     * @return An instance of [NotificationDao] for managing notification records.
     */
    abstract fun notificationDao(): NotificationDao

    /**
     * Provides access to the [ArticleDao] for performing database operations on articles.
     *
     * @return An instance of [ArticleDao] for managing article records.
     */
    abstract fun articleDao(): ArticleDao

    /**
     * Provides access to the [CategoryDao] for performing database operations on categories.
     *
     * @return An instance of [CategoryDao] for managing category records.
     */
    abstract fun categoryDao(): CategoryDao
}
