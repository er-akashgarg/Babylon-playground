package com.akashgarg.sample.presenter

import android.annotation.SuppressLint
import com.akashgarg.sample.model.post.PostResponseModel
import com.akashgarg.sample.restclient.apis.Api
import com.akashgarg.sample.ui.activity.MainActivity
import com.akashgarg.sample.utils.Urls
import com.akashgarg.sample.view.base.BaseView
import com.akashgarg.sample.view.postsivew.PostView
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(MockitoJUnitRunner::class)
class SinglePostPresenterTest {
    private val STRING_UNKNOWN_ERROR = "STRING_UNKNOWN_ERROR"

    @Mock
    lateinit var baseView: BaseView

    @Mock
    lateinit var postView: PostView

    @Mock
    lateinit var mActivity: MainActivity

    lateinit var retrofit: Retrofit

    lateinit var usersPostPresenter: SinglePostPresenter

    var pID = 1

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(Urls.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        mActivity.showLoading()
        usersPostPresenter = SinglePostPresenter(retrofit, baseView, postView, pID)
    }

    @Test
    fun getSinglePost_WhenApisInvoke() {
        getPost()
        mActivity.dismissLoading()
    }


    @SuppressLint("CheckResult")
    fun getPost() {

        // Create the testObserver for response
        val observer = TestObserver<PostResponseModel>()

        getPostsObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

//        Mockito.`when`(mActivity.getString(com.akashgarg.sample.R.string.error_msg)).thenReturn(STRING_UNKNOWN_ERROR)

        // dispose the Observer Or CleanUp
        observer.dispose()
    }

    private fun getPostsObservable(): Observable<PostResponseModel> {
        val api = retrofit.create(Api::class.java)
        return api.post(pID)
    }
}