package com.a2p.evileye.client.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a2p.evileye.client.R
import com.a2p.evileye.client.util.ActivityUtils
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein

class LoginActivity : AppCompatActivity() {

    private val injector = KodeinInjector()
    private val presenter: LoginContract.Presenter by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = supportFragmentManager.findFragmentById(R.id.loginFragmentContainer) as? LoginFragment
            ?: LoginFragment.newInstance().apply {
                ActivityUtils.addFragmentToActivity(supportFragmentManager, this, R.id.loginFragmentContainer)
            }

        injector.inject(Kodein {
            extend(appKodein())
            import(loginPresenterModule(loginFragment))
            bind<LoginContract.Presenter>() with provider { LoginPresenter(instance(), instance()) }
        })
    }
}
