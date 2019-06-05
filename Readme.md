# Babylon Android Tech Test


This is a sample project to loading the list of posts from server.


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
      
# GET http://jsonplaceholder.typicode.com/posts :
  Method : GET 
  

          
  

