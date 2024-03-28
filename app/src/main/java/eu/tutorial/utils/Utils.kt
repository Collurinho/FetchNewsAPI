package eu.tutorial.utils

import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {

    fun loadImage(context: Context, imageUrl: String?, imageView: ImageView, errorDrawableResId: Int) {
        Glide.with(context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errorDrawableResId)
            )
            .into(imageView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val dateTime = LocalDateTime.parse(inputDate, formatter)
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    }
}