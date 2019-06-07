package com.akashgarg.sample.presenter

import com.akashgarg.sample.model.PostResponseModel
import com.akashgarg.sample.restclient.apis.Api
import com.akashgarg.sample.view.base.BaseView
import com.akashgarg.sample.view.postsivew.PostView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit


@RunWith(MockitoJUnitRunner::class)
class UsersPostPresenterTest {

    @Mock
    lateinit var retrofit: Retrofit

    @Mock
    lateinit var baseView: BaseView

    @Mock
    lateinit var postView: PostView


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        baseView.showLoading()
    }

    @Test
    fun getPost() {
        getPostsList()
        baseView.dismissLoading()
    }

    @Test
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
        return  api.postsList
    }
}

