package com.raywenderlich.alltherages

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.view.ViewPager

class RageComicDetailsActivity: AppCompatActivity() {
    private lateinit var mPager: ViewPager

    private lateinit var comicDetailPagerAdapter: ComicDetailsPagerStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_rage_comic_details)

        mPager = findViewById(R.id.pager)
        comicDetailPagerAdapter = ComicDetailsPagerStateAdapter(supportFragmentManager, getComics())
        mPager.adapter = comicDetailPagerAdapter

        setCurrentItem(intent.getStringExtra("comicIndex").toInt())
    }

    private fun setCurrentItem(pos: Int) {
        mPager.setCurrentItem(pos)
    }

    private fun getComics(): ArrayList<Comic> {
        val names = resources.getStringArray(R.array.names)
        val descriptions = resources.getStringArray(R.array.descriptions)
        val urls = resources.getStringArray(R.array.urls)

        // Get rage face images.
        val typedArray = resources.obtainTypedArray(R.array.images)
        val imageCount = names.size
        val imageResIds = IntArray(imageCount)
        for (i in 0..imageCount - 1) {
            imageResIds[i] = typedArray.getResourceId(i, 0)
        }

        val comics: ArrayList<Comic> =
                ArrayList(names.mapIndexed { position, name ->
                    Comic(imageResIds[position], name, descriptions[position], urls[position]) })

        typedArray.recycle()

        return comics
    }
}
