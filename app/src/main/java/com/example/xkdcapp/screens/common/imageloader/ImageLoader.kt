package com.example.xkdcapp.screens.common.imageloader

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.xkdcapp.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ImageLoader @Inject constructor(private val activity: AppCompatActivity) {

    private fun getProgressDrawable(): CircularProgressDrawable {
        return CircularProgressDrawable(activity).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }

    private val requestOptions = RequestOptions()
        .placeholder(getProgressDrawable())
        .error(R.mipmap.ic_launcher_round)

    fun loadImage(imageUrl: String, target: ImageView) {
        Glide.with(activity)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .into(target)
    }
}