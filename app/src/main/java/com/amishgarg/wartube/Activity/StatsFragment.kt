package com.amishgarg.wartube.Activity


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.amishgarg.wartube.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

import androidx.lifecycle.Observer
import com.amishgarg.wartube.PicassoUtil
import com.amishgarg.wartube.ViewModels.StatsViewModel
import com.squareup.picasso.Picasso


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


    private lateinit var viewModel : StatsViewModel

    lateinit var subsPdp:TextView
    lateinit var  subsTS:TextView
    lateinit var  logoPDP : ImageView
    lateinit var  logoTS : ImageView
    lateinit var  diffTextView:TextView
    lateinit var  graphView:GraphView


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

        subsPdp= view!!.findViewById(R.id.subsPdp)
        subsTS = view!!.findViewById(R.id.subsTS)
        logoPDP  = view!!.findViewById<ImageView>(R.id.img_pdp)
        logoTS = view!!.findViewById<ImageView>(R.id.img_ts)
        diffTextView = view!!.findViewById(R.id.diff_text)
        graphView = view!!.findViewById(R.id.graph)
        PicassoUtil.loadImagePicasso("https://yt3.ggpht.com/3Ss-aMQD695qaWBSWMy1mt6aNrIs5kIlL78Ccf_YGO4OHV1txzdWGy5J5bCUu7-T5MXJT3_W=w1280-fcrop64=1,32b75a57cd48a5a8-nd-c0xffffffff-rj-k-no", view!!.findViewById(R.id.bannerP))
        PicassoUtil.loadImagePicasso("https://yt3.ggpht.com/pZI010B_jIyHwAM7qDEpxZ5Zc4zc7StRe5USIB_QKc17k9NW_oSy0afhxd_jgc7pM3Mqn6FP=w1280-fcrop64=1,32b75a57cd48a5a8-nd-c0xffffffff-rj-k-no", view!!.findViewById(R.id.bannerT))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val series = BarGraphSeries(arrayOf(DataPoint(0.2, 2.0), DataPoint(1.0, 5.0), DataPoint(2.0, 3.0)))
        graphView.addSeries(series)
        series.setDrawValuesOnTop(true)
        series.setSpacing(25);
        graphViewSettings(this!!.graphView!!)


        Picasso.get().load("https://yt3.ggpht.com/a-/AN66SAztY6oYWZnS1Cae9o4_msEE1H83Tld5cFtl3Q=s240-mo-c-c0xffffffff-rj-k-no").into(logoPDP);
        Picasso.get().load("https://yt3.ggpht.com/a-/AN66SAxPfKnfHAnAs0rOqaSwINOxDYJsyj-gPBP0OQ=s240-mo-c-c0xffffffff-rj-k-no").into(logoTS)

        viewModel = ViewModelProviders.of(this).get(StatsViewModel::class.java)



        val subsObserver: Observer<List<Int>> = Observer {
            subsPdp.text = it[0].toString()
            subsTS.text = it[1].toString()
            diffTextView.text = it[2].toString()
            var values = arrayOf(DataPoint(1.0, it[0].toDouble()), DataPoint(2.0, it[1].toDouble()), DataPoint(3.0, it[2].toDouble()))
            series.resetData(values)

            series.setValuesOnTopColor(R.color.colorPrimaryDark);
        }

        viewModel.subsData.observe(this, subsObserver)

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









