package com.cartrackers.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.NonNull
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageUtils {
    companion object {
        // https://stackoverflow.com/a/7780289/1076574
        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun saveBitmap(ctx: Context, bitmap: Bitmap, imageName: String): File {
            val filename = "${imageName}.JPG"
            val file = File(ctx.cacheDir, filename)
            file.createNewFile()

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bitmapData = byteArrayOutputStream.toByteArray()

            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(bitmapData)
            fileOutputStream.flush()
            fileOutputStream.close()
            return file
        }

        fun invertImageRun(src: Bitmap): Bitmap? {
            // create new bitmap with the same attributes(width,height)
            //as source bitmap
            val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
            // color info
            var alpha: Int
            var red: Int
            var green: Int
            var blue: Int
            var pixelColor: Int
            // image size
            val height = src.height
            val width = src.width
            // scan through every pixel
            for (y in 0 until height) {
                for (x in 0 until width) {
                    // get one pixel
                    pixelColor = src.getPixel(x, y)
                    // saving alpha channel
                    alpha = Color.alpha(pixelColor)
                    // inverting byte for each R/G/B channel
                    red = 255 - Color.red(pixelColor)
                    green = 255 - Color.green(pixelColor)
                    blue = 255 - Color.blue(pixelColor)
                    // set newly-inverted pixel to output image
                    bmOut.setPixel(x, y, Color.argb(alpha, red, green, blue))
                }
            }
            // return final bitmap
            return bmOut
        }

        fun invertImage(src: Bitmap): Bitmap? {
            val height = src.height
            val width = src.width
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            val matrixGrayscale = ColorMatrix()
            matrixGrayscale.setSaturation(0f)
            val matrixInvert = ColorMatrix()
            matrixInvert.set(
                floatArrayOf(
                    -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
                    0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
                    0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
                    0.0f, 0.0f, 0.0f, 1.0f, 0.0f
                )
            )
            matrixInvert.preConcat(matrixGrayscale)
            val filter = ColorMatrixColorFilter(matrixInvert)
            paint.colorFilter = filter
            val rec = Rect(0,0,0,0)
            canvas.drawBitmap(src, rec, rec, paint)
            return bitmap
        }

        fun hasImage(@NonNull view: ImageView): Boolean {
            return when {
                view.drawable != null -> {
                    val drawable: Drawable = view.drawable
                    var hasImage = true
                    if (hasImage && drawable is BitmapDrawable) {
                        hasImage = drawable.bitmap != null
                    }
                    hasImage
                }
                else -> false
            }
        }

        private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e("Http Error ###: $throwable")
        }

        suspend fun getCoilBitmap(coroutineScope: CoroutineScope, context: Context, url: String) : Bitmap? {
            return coroutineScope.async(exceptionHandler) {
                return@async withContext(Dispatchers.IO) {
                    val loader = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(url)
                        .allowHardware(false) // Disable hardware bitmaps.
                        .build()
                    val result = (loader.execute(request) as SuccessResult).drawable
                    val bitmap = (result as BitmapDrawable).bitmap
                    bitmap
                }
            }.await()
       }
    }
}
