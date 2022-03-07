package com.awais.core.usecase

import com.awais.core.data.Note

class GetWordCount {

    operator fun invoke(note: Note): Int = getCount(note.title) + getCount(note.content)

    private fun getCount(string: String) = string.split(" ", "\n")
        .filter { it.contains(Regex(".*[a-zA-Z].*")) }.count()
}