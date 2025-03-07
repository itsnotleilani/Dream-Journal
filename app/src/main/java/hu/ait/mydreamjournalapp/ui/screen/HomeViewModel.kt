package hu.ait.mydreamjournalapp.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ait.mydreamjournalapp.data.JournalDao
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.mydreamjournalapp.data.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val journalDao: JournalDao
) : ViewModel() {

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())
    val journals: StateFlow<List<Journal>> = _journals

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getAllJournals()
    }

    private fun getAllJournals() {
        _loading.value = true
        viewModelScope.launch {
            try {
                journalDao.getAllJournals().collect { journalList ->
                    _journals.value = journalList
                }
                _loading.value = false
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching journals", e)
                _error.value = "Error fetching journals."
                _loading.value = false
            }
        }
    }

    fun updateJournal(journal: Journal) {
        viewModelScope.launch {
            journalDao.updateJournal(journal)
        }
    }

    fun addJournal(journal: Journal) {
        viewModelScope.launch {
            journalDao.insertJournal(journal)
        }
    }

    fun deleteJournal(journal: Journal) {
        viewModelScope.launch {
            try {
                journalDao.deleteJournal(journal)
                _journals.value = _journals.value.filter { it.id != journal.id }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error deleting journal", e)
                _error.value = "Error deleting journal."
            }
        }
    }
}
