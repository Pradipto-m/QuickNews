package com.pradipto.quicknews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetroFit {

    @GET
    Call<NewsModal> getAllNews(@Url String url);
    @GET
    Call<NewsModal> getNewsByCategory(@Url String url);
}
