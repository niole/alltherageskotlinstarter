package com.raywenderlich.alltherages

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment

class TabPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
       if (position == 0) {
          return RageComicListFragment.newInstance()
       }
       return NumberFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val number = position.toString()
        return "go to $number"
    }
}
