package com.naturaz.bd.runner

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class NaturazTestRunner : AndroidJUnitRunner() {
	override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
		return super.newApplication(cl, className ?: Application::class.java.name, context)
	}
}
