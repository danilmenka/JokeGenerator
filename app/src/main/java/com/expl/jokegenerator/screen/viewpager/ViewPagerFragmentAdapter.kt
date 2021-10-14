package com.expl.jokegenerator.screen.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expl.jokegenerator.screen.saved.SavedJokeFragment
import com.expl.jokegenerator.screen.start.StartFragment
import com.expl.jokegenerator.utilits.START_PAGE_INDEX
import com.expl.jokegenerator.utilits.SAVED_JOKE_PAGE_INDEX


class ViewPagerFragmentAdapter(fragmentManager: Fragment) :
    FragmentStateAdapter(fragmentManager) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        START_PAGE_INDEX to { StartFragment() },
        SAVED_JOKE_PAGE_INDEX to { SavedJokeFragment() }
    )
    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}