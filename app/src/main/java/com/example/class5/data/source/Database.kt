package com.example.class5.data.source

import com.example.class5.data.model.Note

object Database {

    private val dailyNotes = mutableListOf<Note>()

    fun getDailyNotes() = dailyNotes

    fun addDailyNote(title: String, desc: String, date:String, saveType: String) {
        dailyNotes.add(
            Note(
                id = (dailyNotes.lastOrNull()?.id ?: 1) + 1,
                title = title,
                desc = desc,
                date = date,
                saveType = saveType
            )
        )
    }

    fun removeDailyNote(note: Note) = dailyNotes.remove(note)
}