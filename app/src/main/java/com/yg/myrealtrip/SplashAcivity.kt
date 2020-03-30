package com.yg.myrealtrip

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.splash_activity.*

class SplashAcivity : AppCompatActivity() {

    var result: ArrayList<Bitmap> = ArrayList()
    var maskBitmap: ArrayList<Bitmap> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        maskingProcess()

/*
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 1500)*/
    }

    //TODO Glide CropCircleTransformation와 차이?
    fun maskingProcess() {
        try {
            var original0: Bitmap
            var original1: Bitmap
            var original2: Bitmap
            var mask: Bitmap

            original0 = BitmapFactory.decodeResource(resources, R.drawable.newspaper)
            original1 = BitmapFactory.decodeResource(resources, R.drawable.rss)
            original2 = BitmapFactory.decodeResource(resources, R.drawable.internet)
            mask = BitmapFactory.decodeResource(resources, R.drawable.mymask)

            if (original0 != null && original1 != null && original2 != null) {
                val iv0_width: Int = original0.width
                val iv0_height: Int = original0.height

                val iv1_width: Int = original1.width
                val iv1_height: Int = original1.height

                val iv2_width: Int = original2.width
                val iv2_height: Int = original2.height

                result.add(Bitmap.createBitmap(iv0_width, iv0_height, Bitmap.Config.ARGB_8888))
                result.add(Bitmap.createBitmap(iv1_width, iv1_height, Bitmap.Config.ARGB_8888))
                result.add(Bitmap.createBitmap(iv2_width, iv2_height, Bitmap.Config.ARGB_8888))
                maskBitmap.add(Bitmap.createScaledBitmap(mask, iv0_width, iv0_height, true))
                maskBitmap.add(Bitmap.createScaledBitmap(mask, iv1_width, iv1_height, true))
                maskBitmap.add(Bitmap.createScaledBitmap(mask, iv2_width, iv2_height, true))

                var canvas: ArrayList<Canvas> = ArrayList()
                canvas.add(Canvas(result[0]))
                canvas.add(Canvas(result[1]))
                canvas.add(Canvas(result[2]))

                val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_IN))

                canvas[0].drawBitmap(original0, 0F, 0F, null)
                canvas[0].drawBitmap(maskBitmap[0], 0F, 0F, paint)
                canvas[1].drawBitmap(original1, 0F, 0F, null)
                canvas[1].drawBitmap(maskBitmap[1], 0F, 0F, paint)
                canvas[2].drawBitmap(original2, 0F, 0F, null)
                canvas[2].drawBitmap(maskBitmap[2], 0F, 0F, paint)

                paint.setXfermode(null)
                paint.style = Paint.Style.STROKE
            }
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
        }

        image0.setImageBitmap(result[0])
        image1.setImageBitmap(result[1])
        image2.setImageBitmap(result[2])
    }
}