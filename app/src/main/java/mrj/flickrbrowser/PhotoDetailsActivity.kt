package mrj.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.browse.*
import kotlinx.android.synthetic.main.browse.view.*
import kotlinx.android.synthetic.main.content_photo_details.*

class PhotoDetailsActivity : BaseActivity() {
    private val TAG = "PhotoDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolBar(true)
        val photo = intent.extras?.getParcelable<Photo>(PHOTO_TRANSFER) as Photo

        photo_title.text = photo.title
        photo_tags.text = photo.tags
        photo_author.text = photo.author
        Picasso.with(this)
            .load(photo.link)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(photo_image)

    }

}
