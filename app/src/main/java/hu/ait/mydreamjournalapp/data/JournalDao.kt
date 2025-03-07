package hu.ait.mydreamjournalapp.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update

@Dao
interface JournalDao {

    @Query("SELECT * FROM folders")
    fun getAllJournals(): Flow<List<Journal>>

    @Query("SELECT * FROM folders WHERE id = :journalId LIMIT 1")
    fun getJournalById(journalId: Int): Flow<Journal?>

    @Insert
    suspend fun insertJournal(journal: Journal)

    @Delete
    suspend fun deleteJournal(journal: Journal)

    @Query("DELETE FROM folders")
    suspend fun deleteAllJournals()

    @Update
    suspend fun updateJournal(journal: Journal)
}
