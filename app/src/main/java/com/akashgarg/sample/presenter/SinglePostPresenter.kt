package com.akashgarg.sample.presenter

import android.annotation.SuppressLint
import com.akashgarg.sample.model.post.PostResponseModel
import com.akashgarg.sample.restclient.apis.Api
import com.akashgarg.sample.view.base.BaseView
import com.akashgarg.sample.view.postsivew.PostView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class SinglePostPresenter(
    private var retrofit: Retrofit,
    var baseView: BaseView,
    var postView: PostView,
    var pid: Int) {

    private var TAG = SinglePostPresenter::class.java.simpleName

    init {
        baseView.showLoading()
        getPost()
    }


    @SuppressLint("CheckResult")
    fun getPost() {
        getPostsObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())
    }

    private fun getObserver(): DisposableObserver<PostResponseModel> {
        return object : DisposableObserver<PostResponseModel>() {

            override fun onNext(data: PostResponseModel) {
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

    private fun getPostsObservable(): Observable<PostResponseModel> {
        val api = retrofit.create(Api::class.java)
        return api.post(pid)
    }
}
