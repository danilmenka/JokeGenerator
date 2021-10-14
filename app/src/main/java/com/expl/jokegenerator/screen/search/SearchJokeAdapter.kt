package com.expl.jokegenerator.screen.search

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expl.jokegenerator.R
import com.expl.jokegenerator.model.IsJokeSaved
import com.expl.jokegenerator.model.Joke

class SearchJokeAdapter(val clickSave: (joke: Joke) -> Unit,val clickDelete: (joke: Joke) -> Unit)
    : RecyclerView.Adapter<SearchJokeAdapter.MainHolder>() {

    private var isJokeSavedList = emptyList<IsJokeSaved>()

    class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textJoke: TextView = view.findViewById(R.id.item_search_text)
        val imgSave: ImageView = view.findViewById(R.id.item_img_search_save)
    }

    override fun onViewAttachedToWindow(holder: MainHolder) {
        holder.imgSave.setOnClickListener {
            if (isJokeSavedList[holder.bindingAdapterPosition].saved)
                    try {
                        clickDelete(isJokeSavedList[holder.bindingAdapterPosition].joke)
                    }
                    catch (e:Exception){
                        Log.e("Database", "Delete error")
                    }
                else
                    try {
                        clickSave(isJokeSavedList[holder.bindingAdapterPosition].joke)
                    }
                    catch (e:Exception){
                        Log.e("Database", "Save error")
                    }
        }
    }

    override fun onViewDetachedFromWindow(holder: MainHolder) {
        holder.imgSave.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joke_search, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int = isJokeSavedList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.textJoke.text = isJokeSavedList[position].joke.value

        if(isJokeSavedList[position].saved)
            holder.imgSave.setBackgroundResource(R.drawable.ic_done)
        else
            holder.imgSave.setBackgroundResource(R.drawable.ic_save)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<IsJokeSaved>) {
        isJokeSavedList = list
        notifyDataSetChanged()
    }

}