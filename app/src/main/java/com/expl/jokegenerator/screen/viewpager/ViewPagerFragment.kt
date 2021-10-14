package com.expl.jokegenerator.screen.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expl.jokegenerator.R
import com.expl.jokegenerator.databinding.FragmentViewPagerBinding
import com.expl.jokegenerator.utilits.START_PAGE_INDEX
import com.expl.jokegenerator.utilits.SAVED_JOKE_PAGE_INDEX
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val mBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        val tabLayout = mBinding.tabs
        val viewPager = mBinding.viewPager
        viewPager.adapter = ViewPagerFragmentAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            START_PAGE_INDEX -> R.drawable.ic_search
            SAVED_JOKE_PAGE_INDEX -> R.drawable.ic_save
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            START_PAGE_INDEX -> getString(R.string.toolbar_search_text)
            SAVED_JOKE_PAGE_INDEX -> getString(R.string.toolbar_saved_text)
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}