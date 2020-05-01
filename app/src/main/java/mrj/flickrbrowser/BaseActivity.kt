package mrj.flickrbrowser

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by MrJ
 */
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"
internal const val FLICKR_QUERY = "FLICKR_QUERY"

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private val TAG = "BaseActivity"

    internal fun activateToolBar(enableHome:Boolean){
        Log.d(TAG,".activateToolBar starts")
        var toolbar = findViewById<View>(R.id.toolbar)
        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }

}
