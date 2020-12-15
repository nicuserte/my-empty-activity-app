package ro.ubbcluj.cs.ilazar.myapp2.todo.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ro.ubbcluj.cs.ilazar.myapp2.todo.data.Bug

object BugApi {
    private const val URL = "http://192.168.0.106:3000/"

    interface Service {
        @GET("/bug")
        suspend fun find(): List<Bug>

        @GET("/bug/{id}")
        suspend fun read(@Path("id") itemId: String): Bug;

        @Headers("Content-Type: application/json")
        @POST("/bug")
        suspend fun create(@Body bug: Bug): Bug

        @Headers("Content-Type: application/json")
        @PUT("/bug/{id}")
        suspend fun update(@Path("id") itemId: String, @Body bug: Bug): Bug
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}