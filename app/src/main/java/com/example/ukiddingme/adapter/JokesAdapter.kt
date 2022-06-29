package com.example.ukiddingme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ukiddingme.databinding.CardItemBinding
import com.example.ukiddingme.model.Jokes

class JokesAdapter(
    private val jokesSet : MutableList<Jokes> = mutableListOf(),
    private val onCardClicked: (String?) -> Unit
) : RecyclerView.Adapter<JokesViewHolder>() {

    fun updateNewJokes(newJokes: List<Jokes>){
        jokesSet.clear()
        jokesSet.addAll(newJokes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder =
        JokesViewHolder(
            CardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int)=
        holder.bind(jokesSet[position],onCardClicked)

    override fun getItemCount(): Int = jokesSet.size

}

class JokesViewHolder(
    private val binding: CardItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Jokes,onCardClicked: (String?) -> Unit) {
        binding.cardId.text = card.id.toString()
        binding.cardJoke.text = card.joke

        itemView.setOnClickListener{onCardClicked(card.id.toString())}
    }
}