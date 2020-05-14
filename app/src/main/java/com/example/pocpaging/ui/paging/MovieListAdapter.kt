package com.example.pocpaging.ui.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pocpaging.R
import com.example.pocpaging.data.model.Movie
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieListAdapter :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(DiffUtil) {


    companion object {

        val FOOTER = 1
        val FOOTER_RETRY = 2
        val ROW_ITEM = 3


        val DiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    var pagedListFooter: PageListFooter = PageListFooter.NONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ROW_ITEM) {
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_movie_list,
                    parent,
                    false
                )
            )
        } else if (viewType == FOOTER) {
            FooterLoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.load_more_indecator,
                    parent,
                    false
                )
            )
        } else {
            FooterRetryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.retry_loading_item,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            FOOTER -> {
                (holder as FooterLoadingViewHolder)
            }
            FOOTER_RETRY -> {
//                (holder as FooterRetryViewHolder).bind(retryListener)
            }
            ROW_ITEM -> {
                (holder as MovieViewHolder).bind(getItem(position)!!)
            }

        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount() && getItem(position) != null)
            ROW_ITEM
        else if (pagedListFooter == PageListFooter.RETRY)
            FOOTER_RETRY
        else
            FOOTER
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }


    fun setFooter(pageListFooter: PageListFooter) {
        if (this.pagedListFooter != pageListFooter) {
            this.pagedListFooter = pageListFooter
            notifyDataSetChanged()
        }
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 &&
                ((pagedListFooter is PageListFooter.LOADING) || (pagedListFooter is PageListFooter.RETRY))
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            movie.let {
                Glide.with(view.iv_poster.context)
                    .load("http://image.tmdb.org/t/p/w500" + it.posterPath)
                    .centerCrop()
                    .into(view.iv_poster)
            }

        }


    }


    class FooterLoadingViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    class FooterRetryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            //pass
        }
    }

}

sealed class PageListFooter {
    object LOADING : PageListFooter()
    object RETRY : PageListFooter()
    object NONE : PageListFooter()

}