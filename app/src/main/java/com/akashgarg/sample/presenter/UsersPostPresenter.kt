package com.akashgarg.sample.presenter

import android.annotation.SuppressLint
import com.akashgarg.sample.restclient.apis.Api
import com.akashgarg.sample.model.PostResponseModel
import com.akashgarg.sample.view.base.BaseView
import com.akashgarg.sample.view.postsivew.PostView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class UsersPostPresenter(private var retrofit: Retrofit, var baseView: BaseView, var postView: PostView) {

    private var TAG = UsersPostPresenter::class.java.simpleName

    init {
        baseView.showLoading()
        getPostsList()
    }


    @SuppressLint("CheckResult")
    fun getPostsList() {
        getPostsObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())
    }

    private fun getObserver(): DisposableObserver<List<PostResponseModel>> {
        return object : DisposableObserver<List<PostResponseModel>>() {

            override fun onNext(data: List<PostResponseModel>) {
                baseView.dismissLoading()
                postView.successResult(data)
            }

            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
                postView.failure(e.message)
            }
        }
    }

    private fun getPostsObservable(): Observable<List<PostResponseModel>> {
        val api = retrofit.create(Api::class.java)
        return api.postsList
    }
}