# Babylon Android Tech Test


This is a sample project to loading the list of posts from server. In this sample project, I would like to show how to implement MVP (Model-View-Presenter) pattern with using Dagger2 and also by Kotlin.

For the Android, with MVP, we are able to take most of logic out from the activities (or fragments). 

# What is MVP ? Why do we need?
In Android we have a problem arising from the fact that activities are closely coupled to interface and data access mechanisms.

The Model-View-Presenter pattern allows to separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen.

# Language: Kotlin : 
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

# There are some third party dependencies which is used in this application:

# SDP Dependencies for UI to All compatible devices
    implementation "com.intuit.sdp:sdp-android:$sdp_lib_version"

# DI(Dependency Injection) : Dagger2:
    implementation "com.google.dagger:dagger:$dagger_version"
    kapt "com.google.dagger:dagger-compiler:$dagger_version"
    
# Rx-java & Rx-android:
    implementation "io.reactivex.rxjava2:rxandroid:$rxandroid_version"
    implementation "io.reactivex.rxjava2:rxjava:$rxjava_version"
    implementation "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:$rxjava_adapter_version"
    
# Retrofit(Network Lib):     
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    
# RxJava2CallAdapter
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"    
    
# Dagger2 Lib Dependencies
     implementation "com.google.dagger:dagger:$dagger_version"
     annotationProcessor "com.google.dagger:dagger-compiler:$dagger_version"
     implementation "com.google.dagger:dagger-android:$dagger_version"
     implementation "com.google.dagger:dagger-android-support:$dagger_version"
     annotationProcessor "com.google.dagger:dagger-android-processor:$dagger_version"
     
# Proguard-: for Debug Mode:   
     minifyEnabled  true
     shrinkResources  true
     proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro' 
  
# Lets try to setup MVP architecture with Kotlin, RxJava2, Retrofit, Dagger2 in app .
  
  ##This project structured into following setup or packages like :
  
  **. Retrofit:** is a Rest Client. Retrofit depend on OkHttp.
  
  **. Dagger(di):** used for dependency injection.
  
  **. RxJava:** Reactive Extensions.
  
  **. models:** Data models.
  
  **. ui:** Activities and also with presenter and contract.
  
  **. utils:** Some tweaks.
  
  
  ##We need to create Api Module for Retrofit Setup
  
  addConvertorFactory()- I used gson converter for data serialization but if you want, you can create custom convertor, use moshi library or etc.
  
  addCallAdapterFactory() support service method return types. I added RxJava2CallAdapterFactory.create()
  
  **Lets start to create the api module!**
  
     @Module
     public class ApiModule {
      private String mBaseUrl;
  
      public ApiModule(String mBaseUrl) {
          this.mBaseUrl = mBaseUrl;
      }
  
  
      @Provides
      @Singleton
      Cache provideHttpCache(Application application) {
          int cacheSize = 10 * 1024 * 1024;
          Cache cache = new Cache(application.getCacheDir(), cacheSize);
          return new Cache();
      }
  
      @Provides
      @Singleton
      Gson provideGson() {
          GsonBuilder gsonBuilder = new GsonBuilder();
          gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
          return gsonBuilder.create();
      }
  
      @Provides
      @Singleton
      OkHttpClient provideOkhttpClient(Cache cache) {
          OkHttpClient.Builder client = new OkHttpClient.Builder();
         client.cache(cache);
          return client.build();
      }
  
      @Provides
      @Singleton
      Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
          return new Retrofit.Builder()
                  .addConverterFactory(GsonConverterFactory.create(gson))
                  .baseUrl(mBaseUrl)
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                  .client(okHttpClient)
                  .build();
      }
  }
  
  **We need to make an api call so create Service interface. I used Observable for this.**  
  
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
  
  }
  
  **Now, we can go on Presenter Layer, Contract for View and Presenter interfaces…**
  
       interface BaseView {
        fun showLoading()
        fun dismissLoading()
    }
     interface PostView {
         fun successResult(data:Any )
         fun failure(message: String?)
     }
     
 **Lets create the presenter class:**
 
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

**ApplicationComponent class: Finally We can bind it with Builder.**

    import com.akashgarg.sample.di.module.ApiModule;
    import com.akashgarg.sample.di.module.AppModule;
    import com.akashgarg.sample.ui.activity.MainActivity;
    import dagger.Component;
    import javax.inject.Singleton;

    @Singleton
    @Component(modules = {AppModule.class, ApiModule.class})
    public interface ApiComponent {
       void inject(MainActivity activity);
     }    

  **API URL-:**  http://jsonplaceholder.typicode.com/posts
  **Method :**  GET        
  

