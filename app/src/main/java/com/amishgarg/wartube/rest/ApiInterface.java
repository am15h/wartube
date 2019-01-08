package com.amishgarg.wartube.rest;

import com.amishgarg.wartube.Model.YoutubeModels.ChannelResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("channels")
    Call<ChannelResponse> getSubs(@QueryMap Map<String, String> qMap);
}
