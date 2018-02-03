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
    private var coinName: String? = null

    private var cryptoStatus: CryptoStatus = CryptoStatus(0.toFloat(), 0.toFloat(), 0.toFloat(), 0.toFloat(), 0.toFloat(), 0.toFloat())

    companion object {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        fun newInstance(n: Int): NumberFragment {
            val args = Bundle() // Bundle is key value pairs
            args.putSerializable("n", n as Serializable)
            val fragment = NumberFragment()

            // add init params via arguments
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            setStateFromOutState(savedInstanceState)
        }
    }

    fun setStateFromOutState(outState: Bundle) {
        coinName = outState.getString("coinname")
        val open = outState.getFloat("open")
        val high = outState.getFloat("high")
        val low = outState.getFloat("low")
        val volume = outState.getFloat("volume")
        val last = outState.getFloat("last")
        val bigvol = outState.getFloat("volume_30day")

        cryptoStatus = CryptoStatus(
            open,
            high,
            low,
            volume,
            last,
            bigvol
        )
    }

    override fun onSaveInstanceState(outState: Bundle?) {
       val nextOutState = saveState(Bundle())

       super.onSaveInstanceState(nextOutState)
    }

    fun saveState(bundle: Bundle): Bundle {
        val nextOutState = Bundle()
        nextOutState.putString("coinname", coinName)

        nextOutState.putFloat("open", cryptoStatus.open)
        nextOutState.putFloat("high", cryptoStatus.high)
        nextOutState.putFloat("low", cryptoStatus.low)
        nextOutState.putFloat("volume", cryptoStatus.volume)
        nextOutState.putFloat("last", cryptoStatus.last)
        nextOutState.putFloat("volume_30day", cryptoStatus.volume_30day)

        return nextOutState
    }

    fun mutateView(view: View): View {
        val nameText = view.findViewById<TextView>(R.id.name) as TextView
        val openText = view.findViewById<TextView>(R.id.open) as TextView
        val highText = view.findViewById<TextView>(R.id.high) as TextView
        val lowText = view.findViewById<TextView>(R.id.low) as TextView
        val volumeText = view.findViewById<TextView>(R.id.volume) as TextView
        val lastText = view.findViewById<TextView>(R.id.last) as TextView
        val vol30Text = view.findViewById<TextView>(R.id.volume_30day) as TextView

        nameText.text = coinName
        openText.text = cryptoStatus.open.toString()
        highText.text = cryptoStatus.high.toString()
        lowText.text = cryptoStatus.low.toString()
        volumeText.text = cryptoStatus.volume.toString()
        lastText.text = cryptoStatus.last.toString()
        vol30Text.text = cryptoStatus.volume_30day.toString()

        return view
    }

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
        val view: View = inflater!!.inflate(R.layout.fragment_number, container, false)
        val index = arguments.getSerializable("n") as Int

        if (savedInstanceState == null) {
            if (index > 3) {
                val host = resources.getString(R.string.wordServiceHost)
                Fuel.get("$host/word/$index").responseString { request, response, result ->
                  when (result) {
                      is Result.Failure -> {
                          val errorText = view.findViewById<TextView>(R.id.error) as TextView
                          errorText.text = "There was an error"
                      }
                      is Result.Success -> {
                          val data = result.get()
                          val errorText = view.findViewById<TextView>(R.id.error) as TextView
                          errorText.text = data

                      }
                  }
                }
            } else {
                coinName = resources.getStringArray(R.array.coins)[index]
                Fuel.get("https://api.gdax.com/products/$coinName/stats").responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val errorText = view.findViewById<TextView>(R.id.error) as TextView
                            errorText.text = "There was an error"
                        }
                        is Result.Success -> {
                            val data = result.get()
                            val cryptoAdapter = moshi.adapter(CryptoStatus::class.java)

                            val out = cryptoAdapter.fromJson(data)
                            if (out != null) {
                                cryptoStatus = out
                                saveState(Bundle())
                            }

                            mutateView(view)
                        }
                    }
                }
            }
        } else {
            setStateFromOutState(savedInstanceState)
        }

        return view
    }

    fun handleResponse(onSuccess: () -> Unit, onFailure: () -> Unit): Unit {

    }
}