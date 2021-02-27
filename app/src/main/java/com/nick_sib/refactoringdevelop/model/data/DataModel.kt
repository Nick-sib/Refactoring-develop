package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DataModel(
    val text: String?,
    val meanings: List<Meanings>?
): Parcelable {
    val getMeanings: String
        get() =
            if (meanings.isNullOrEmpty()) defValue else meanings[0].translation?.text ?: defValue
    val getMeaningsAll: String
        get() {
            var meaningsSeparatedByComma = String()
            meanings?.forEachIndexed { i, it ->
                meaningsSeparatedByComma +=
                    if (i != meanings.size) "${it.translation?.text}, " else it.translation?.text
            }
            return meaningsSeparatedByComma
        }
    val imageUrl: String?
        get() = if (meanings.isNullOrEmpty()) null else meanings[0].imageUrl

}

private const val defValue = ""