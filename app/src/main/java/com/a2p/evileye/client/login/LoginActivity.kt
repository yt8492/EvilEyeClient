package com.a2p.evileye.client.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a2p.evileye.client.R
import com.a2p.evileye.client.data.user.UserRepository
import com.a2p.evileye.client.util.ActivityUtils
import io.grpc.ManagedChannelBuilder

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = supportFragmentManager.findFragmentById(R.id.loginFragmentContainer) as? LoginFragment
            ?: LoginFragment.newInstance().apply {
                ActivityUtils.addFragmentToActivity(supportFragmentManager, this, R.id.loginFragmentContainer)
            }
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build()
        val presenter = LoginPresenter(UserRepository(channel), loginFragment)
    }
}
