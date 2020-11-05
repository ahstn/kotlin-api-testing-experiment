package payment.retrofit

import model.Response
import model.Charge
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @Headers("Content-Type: application/json")
    @POST("/payment")
    fun postPayment(@Body charge: Charge): Call<Response>

    companion object {
        fun create(): Api {
            val retrofit = Retrofit.Builder()
                    // here we set the base url of our API
                    .baseUrl("http://localhost:3000")
                    // add the JSON dependency so we can handle json APIs
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            // here we pass a reference to our API interface
            // and get back a concrete instance
            return retrofit.create(Api::class.java)
        }
    }
}