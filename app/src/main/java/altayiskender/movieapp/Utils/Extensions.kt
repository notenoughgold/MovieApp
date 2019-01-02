package altayiskender.movieapp.Utils

import android.widget.ImageView
import com.squareup.picasso.Picasso


fun ImageView.loadImage(imageUrl: String, imageView: ImageView) {
    if (!imageUrl.isEmpty()) {
        Picasso.get().load(imageUrl).into(imageView)
    }
}