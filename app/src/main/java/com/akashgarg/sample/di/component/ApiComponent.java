package com.akashgarg.sample.di.component;

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