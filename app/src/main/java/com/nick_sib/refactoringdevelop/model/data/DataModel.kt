package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataModel(
    val id: Long = -1L,
    val text: String?,
    val meanings: List<Meanings>?,
    var isFavorite: Boolean = false
): Parcelable {
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