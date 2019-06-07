package com.akashgarg.sample.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akashgarg.sample.R
import com.akashgarg.sample.model.post.PostResponseModel
import kotlinx.android.synthetic.main.adapter_item.view.*

open class UserPostAdapter(
    var mContext: Context,
    var data: List<PostResponseModel>,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<UserPostAdapter.MyViewHolder>() {

    private var inflater = LayoutInflater.from(mContext)!!

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): UserPostAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.adapter_item, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) data.size
        else 0
    }

    override fun onBindViewHolder(holder: UserPostAdapter.MyViewHolder, position: Int) {
        holder.tvTitle.text = data[position].title
            holder.tvTitle.setOnClickListener {
                Toast.makeText(mContext, "Position:$position", Toast.LENGTH_SHORT).show()
                listener.let {
                    it.onItemClick(data[position])
                }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.tvTitle!!
    }

    interface OnItemClickListener {
        fun onItemClick(responseModel: PostResponseModel)
    }
}