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

import java.io.Serializable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.alltherages.databinding.FragmentRageComicDetailsBinding // is this generated?

class RageComicDetailsFragment : Fragment() {

  companion object {

    private const val COMIC = "comic"

    fun newInstance(comic: Comic): RageComicDetailsFragment {
      // injects a comic into the details fragment view via arguments

      val args = Bundle() // Bundle is key value pairs
      args.putSerializable(COMIC, comic as Serializable)
      val fragment = RageComicDetailsFragment()

      // add init params via arguments
      fragment.arguments = args
      return fragment
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val fragmentRageComicDetailsBinding = FragmentRageComicDetailsBinding.inflate(inflater!!,
            container, false)

    val comic = arguments.getSerializable(COMIC) as Comic
    fragmentRageComicDetailsBinding.comic = comic
    comic.text = String.format(getString(R.string.description_format), comic.description, comic.url)
    return fragmentRageComicDetailsBinding.root
  }

}
