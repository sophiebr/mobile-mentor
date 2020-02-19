package edu.rosehulman.brusniss.mobilementor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import android.util.Log
import java.io.IOException

object BitmapUtils {
    fun rotateAndScaleByRatio(context: Context, localPath: String, ratio: Int): Bitmap? {
        Log.d(Constants.TAG, "Rotating and scaling by ratio: $localPath")
        return if (localPath.startsWith("content")) {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(localPath))
            // android-developers.googleblog.com/2016/12/introducing-the-exifinterface-support-library.html
            // stackoverflow.com/questions/34696787/a-final-answer-on-how-to-get-exif-data-from-uri
            var exif: androidx.exifinterface.media.ExifInterface? = null
            try {
                val inputStream = context.contentResolver.openInputStream(Uri.parse(localPath))!!
                exif = androidx.exifinterface.media.ExifInterface(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return rotateAndScaleBitmapByRatio(exif, bitmap, ratio)
        } else if (localPath.startsWith("/storage")) {
            val bitmap = BitmapFactory.decodeFile(localPath)
            var exif: androidx.exifinterface.media.ExifInterface? = null
            try {
                exif = androidx.exifinterface.media.ExifInterface(localPath)
            } catch (e: IOException) {
                Log.e(Constants.TAG, "Exif error: $e")
            }
            return rotateAndScaleBitmapByRatio(exif, bitmap, ratio)
        } else {
            null
        }
    }

    private fun rotateAndScaleBitmapByRatio(exif: androidx.exifinterface.media.ExifInterface?, bitmap: Bitmap, ratio: Int): Bitmap {
        val photoW = bitmap.width
        val photoH = bitmap.height
        val bm = Bitmap.createScaledBitmap(bitmap, photoW / ratio, photoH / ratio, true)

        //guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media#rotating-the-picture
        val orientString = exif?.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION)
        val orientation =
            if (orientString != null) Integer.parseInt(orientString) else androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL
        var rotationAngle = 0
        if (orientation == androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90
        if (orientation == androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180
        if (orientation == androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270
        Log.d(Constants.TAG, "Rotation angle: $rotationAngle")

        // Rotate Bitmap
        val matrix = Matrix()
        matrix.setRotate(rotationAngle.toFloat(), bm.width.toFloat() / 2, bm.height.toFloat() / 2)
        return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
    }
}