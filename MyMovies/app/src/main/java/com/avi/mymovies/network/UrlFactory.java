package com.avi.mymovies.network;

import com.avi.mymovies.network.objects.responses.MovieCastResponse;
import com.avi.mymovies.network.objects.responses.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by avibarel on 29/10/2017.
 */

public class UrlFactory {

    private UrlFactory() {
        // private constructor
    }

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    public interface API {

//        @GET("region/{region}")
//        authentication/token/new?api_key=
//        Call<TokenResponse> requestToken(@Path("region") String region);

//        @GET("alpha")
//        Call<List<Country>> getCountriesByCodes(@Query("codes") String codes);

        @GET("movie/top_rated")
        Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

        @GET("movie/{movie_id}/casts")
        Call<MovieCastResponse> getMovieCast(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    }

}
