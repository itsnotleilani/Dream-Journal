package hu.ait.mydreamjournalapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.mydreamjournalapp.data.Journal
import hu.ait.mydreamjournalapp.data.JournalRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class JournalDetailViewModel @Inject constructor(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _journalState = MutableStateFlow<Journal?>(null)
    val journalState: StateFlow<Journal?> = _journalState

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun loadJournal(journalId: Int) {
        viewModelScope.launch {
            println("loadJournal called with journalId: $journalId")
            _isLoading.value = true
            try {
                journalRepository.getJournalById(journalId).collect { journal ->
                    println("Journal retrieved: $journal")
                    _journalState.value = journal
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                println("Error in loadJournal: ${e.message}")
                _errorMessage.value = "Failed to load journal"
                _isLoading.value = false
            }
        }
    }

    fun updateJournal(updatedJournal: Journal) {
        viewModelScope.launch {
            try {
                journalRepository.updateJournal(updatedJournal)
                _journalState.value = updatedJournal
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update journal"
            }
        }
    }

    fun deleteJournal(journal: Journal) {
        viewModelScope.launch {
            try {
                journalRepository.deleteJournal(journal)
                _journalState.value = journal
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete journal"
            }
        }
    }
}
