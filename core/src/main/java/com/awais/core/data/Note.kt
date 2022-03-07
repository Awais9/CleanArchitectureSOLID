package com.awais.core.data

data class Note(
    var title: String = "",
    var content: String = "",
    var creationTime: Long = 0,
    var updateTime: Long = 0,
    var id: Long = 0
)