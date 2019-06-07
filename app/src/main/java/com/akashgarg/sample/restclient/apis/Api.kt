package com.akashgarg.sample.restclient.apis

import com.akashgarg.sample.model.post.PostResponseModel
import com.akashgarg.sample.model.user.UsersResponseModel
import com.akashgarg.sample.utils.Urls
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface Api {

    @get:GET(Urls.POSTS_URL)
    val postsList: Observable<List<PostResponseModel>>

    @GET(Urls.POSTS_URL+"/{id}")
    fun post(@Path("id") id: Int): Observable<PostResponseModel>

    @GET(Urls.UERS_URL)
    fun getUserList(): Observable<List<UsersResponseModel>>


//    @GET(Urls.COMMENTS_URL)
//    fun getUserList(): Observable<List<UsersResponseModel>>


}
