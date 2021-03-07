package com.nick_sib.refactoringdevelop.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nick_sib.refactoringdevelop.model.data.Meanings
import com.nick_sib.refactoringdevelop.view.fragment.PageFragment

class MeaningsViewPagerAdapter(
    private var data: List<Meanings>,
    fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    fun setData(value: List<Meanings>) {
        data = value
        notifyDataSetChanged()
    }

    override fun getCount() = data.size

    override fun getPageTitle(position: Int) = "${data.size}/${position + 1}"

    override fun getItem(position: Int): Fragment =
        PageFragment.newInstance(data[position])
}