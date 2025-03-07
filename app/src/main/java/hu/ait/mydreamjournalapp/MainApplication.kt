package hu.ait.mydreamjournalapp

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import hu.ait.mydreamjournalapp.data.AppDatabase

@HiltAndroidApp
class MainApplication : Application() {
}
