package com.akashgarg.sample.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.akashgarg.sample.R

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finishAffinity()
        }, 2000)
    }
}