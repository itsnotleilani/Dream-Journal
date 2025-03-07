package hu.ait.mydreamjournalapp.data

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class JournalRepository @Inject constructor(
    private val journalDao: JournalDao
) {
    fun getJournalById(journalId: Int): Flow<Journal?> {
        return journalDao.getJournalById(journalId)
    }

    suspend fun updateJournal(journal: Journal) {
        journalDao.updateJournal(journal)
    }

    suspend fun deleteJournal(journal: Journal) {
        journalDao.deleteJournal(journal)
    }

    fun getAllJournals(): Flow<List<Journal>> = journalDao.getAllJournals()

}
