package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meanings (
    val translation: Translation?,
    val imageUrl: String?
): Parcelable