package com.example.cookiteatit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookiteatit.databinding.CategoryRvItemBinding

class CategoryAdapter(var dataList:ArrayList<Recipe>,var context: Context):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: CategoryRvItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CategoryRvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.categoryImg
        Glide.with(context).load(dataList.get(position).img).into(holder.binding.categoryImg)
        holder.binding.categoryText.text=dataList.get(position).tittle
        var time = dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.binding.categoryTime.text=time.get(0)
        holder.itemView.setOnClickListener {
            var intent = Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    // Add this method to update the data in the adapter
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Recipe>) {
        dataList = newData as ArrayList<Recipe>
        notifyDataSetChanged()
    }
}