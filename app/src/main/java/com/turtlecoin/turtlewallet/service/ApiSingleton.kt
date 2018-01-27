package com.turtlecoin.turtlewallet.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.turtlecoin.turtlewallet.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.atomic.AtomicInteger


object ApiSingleton {
    private val retrofit: Retrofit
    val turtleApi: TurtleApi
    private var requestId: AtomicInteger = AtomicInteger(0)
    init {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        val client = builder.build()

        // currently in Strict configuration, will blow up if another value gets added to json
        val mapper = ObjectMapper().registerKotlinModule()

        retrofit = Retrofit.Builder()
                .baseUrl("http://us.turtlepool.space:11899/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
        turtleApi = retrofit.create(TurtleApi::class.java)
    }

    fun getRequestId()= requestId.getAndAdd(1)

}

