package com.a2p.evileye.client.login

import android.os.Bundle
import com.a2p.evileye.client.R
import dagger.android.support.DaggerAppCompatActivity

class LoginActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
