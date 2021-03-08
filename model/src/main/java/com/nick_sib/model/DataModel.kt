package com.nick_sib.model

data class DataModel(
    val id: Long = -1L,
    val text: String?,
    val meanings: List<Meanings> = emptyList(),
    var isFavorite: Boolean = false
) {
    val getMeaningsAll: String
        get() {
            val result = meanings.map{ s -> s.translation?.text }.toString()
            return result.substring(1 until result.length-1)
        }
    val visible: Boolean = !meanings.isNullOrEmpty()
}