package com.awais.cleanarchitecturesolid.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awais.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    var title: String = "",
    var content: String = "",
    @ColumnInfo(name = "creation_time")
    var creationTime: Long = 0,
    @ColumnInfo(name = "update_time")
    var updateTime: Long = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) {
    companion object {
        fun fromNote(note: Note) =
            NoteEntity(note.title, note.content, note.creationTime, note.updateTime, note.id)
    }

    fun toNote() = Note(title, content, creationTime, updateTime, id)
}