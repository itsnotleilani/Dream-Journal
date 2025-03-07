package hu.ait.mydreamjournalapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Journal::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}
