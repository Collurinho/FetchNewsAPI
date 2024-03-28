package eu.tutorial.fetchnewsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.tutorial.fetchnewsapi.R
import eu.tutorial.fetchnewsapi.model.NewsItem

class NewsAdapter(
    private val context: Context,
    private var newsList: List<NewsItem>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(newsItem: NewsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun submitList(list: List<NewsItem>) {
        newsList = list
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.id_titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.id_descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.id_imageView)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            descriptionTextView.text = newsItem.description
            Glide.with(context)
                .load(newsItem.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.baseline_warning_amber_24)
                .into(imageView)

            itemView.setOnClickListener {
                itemClickListener.onItemClick(newsItem)
            }
        }
    }
}