package com.example.totalhealth

import android.app.Application

class HealthApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}