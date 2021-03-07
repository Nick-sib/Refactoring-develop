package com.nick_sib.refactoringdevelop.model.data

data class DataModel(
    val id: Long = -1L,
    val text: String?,
    val meanings: List<Meanings>?,
    var isFavorite: Boolean = false
) {
    val getMeaningsAll: String
        get() {
            val result = meanings?.map{ s -> s.translation?.text }?.toString() ?: defValue
            return result.substring(1 until result.length-1)
        }
    val visible: Boolean = !meanings.isNullOrEmpty()
}

private const val defValue = "[NONE]"