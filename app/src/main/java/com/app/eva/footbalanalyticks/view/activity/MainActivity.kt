package com.app.eva.footbalanalyticks.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.eva.footbalanalyticks.databinding.ActivityMainBinding
import com.app.eva.footbalanalyticks.view.fragment.LeaguesFragment

 lateinit var bindingMain: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(bindingMain.container.id, LeaguesFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            val currentFragment = supportFragmentManager.findFragmentById(bindingMain.container.id)
            if (currentFragment !is LeaguesFragment) {
                super.onBackPressed()
            }
        }
    }
}

