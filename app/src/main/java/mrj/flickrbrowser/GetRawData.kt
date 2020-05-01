package mrj.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by MrJ
 */

enum class DownloadStatus {
    OK, IDLE, NOT_INSTALLED, FAILED_OR_EMPTY, PERMISSION_ERROR, ERROR
}

class GetRawData(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private val TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String, status: DownloadStatus)
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called, with parametr $result")
        listener?.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params == null) {
            downloadStatus = DownloadStatus.NOT_INSTALLED
            return "No Url specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (ex: Exception) {
            val errorMessage = when (ex) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INSTALLED
                    "doInBackground: Invalid URL ${ex.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data ${ex.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSION_ERROR
                    "doInBackground: Security exception: Needs permission ${ex.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "Unknown error ${ex.message}"
                }
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }
}