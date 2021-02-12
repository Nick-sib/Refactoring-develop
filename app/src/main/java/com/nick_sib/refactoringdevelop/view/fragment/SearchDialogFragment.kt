package com.nick_sib.refactoringdevelop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nick_sib.refactoringdevelop.R

class SearchDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_dialog_fragment, container, false)
    }


    companion object {
        fun instance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }
}