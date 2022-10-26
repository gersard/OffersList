package cl.gersard.offerslist.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Load an Image from a URL String into an ImageView
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}