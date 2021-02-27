package com.nick_sib.refactoringdevelop.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Translation(
    val text: String = ""
): Parcelable