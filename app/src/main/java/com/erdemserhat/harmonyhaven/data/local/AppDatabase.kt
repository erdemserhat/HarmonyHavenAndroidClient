package com.erdemserhat.harmonyhaven.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erdemserhat.harmonyhaven.data.local.dao.ArticleDao
import com.erdemserhat.harmonyhaven.data.local.dao.CategoryDao
import com.erdemserhat.harmonyhaven.data.local.dao.JwtTokenDao
import com.erdemserhat.harmonyhaven.data.local.dao.NotificationDao
import com.erdemserhat.harmonyhaven.data.local.dao.QuoteDao
import com.erdemserhat.harmonyhaven.data.local.entities.ArticleEntity
import com.erdemserhat.harmonyhaven.data.local.entities.CategoryEntity
import com.erdemserhat.harmonyhaven.data.local.entities.JwtTokenEntity
import com.erdemserhat.harmonyhaven.data.local.entities.NotificationEntity
import com.erdemserhat.harmonyhaven.data.local.entities.QuoteEntity
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erdemserhat.harmonyhaven.domain.WarningProductionCase

@Database(
    entities = [
        JwtTokenEntity::class,
        NotificationEntity::class,
        ArticleEntity::class,
        CategoryEntity::class,
        QuoteEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jwtTokenDao(): JwtTokenDao
    abstract fun notificationDao(): NotificationDao
    abstract fun articleDao(): ArticleDao
    abstract fun categoryDao(): CategoryDao
    abstract fun quoteDao(): QuoteDao

    companion object {
        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                CREATE TABLE IF NOT EXISTS quotes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    quote TEXT NOT NULL,
                    writer TEXT NOT NULL,
                    imageUrl TEXT NOT NULL,
                    quoteCategory INTEGER NOT NULL,
                    isLiked INTEGER NOT NULL DEFAULT 0
                )
            """)
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                CREATE TABLE IF NOT EXISTS quotes_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    quoteId INTEGER NOT NULL,
                    quote TEXT NOT NULL,
                    writer TEXT NOT NULL,
                    imageUrl TEXT NOT NULL,
                    quoteCategory INTEGER NOT NULL,
                    isLiked INTEGER NOT NULL DEFAULT 0
                )
            """)
                database.execSQL("""
                INSERT INTO quotes_new (id, quote, writer, imageUrl, quoteCategory, isLiked)
                SELECT id, quote, writer, imageUrl, quoteCategory, isLiked
                FROM quotes
            """)
                database.execSQL("DROP TABLE quotes")
                database.execSQL("ALTER TABLE quotes_new RENAME TO quotes")
            }
        }
    }

}
