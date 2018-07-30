package com.example.mahmouddiab.dazzlekitchen.api;

import com.example.mahmouddiab.dazzlekitchen.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by mahmoud.diab on 11/29/2017.
 */

public class ApiFactory {


    private static ApiClient clientServices;
    private static final String API_KEY = "*>]#k-5)b^q>vTWB";

    private ApiFactory() {

    }

    public static ApiClient createInstance() {
        if (clientServices != null) {
            return clientServices;
        } else {
            clientServices = new Retrofit.Builder().baseUrl(ApiClient.baseUrl).client(newClient(API_KEY))
                    .addConverterFactory(GsonConverterFactory.create(newGson()))
                    .build().create(ApiClient.class);
            return clientServices;

        }

    }

    private static OkHttpClient newClient(String apiKey) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(new HeadersInterceptor(API_KEY));
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(BODY));
        }
        return builder.build();


    }

    private static Gson newGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }


    private static final class HeadersInterceptor implements Interceptor {
        private final String apiKey;

        HeadersInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            // add default header
            builder.addHeader("X-Api-Key", apiKey);
            builder.addHeader("Accept", "application/json"); // config

            return chain.proceed(builder.build());

        }
    }
}
