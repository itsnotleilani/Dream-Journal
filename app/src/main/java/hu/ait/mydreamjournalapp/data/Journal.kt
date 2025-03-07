package hu.ait.mydreamjournalapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class Journal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val journalContent: String? = null,
    val createdAt: Long,
//    val coverImage: Int = R.drawable.default_icon, // Default icon
//    val coverColor: Color = PastelPurple
    val coverColor: Int = 0
)
