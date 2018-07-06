package com.example.dijonkariz.m_zito_hq.Network;

import com.example.dijonkariz.m_zito_hq.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitBuilder {
    //remember to change when uploaded. The Online URL(Wil be 000webhost)
    private static final String BASE_URL = "https://mzitohq.000webhostapp.com/";
    private final static OkHttpClient client = buildClient();
    private static Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Connection", "close");
                request = builder.build();
                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG)
        {
            builder.interceptors().add(interceptor);

            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client)
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create().asLenient())
                .build();
    }

    public static <T> T createService(Class<T> service)
    {
        return retrofit.create(service);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
