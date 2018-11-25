package com.dreamwalkers.elab_yang.mmk.adapter.appinfo

import android.content.Context
import android.content.Intent
import android.support.design.button.MaterialButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.activity.WebViewActivity
import com.dreamwalkers.elab_yang.mmk.consts.IntentConst
import com.dreamwalkers.elab_yang.mmk.model.appinfos.Developer

class DeveloperAdapter(val context: Context, private val developerList: ArrayList<Developer>) : RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DeveloperViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_developer_layout, p0, false)
        return DeveloperViewHolder(view)
    }

    override fun getItemCount(): Int {
        return developerList.size
    }

    override fun onBindViewHolder(p0: DeveloperViewHolder, p1: Int) {
        p0.itemName.text = developerList[p1].name
        p0.itemGithub.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(IntentConst.WEB_URL, developerList[p1].githubLink)
            context.startActivity(intent)
        }

    }

    inner class DeveloperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var itemGithub: MaterialButton = itemView.findViewById<View>(R.id.github_button) as MaterialButton
        internal var itemName: TextView = itemView.findViewById<View>(R.id.name_text_view) as TextView


    }
}