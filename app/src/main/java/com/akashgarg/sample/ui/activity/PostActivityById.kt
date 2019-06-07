package com.akashgarg.sample.ui.activity

import android.os.Bundle
import android.util.Log
import com.akashgarg.sample.R
import com.akashgarg.sample.model.post.PostResponseModel
import kotlinx.android.synthetic.main.activity_post.*

class PostActivityById : BaseActivity() {
    var TAG = PostActivityById::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val postResponse = intent.extras!!.getSerializable("data") as PostResponseModel
        Log.e(TAG, "----postResponse--:  $postResponse")
        setData(postResponse)
    }

    private fun setData(postResponse: PostResponseModel) {
        tvTitle.text = postResponse.title
        tvBody.text = postResponse.body
    }
}