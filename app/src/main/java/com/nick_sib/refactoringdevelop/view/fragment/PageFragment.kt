package com.nick_sib.refactoringdevelop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nick_sib.model.Meanings
import com.nick_sib.refactoringdevelop.databinding.PagerLayoutBinding

class PageFragment: Fragment() {

    private lateinit var data: Meanings
    private lateinit var binding: PagerLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        data = arguments?.getParcelable(EXTRA_DATA) ?: Meanings(null, null)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = PagerLayoutBinding.inflate(inflater, container, false).let {
        binding = it
        binding.property = data
        binding.executePendingBindings()
        it.root
    }


    companion object {
        private val EXTRA_DATA = PageFragment::class.java.name + "EXTRA_DATA"
        fun newInstance(data: Meanings) = PageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_DATA, data)
            }
        }
    }
}