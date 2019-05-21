package altayiskender.movieapp.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.loadImage(imageUrl: String) {
    if (!imageUrl.isEmpty()) {
        Picasso.get().load(imageUrl).into(this)
    }
}