package com.raywenderlich.alltherages

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ComicDetailsPagerStateAdapter(fragmentManager: FragmentManager, private val comics: ArrayList<Comic>) :
        FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return RageComicDetailsFragment.newInstance(comics[position])
    }

    override fun getCount(): Int {
        return comics.size
    }
}