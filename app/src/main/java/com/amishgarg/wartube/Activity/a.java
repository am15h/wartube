package com.amishgarg.wartube.Activity;

import com.amishgarg.wartube.FirebaseUtil;
import com.amishgarg.wartube.Model.Post;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.Observable;
import androidx.lifecycle.Observer;


public class a {

    DataPoint[] values = {new DataPoint(0.5,3), new DataPoint(1.5,3), new DataPoint(2.5,3)};

    List<Post> posts = new ArrayList<>();


    final Observer<List<Integer>> subsObserver = new Observer<List<Integer>>() {
        @Override
        public void onChanged(List<Integer> integers) {

        }
    };
}
