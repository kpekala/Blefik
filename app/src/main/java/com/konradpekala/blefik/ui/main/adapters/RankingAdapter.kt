package com.konradpekala.blefik.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.ui.main.ranking.RankingMvp
import kotlinx.android.synthetic.main.item_ranking.view.*


//Position in list is a position in ranking
class RankingAdapter(val users: ArrayList<User>, val mvpView: RankingMvp.View)
    : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.apply {
                textName.text = user.nick
                textPosition.text = (layoutPosition+1).toString()
                textGamesWon.text = "Wygrane gry: ${user.gamesWon}"
            }
        }
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view =  LayoutInflater.from(mvpView.getCtx())
            .inflate(R.layout.item_ranking,parent,false)
        return RankingViewHolder(view)
    }

    fun showUsers(users: List<User>){
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }


    override fun getItemCount() = users.size

}