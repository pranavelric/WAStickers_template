package com.template.samplestickerapp.main.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.royrodriguez.transitionbutton.TransitionButton
import com.template.samplestickerapp.EntryActivity
import com.template.samplestickerapp.R
import com.template.samplestickerapp.databinding.ActivityMainBinding
import com.template.samplestickerapp.main.utils.ResponseState
import com.template.samplestickerapp.main.utils.gone
import com.template.samplestickerapp.main.utils.transitionAnimationBundle
import com.template.samplestickerapp.main.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private var nativeAd: UnifiedNativeAd? = null
    lateinit var binding: ActivityMainBinding


    private val moreAppsViewModel: MoreAppsViewModel by lazy {
        ViewModelProvider(this)[MoreAppsViewModel::class.java]
    }

    //  lateinit var advertisementLink: String
    // lateinit var advertisementUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainAct = this
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.app_name)




        binding.transitionButton.setOnClickListener {
            binding.transitionButton.startAnimation()


            // Do your networking task or background work here.
            val handler = Handler()
            handler.postDelayed(Runnable {
                val isSuccessful = true

                // Choose a stop animation if your call was succesful or not
                if (isSuccessful) {
                    binding.transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND,
                            TransitionButton.OnAnimationStopEndListener {

                                Intent(this@MainActivity, EntryActivity::class.java).also {
                                    startActivity(it, transitionAnimationBundle())
                                }

                                binding.transitionButton.stopAnimation(
                                        TransitionButton.StopAnimationStyle.SHAKE,
                                        null
                                )

                            })
                } else {
                    binding.transitionButton.stopAnimation(
                            TransitionButton.StopAnimationStyle.SHAKE,
                            null
                    )
                }
            }, 2000)
        }
        refreshAd()






        getData()



        MobileAds.initialize(this)

    }

    private fun getData() {


        moreAppsViewModel.getMyData()
        moreAppsViewModel.myData.observe(this, {
            when (it) {
                is ResponseState.Success -> {
                    //   binding.progressCircular.gone()

                    it.data?.let { moreApp ->
                        moreApp.apps?.let { app ->

                            var advertisementLink = ""
                            var advertisementUrl = ""

                            for (i in app) {

                                if (i.name == resources.getString(R.string.more_app_name)) {
                                    if (i.advertisement.ad_link != null)
                                        advertisementLink = i.advertisement.ad_link!!
                                    if (i.advertisement.ad_image != null)
                                        advertisementUrl = i.advertisement.ad_image!!
                                    if (advertisementLink != "" && advertisementUrl != "")
                                        setImageData(advertisementUrl, advertisementLink)
                                    break
                                }
                            }

                        }


                    }


                }

                is ResponseState.Error -> {
                    //        binding.progressCircular.gone()

                }
                is ResponseState.Loading -> {
                    // binding.progressCircular.visible()
                }
            }


        })


    }

    private fun setImageData(madvertisementUrl: String, madvertisementLink: String) {

        Log.d("RRR", "setImageData: ${madvertisementUrl}  ${madvertisementLink}")
        Glide.with(binding.root).load(madvertisementUrl).into(binding.topImg
        )
        binding.topImg.setOnClickListener {
            val rateintent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(madvertisementLink)
            )
            startActivity(rateintent)
        }

    }


    private fun populateUnifiedNativeAdView(
            nativeAd: UnifiedNativeAd,
            adView: UnifiedNativeAdView
    ) {
        adView.mediaView = adView.findViewById<View>(R.id.ad_media) as MediaView
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }
        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).setText(nativeAd.callToAction)
        }
        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(nativeAd.icon.drawable)
            adView.iconView.setVisibility(View.VISIBLE)
        }
        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }
        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }
        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }
        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)
    }

    private fun refreshAd() {
        val builder: AdLoader.Builder =
                AdLoader.Builder(this@MainActivity, getString(R.string.ADMOB_ADS_UNIT_ID))
        builder.forUnifiedNativeAd { unifiedNativeAd ->
            nativeAd?.destroy()
            nativeAd = unifiedNativeAd
            val frameLayout: FrameLayout = binding.flAdplaceholder
            val adView = layoutInflater.inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayout.removeAllViews()
            frameLayout.addView(adView)
        }
        val adOptions = NativeAdOptions.Builder().build()
        builder.withNativeAdOptions(adOptions)
        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(i: Int) {
                Log.d("RRR", "onAdFailedToLoad: $i")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                Log.d("RRR", "onAdFailedToLoad: " + loadAdError.message)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }
        }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }


    fun more(view: View?) {
//        val moreintent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://play.google.com/store/apps/developer?id=Professional+Cipher&hl=en_US")
//        )
//        startActivity(moreintent)


        Intent(this@MainActivity, MoreAppsActivity::class.java).also {
            startActivity(it, transitionAnimationBundle())
        }


    }

    fun rate(view: View?) {
        val rateintent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
        )
        startActivity(rateintent)
    }


}