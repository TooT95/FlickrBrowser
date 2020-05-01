package mrj.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.lang.Exception


/**
 * Created by MrJ
 */

class GetFlickrJsonData(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<Photo>>() {

    private val TAG = "GetFlickrJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute ends")
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground starts")

        val photoList = ArrayList<Photo>()

        try {
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")
            for (i in 0 until itemsArray.length()) {
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorID = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("title")
                val jsonmedia = jsonPhoto.getJSONObject("media")
                val photoURL = jsonmedia.getString("m")
                val link = photoURL.replaceFirst("_m.jpg", "_b.jpg")
                val photoObject = Photo(title, author, authorID, link, tags, photoURL)
                photoList.add(photoObject)
                Log.d(TAG, ".doInBackground $photoObject")

            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e(TAG, ".doINBackground: Error processing Json data. ${ex.message}")
            cancel(true)
            listener.onError(ex)
        }
        Log.d(TAG, ".doINBackground ends")
        return photoList
    }
}