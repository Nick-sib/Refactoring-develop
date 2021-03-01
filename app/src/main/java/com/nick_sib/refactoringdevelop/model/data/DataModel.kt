package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    val text: String?,
    val meanings: List<Meanings>?
): Parcelable {
    var isFavorite: Boolean = false

    @IgnoredOnParcel
    val getMeaningsAll: String
        get() {
            val result = meanings?.map{ s -> s.translation?.text }?.toString() ?: defValue
            return result.substring(1 until result.length-1)
        }
    @IgnoredOnParcel
    val visible: Boolean = !meanings.isNullOrEmpty()
}

private const val defValue = "[NONE]"