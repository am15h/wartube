package com.amishgarg.wartube.Activity


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amishgarg.wartube.Model.YoutubeModels.ChannelResponse
import com.amishgarg.wartube.R
import com.amishgarg.wartube.rest.ApiClient
import com.amishgarg.wartube.rest.ApiInterface
import com.bumptech.glide.Glide
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StatsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class StatsFragment : Fragment() {

    //todo: check internet
    //todo:test on small screens

    private val GOOGLE_YOUTUBE_API_KEY = "AIzaSyDK__bGjXkyQzUV--1jiLfpn3h4gRrUtK4"
    private val CHANNEL_ID_TS = "UCq-Fj5jknLsUf-MWSy4_brA"
    private val CHANNEL_ID_PDP = "UC-lHJZR3Gqxm24_Vd_AJ5Yw"
    var SUBS_TS = 0
    var SUBS_PDP = 0


    private lateinit var mRunnable:Runnable
    inline fun runnable(crossinline body: Runnable.() -> Unit) = object : Runnable {
        override fun run() = this.body()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_stats, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subsPdp:TextView = view.findViewById(R.id.subsPdp)
        val subsTS:TextView = view.findViewById(R.id.subsTS)
        val logoPDP : ImageView = view.findViewById<ImageView>(R.id.img_pdp)
        val logoTS : ImageView = view.findViewById<ImageView>(R.id.img_ts)
        val diffTextView:TextView = view.findViewById(R.id.diff_text)
        val graphView:GraphView = view.findViewById(R.id.graph)

        val series = BarGraphSeries(arrayOf(DataPoint(0.2, 2.0), DataPoint(1.0, 5.0), DataPoint(2.0, 3.0)))
        graphView.addSeries(series)
        series.setSpacing(25);
        graphViewSettings(graphView)

        Glide.with(this).load("https://yt3.ggpht.com/a-/AN66SAztY6oYWZnS1Cae9o4_msEE1H83Tld5cFtl3Q=s240-mo-c-c0xffffffff-rj-k-no").into(logoPDP);
        Glide.with(this).load("https://yt3.ggpht.com/a-/AN66SAxPfKnfHAnAs0rOqaSwINOxDYJsyj-gPBP0OQ=s240-mo-c-c0xffffffff-rj-k-no").into(logoTS)

        val qMap = HashMap<String, String>()
        qMap["part"] = "snippet,contentDetails,statistics"
        qMap["id"] = CHANNEL_ID_TS
        qMap["key"] = GOOGLE_YOUTUBE_API_KEY

        val qMap2 = HashMap<String, String>()
        qMap2["part"] = "snippet,contentDetails,statistics"
        qMap2["id"] = CHANNEL_ID_PDP
        qMap2["key"] = GOOGLE_YOUTUBE_API_KEY

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)


        fun getSubsData() : Unit
        {

            var call = apiService.getSubs(qMap)
            call.enqueue(object : Callback<ChannelResponse> {
                override fun onResponse(call: Call<ChannelResponse>, response: Response<ChannelResponse>) {

                    Log.d("URL:", response.raw().request().url().toString())
                    val channels = response.body()!!.channels
                    SUBS_TS = channels[0].statistics.subscriberCount
                    subsTS.text = SUBS_TS.toString()
                    Log.d("GEEK TS", SUBS_TS.toString() + "")


                }

                override fun onFailure(call: Call<ChannelResponse>, t: Throwable) {
                    Log.d("GEEK", t.toString())
                }
            })

            val call2 = apiService.getSubs(qMap2)
            call2.enqueue(object : Callback<ChannelResponse> {
                override fun onResponse(call: Call<ChannelResponse>, response: Response<ChannelResponse>) {

                    val channels = response.body()!!.channels
                    SUBS_PDP = channels[0].statistics.subscriberCount
                    subsPdp.text = SUBS_PDP.toString()
                    Log.d("GEEK", SUBS_PDP.toString() + "")
                    Log.d("GEEK", (SUBS_PDP - SUBS_TS).toString() + " Difference")
                    diffTextView.text = (SUBS_PDP - SUBS_TS).toString()
                    var values = arrayOf(DataPoint(0.5, SUBS_PDP.toDouble()), DataPoint(1.5, SUBS_TS.toDouble()), DataPoint(2.5, (SUBS_PDP-SUBS_TS).toDouble()))
                    series.resetData(values)
                   // graphView.addSeries(series)
                }

                override fun onFailure(call: Call<ChannelResponse>, t: Throwable) {
                    Log.d("GEEK", t.toString())
                }
            })

        }

        var shouldStopLoop = false

        val handler = Handler()
        val runnableCode = runnable {
            // do something
            getSubsData()
            Log.d("GEEK", "called getSubsData")
            if(!shouldStopLoop)
            {
                handler.postDelayed(this, 10000)
            }
        }
        handler.post(runnableCode)


    }


    fun graphViewSettings(graphView: GraphView)
    {
        // set manual X bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0.0);
        graphView.getViewport().setMaxY(100000000.0);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0.0);
        graphView.getViewport().setMaxX(4.0);
        graphView.getViewport().setScrollable(true); // enables horizontal scrolling
        graphView.getViewport().setScrollableY(true); // enables vertical scrolling
    }
}

