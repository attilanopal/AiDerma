package com.example.aiderma.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.nio.ByteBuffer
import com.example.aiderma.R
import com.example.aiderma.api.response.DiseasesItem

class DiseaseAdapter(private var listDisease: List<DiseasesItem>) : RecyclerView.Adapter<DiseaseAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: DiseasesItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_item_titleDisease)
        val tvDesc: TextView = view.findViewById(R.id.tv_item_descDisease)
        val ivImg: ImageView = view.findViewById(R.id.iv_item_disease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_disease,parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = listDisease[position]

        holder.tvTitle.text = disease.namaPenyakit
        holder.tvDesc.text = disease.deskripsi


        val intArray = disease.gambar?.data?.toIntArray()
        val byteArray = intArray?.map { it.toByte() }?.toByteArray()

        val bitmap = byteArray?.let { BitmapFactory.decodeByteArray(byteArray, 0, it.size) }

        holder.ivImg.setImageBitmap(bitmap)



        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listDisease[position])
        }
    }

    override fun getItemCount(): Int {
        return listDisease.size
    }

    fun updateData(newData: List<DiseasesItem>) {
        listDisease = newData
        notifyDataSetChanged()
    }

}