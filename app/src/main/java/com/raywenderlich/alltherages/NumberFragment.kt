package com.raywenderlich.alltherages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.Serializable
import android.widget.TextView


class NumberFragment : Fragment() {
    companion object {

        fun newInstance(n: Int): NumberFragment {
            val args = Bundle() // Bundle is key value pairs
            args.putSerializable("n", n as Serializable)
            val fragment = NumberFragment()

            // add init params via arguments
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val number = arguments.getSerializable("n").toString()

        val view: View = inflater!!.inflate(R.layout.fragment_number, container, false)
        val numberText = view.findViewById<TextView>(R.id.number) as TextView
        numberText.text = "this is page number $number"

        return view
    }
}