package eu.tutorial.fetchnewsapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import eu.tutorial.fetchnewsapi.R

class WelcomeSpinnerAdapter(
    context: Context,
    private val data: List<Pair<String, Int>>
) : ArrayAdapter<Pair<String, Int>>(context, R.layout.custom_spinner_welcome, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.custom_spinner_welcome, parent, false)

        val textView = view.findViewById<TextView>(R.id.id_text_welcome_spinner)
        val imageView = view.findViewById<ImageView>(R.id.id_image_welcome_spinner)

        val item = getItem(position)
        textView.text = item?.first
        imageView.setImageResource(item?.second ?: R.drawable.ic_launcher_background)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}