package com.template.samplestickerapp.main.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar



fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}


fun Context.transitionAnimationBundle(): Bundle {

    return ActivityOptions.makeCustomAnimation(
        this,
        android.R.anim.fade_in,
        android.R.anim.fade_out
    ).toBundle()
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}


fun Activity.setFullScreen() {


    this.window.decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_IMMERSIVE
    )


}

fun Activity.setFullScreenWithBtmNav() {


    this.window.decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_IMMERSIVE
    )


}

fun Context.share(link: String?) {

    val shareIntent = Intent()

    shareIntent.action = Intent.ACTION_SEND
    shareIntent.setType("text/plain");




    shareIntent.putExtra(
        Intent.EXTRA_TEXT,
        "Check out the App at: https://play.google.com/store/apps/developer?id=Pranav+Elric"
    )
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Pranav Elric");


    this.startActivity(Intent.createChooser(shareIntent, "Share to"))

}


fun Activity.setFullScreenForNotch() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
    }

}


fun Activity.adjustToolBarMarginForNotchDevices(toolbar: Toolbar) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val windowInsets = this.window.decorView.rootWindowInsets
        if (windowInsets != null) {
            val displayCutout = windowInsets.displayCutout
            if (displayCutout != null) {
                val safeInsetTop = displayCutout.safeInsetTop
                val newLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
                newLayoutParams.setMargins(0, safeInsetTop, 0, 0)
                toolbar.layoutParams = newLayoutParams

            }
        }

    }

}


fun ViewPager2.setShowSideItems2(pageMarginPx: Int, offsetPx: Int) {

    clipToPadding = false
    clipChildren = false
    offscreenPageLimit = 3


    setPageTransformer { page, position ->

        val myOffset: Float = position * (-(2 * offsetPx + pageMarginPx))
        if (position < -1) {
            page.translationX = -myOffset

        } else if (position <= 1) {
            val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
            page.translationX = myOffset
            page.scaleY = scaleFactor
            page.alpha = scaleFactor
        } else {
            page.alpha = 0F;
            page.translationX = myOffset;
        }

    }


}




fun Context.openInBrowser(url: String) {
    var myUrl = url
    if (!myUrl.startsWith("http://") && !myUrl.startsWith("https://"))
        myUrl = "http://$url";
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(myUrl))
    this.startActivity(browserIntent)

}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}




