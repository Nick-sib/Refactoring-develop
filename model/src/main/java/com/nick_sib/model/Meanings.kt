package com.nick_sib.model

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