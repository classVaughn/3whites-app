package com.wifighters.threewhites

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.wifighters.threewhites.BuildConfig

class ThreeWhitesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize Firebase App Check
        if (BuildConfig.DEBUG) {
            // Use debug provider for development
            val debugToken = getString(R.string.app_check_debug_token)
            FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
            // Set the debug token through system property
            System.setProperty(
                "firebase.appcheck.debug.token",
                debugToken
            )
        } else {
            // Use Play Integrity provider for production
            FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
            )
        }
    }
} 