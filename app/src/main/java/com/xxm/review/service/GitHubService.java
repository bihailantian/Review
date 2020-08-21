package com.xxm.review.service;



import com.xxm.review.domain.Repo;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("weather/json.shtml")
    Call<ResponseBody> weatherOfCity(@Query("city") String city);

    @GET
    Call<ResponseBody> weatherOfCity();
    @GET
    Observable<Repo> weatherOfCity2();

}
