/**
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.alltherages

import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout


class MainActivity : AppCompatActivity(), RageComicListFragment.OnRageComicSelected  {
  internal lateinit var viewpageradapter: TabPagerAdapter

  private val detailsViewRequestCode: Int = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewpageradapter = TabPagerAdapter(supportFragmentManager)

    val viewPager = findViewById<ViewPager>(R.id.main_view_pager)
    viewPager.adapter = viewpageradapter

    val tabLayoutView = findViewById<TabLayout>(R.id.main_tab_layout)
    tabLayoutView.setupWithViewPager(viewPager)
  }

  override fun onRageComicSelected(position: Int) {
    val name = resources.getStringArray(R.array.names)[position]
    Toast.makeText(this, "you selected " + name + "!", Toast.LENGTH_SHORT).show()

    val intent = Intent(this, RageComicDetailsActivity::class.java)
    intent.putExtra("comicIndex", position.toString())
    startActivityForResult(intent, detailsViewRequestCode)
  }
}
