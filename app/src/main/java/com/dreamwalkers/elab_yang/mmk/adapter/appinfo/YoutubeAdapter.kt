package com.dreamwalkers.elab_yang.mmk.adapter.appinfo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.model.YoutubeItem

class YoutubeAdapter(private val mDataList: List<YoutubeItem>) : RecyclerView.Adapter<YoutubeAdapter.ViewHolder>() {
    private var mListener: YoutubeViewClickListener? = null

    // 뷰 홀더를 생성하는 부분. 레이아웃을 만드는 부분
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.youtube_item, parent, false)
        return ViewHolder(view)
    }

    // 뷰 홀더에 데이터를 설정하는 부분
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDataList[position]

        holder.itemImage.setImageResource(item.image)
        holder.itemName.text = item.text

        // 클릭 이벤트
        if (mListener != null) {
            // 현재 위치
            val pos = holder.adapterPosition
            holder.itemView.setOnClickListener { mListener!!.onItemClicked(pos) }
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var itemImage: ImageView
        internal var itemName: TextView

        init {
            itemImage = itemView.findViewById<View>(R.id.itemImage) as ImageView
            itemName = itemView.findViewById<View>(R.id.itemName) as TextView
        }
    }

    fun setOnClickListener(listener: YoutubeViewClickListener) {
        mListener = listener
    }

    interface YoutubeViewClickListener {
        fun onItemClicked(position: Int)
    }

    companion object {
        private val TYPE_FOOTER = 3
    }
}
