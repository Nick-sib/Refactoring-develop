package com.nick_sib.refactoringdevelop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.SearchDialogFragmentBinding
import java.util.*

class SearchDialogFragment : BottomSheetDialogFragment() {

    private val minCharCount = 2

    private var binding: SearchDialogFragmentBinding? = null
    private var onSearchClickListener: ((String) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        SearchDialogFragmentBinding.inflate(inflater).let {
            binding = it
            it.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            ivSearch.setOnClickListener {
                doSearch()
            }
            searchEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch()
                }
                true
            }
        }
    }

    private fun doSearch(){
        binding?.apply {
            val value = searchEditText.text.toString().trim().toLowerCase(Locale.ROOT)
            if (value.length < minCharCount) {
                searchInputLayout.isErrorEnabled = true
                searchInputLayout.error = getString(R.string.error_length)
            } else {
                onSearchClickListener?.invoke(value)
                dismiss()
            }
        }
    }

    fun setOnSearchClickListener(listener:((String) -> Unit)?) {
        onSearchClickListener = listener
    }

    companion object {
        fun instance(): SearchDialogFragment = SearchDialogFragment()
    }
}