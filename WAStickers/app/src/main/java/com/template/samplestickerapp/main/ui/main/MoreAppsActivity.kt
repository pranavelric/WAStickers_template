package com.template.samplestickerapp.main.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.template.samplestickerapp.R
import com.template.samplestickerapp.databinding.ActivityMainBinding
import com.template.samplestickerapp.databinding.ActivityMoreAppsBinding
import com.template.samplestickerapp.main.Adapter.MyDataAdapter
import com.template.samplestickerapp.main.data.model.App
import com.template.samplestickerapp.main.utils.ResponseState
import com.template.samplestickerapp.main.utils.gone
import com.template.samplestickerapp.main.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreAppsActivity : AppCompatActivity() {


    private val moreAppsViewModel: MoreAppsViewModel by lazy {
        ViewModelProvider(this)[MoreAppsViewModel::class.java]
    }

    @Inject
    lateinit var myDataAdapter: MyDataAdapter


    //  lateinit var myDataAdapterList: ArrayList<MyDataAdapter>


    lateinit var binding: ActivityMoreAppsBinding
    var appList = ArrayList<App>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_more_apps)
        binding.moreAppAct = this
        setContentView(binding.root)
        supportActionBar?.title = "More Apps"

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);


        moreAppsViewModel.getMyData()
        moreAppsViewModel.myData.observe(this, {
            when (it) {
                is ResponseState.Success -> {
                    binding.progressCircular.gone()

                    it.data?.let { moreApp ->
                        moreApp.apps?.let { app ->

                            appList.clear()
                            //  myDataAdapter.submitList(app)
                            for (i in app) {

                                if (i.name == resources.getString(R.string.more_app_name)) {
                                    appList.add(i)
                                }
                            }
                        }

                    }

                    myDataAdapter.submitList(appList.distinct())


                }

                is ResponseState.Error -> {
                    binding.progressCircular.gone()

                }
                is ResponseState.Loading -> {
                    binding.progressCircular.visible()
                }
            }


        })

        binding.rvMydata.apply {
            adapter = myDataAdapter
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }

}