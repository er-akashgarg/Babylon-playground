package com.akashgarg.sample.restclient.apis

import com.akashgarg.sample.model.PostResponseModel
import com.akashgarg.sample.utils.Urls
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    @get:GET(Urls.POSTS_URL)
    val postsList: Observable<List<PostResponseModel>>

    @GET("posts/{id}")
    fun post(@Path("id") id: Int): Observable<PostResponseModel>

//    @GET("users")
//    fun getUserList(): Observable<List<User>>

}
