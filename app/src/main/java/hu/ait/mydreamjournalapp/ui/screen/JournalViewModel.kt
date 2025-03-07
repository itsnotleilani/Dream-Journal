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
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository // Repository injected here
) : ViewModel() {

    private val _journalState = MutableStateFlow<Journal?>(null)
    val journalState: StateFlow<Journal?> = _journalState

    fun getJournalById(folderId: Int): StateFlow<Journal?> {
        viewModelScope.launch {
            journalRepository.getJournalById(folderId).collect { folder ->
                _journalState.value = folder
            }
        }
        return journalState
    }

    fun updateJournal(folder: Journal) {
        // Update the folder in the repository or database
        viewModelScope.launch {
            journalRepository.updateJournal(folder)  // Assuming you have a repository method to update the folder
        }
    }
}
