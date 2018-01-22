package com.raywenderlich.alltherages

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.Serializable
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.github.kittinunf.fuel.android.core.Json
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

data class CryptoStatus(
    val open: Float,
    val high: Float,
    val low: Float,
    val volume: Float,
    val last: Float,
    val volume_30day: Float
)


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

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
        val index = arguments.getSerializable("n") as Int
        val view: View = inflater!!.inflate(R.layout.fragment_number, container, false)

        val coinName = resources.getStringArray(R.array.coins)[index]
        Fuel.get("https://api.gdax.com/products/$coinName/stats").responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val errorText = view.findViewById<TextView>(R.id.error) as TextView
                    errorText.text = "There was an error"
                }
                is Result.Success -> {
                    val data = result.get()

                    val nameText = view.findViewById<TextView>(R.id.name) as TextView
                    val openText = view.findViewById<TextView>(R.id.open) as TextView
                    val highText = view.findViewById<TextView>(R.id.high) as TextView
                    val lowText = view.findViewById<TextView>(R.id.low) as TextView
                    val volumeText = view.findViewById<TextView>(R.id.volume) as TextView
                    val lastText = view.findViewById<TextView>(R.id.last) as TextView
                    val vol30Text = view.findViewById<TextView>(R.id.volume_30day) as TextView

                    val moshi = Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    val cryptoAdapter = moshi.adapter(CryptoStatus::class.java)
                    val cryptoStatus: CryptoStatus? = cryptoAdapter.fromJson(data)

                    nameText.text = coinName
                    openText.text = cryptoStatus?.open.toString()
                    highText.text = cryptoStatus?.high.toString()
                    lowText.text = cryptoStatus?.low.toString()
                    volumeText.text = cryptoStatus?.volume.toString()
                    lastText.text = cryptoStatus?.last.toString()
                    vol30Text.text = cryptoStatus?.volume_30day.toString()
                }
            }
        }

        return view
    }
}