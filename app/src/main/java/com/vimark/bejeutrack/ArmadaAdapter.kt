package com.vimark.bejeutrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimark.bejeutrack.model.DeviceModel
import kotlinx.android.synthetic.main.item_armada.view.*

class ArmadaAdapter(private val items: List<DeviceModel>): RecyclerView.Adapter<ArmadaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_armada, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class ViewHolder(private val v: View): RecyclerView.ViewHolder(v) {
        fun bind(item: DeviceModel) {
            v.item_tv_armada.text = item.name
        }
    }
}