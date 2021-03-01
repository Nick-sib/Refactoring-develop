package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meanings (
    val translation: Translation?,
    val imageUrl: String?
): Parcelable {

    @IgnoredOnParcel
    val text: String
        get() = translation?.text ?: ""

}