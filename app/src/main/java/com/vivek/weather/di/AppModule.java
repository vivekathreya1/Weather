package com.vivek.weather.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.vivek.weather.R;
import com.vivek.weather.api.interceptor.NetworkConnectionInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vivek.weather.utils.Constants.BASE_URL;

@Module
public class AppModule {

    @Singleton
    @Provides
    static OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    static NetworkConnectionInterceptor  provideNetworkConInterceptor(Application application){
        return new NetworkConnectionInterceptor(application.getApplicationContext());
    }


    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(NetworkConnectionInterceptor networkConnectionInterceptor,
                                            OkHttpClient.Builder httpCLientBuilder ){
        return httpCLientBuilder.addInterceptor(networkConnectionInterceptor).
                readTimeout(15, TimeUnit.SECONDS). connectTimeout(20, TimeUnit.SECONDS).build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){
        return Glide.with(application).setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application){
        return ContextCompat.getDrawable(application, R.drawable.ic_launcher_foreground);
    }







}
