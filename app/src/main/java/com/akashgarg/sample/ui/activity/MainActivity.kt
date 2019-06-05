package com.akashgarg.sample.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.akashgarg.sample.app.MyApplication
import com.akashgarg.sample.model.PostResponseModel
import com.akashgarg.sample.presenter.SinglePostPresenter
import com.akashgarg.sample.presenter.UsersPostPresenter
import com.akashgarg.sample.ui.adapter.UserPostAdapter
import com.akashgarg.sample.utils.NetworkUtils
import com.akashgarg.sample.utils.Space
import com.akashgarg.sample.view.base.BaseView
import com.akashgarg.sample.view.postsivew.PostView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import retrofit2.Retrofit
import javax.inject.Inject


class MainActivity : BaseActivity(), BaseView, PostView, UserPostAdapter.OnItemClickListener {

    var TAG = MainActivity::class.java.simpleName

    //injecting retrofit
    @Inject
    lateinit var retrofit: Retrofit
    private lateinit var adapter: UserPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akashgarg.sample.R.layout.activity_main)
        (application as MyApplication).netComponent.inject(this@MainActivity)

        if (NetworkUtils.isNetworkConnected(this@MainActivity))
            UsersPostPresenter(retrofit, this@MainActivity, this)
        else
            showToast("No Internet Connection !")
    }

    override fun showLoading() {
        progressBar?.let {
            it.visibility = View.VISIBLE
        }
    }

    override fun dismissLoading() {
        progressBar?.let {
            it.visibility = View.GONE
        }
    }

    override fun successResult(data: Any) {
        when (data) {
            is PostResponseModel -> {
                val intent = Intent(this@MainActivity, PostActivityById::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
            else -> {
                adapter = UserPostAdapter(this@MainActivity, data as List<PostResponseModel>, this)
                recyclerView.addItemDecoration(Space(20, 0))
                recyclerView.adapter = adapter
            }
        }
    }

    override fun failure(message: String?) {
        showToast(message!!)
        Log.e(TAG, "------message------: $message")
    }

    override fun onItemClick(responseModel: PostResponseModel) {
        Toast.makeText(this@MainActivity, "ID: ${responseModel.id}", Toast.LENGTH_SHORT).show()
        SinglePostPresenter(retrofit, this@MainActivity, this, responseModel.id!!)
    }
}
