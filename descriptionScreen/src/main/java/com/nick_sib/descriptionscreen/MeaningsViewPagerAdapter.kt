package com.nick_sib.descriptionscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nick_sib.model.Meanings

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