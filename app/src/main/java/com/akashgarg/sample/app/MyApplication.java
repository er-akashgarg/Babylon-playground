package com.akashgarg.sample.app;


import android.app.Application;
import com.akashgarg.sample.di.component.ApiComponent;
import com.akashgarg.sample.di.component.DaggerApiComponent;
import com.akashgarg.sample.di.module.ApiModule;
import com.akashgarg.sample.di.module.AppModule;
import com.akashgarg.sample.utils.Urls;

public class MyApplication extends Application {
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(Urls.BASE_URL))
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}
