package com.expl.jokegenerator.screen.saved

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expl.jokegenerator.R
import com.expl.jokegenerator.model.Joke

class SavedJokeAdapter(val clickDelete: (joke:Joke) -> Unit) : RecyclerView.Adapter<SavedJokeAdapter.MainHolder>() {

    private var mListJokes = emptyList<Joke>()

    class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textJoke: TextView = view.findViewById(R.id.item_saved_text)
        val btnSave: ImageView = view.findViewById(R.id.item_btn_saved_delete)
    }

    override fun onViewAttachedToWindow(holder: MainHolder) {
        holder.btnSave.setOnClickListener {
            try {
                clickDelete(mListJokes[holder.bindingAdapterPosition])
            }
            catch (e:Exception){
                Log.e("Database", "Delete error")
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: MainHolder) {
        holder.btnSave.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joke_saved, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int = mListJokes.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.textJoke.text = mListJokes[position].value
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Joke>) {
        mListJokes = list
        notifyDataSetChanged()
    }

}