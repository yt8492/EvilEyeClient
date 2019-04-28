package com.a2p.evileye.client.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.my_page.MyPageFragment
import com.a2p.evileye.client.main.search.SearchFragment
import com.a2p.evileye.client.main.tarekomi_board.TarekomiBoardFragment
import com.a2p.evileye.client.util.ActivityUtils
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.appKodein
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val injector = KodeinInjector()
    private val presenter: MainContract.MainPresenter by injector.instance()
    private var active: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFragment = supportFragmentManager.findFragmentById(R.id.mainVewContainer) as? SearchFragment
            ?: SearchFragment.newInstance().apply {
                ActivityUtils.addFragmentToActivity(supportFragmentManager, this, R.id.mainVewContainer)
            }.also {
                supportFragmentManager.commit {
                    hide(it)
                }
            }
        val myPageFragment = supportFragmentManager.findFragmentById(R.id.mainVewContainer) as? MyPageFragment
            ?: MyPageFragment.newInstance().apply {
                ActivityUtils.addFragmentToActivity(supportFragmentManager, this, R.id.mainVewContainer)
            }.also {
                supportFragmentManager.commit {
                    hide(it)
                }
            }
        val tarekomiBoardFragment = supportFragmentManager.findFragmentById(R.id.mainVewContainer) as? TarekomiBoardFragment
            ?: TarekomiBoardFragment.newInstance().apply {
                ActivityUtils.addFragmentToActivity(supportFragmentManager, this, R.id.mainVewContainer)
                active = this
            }

        injector.inject(Kodein {
            extend(appKodein())
            import(mainPresenerModule(tarekomiBoardFragment, searchFragment, myPageFragment))
            bind<MainContract.MainPresenter>() with provider {
                MainPresenter(instance(), instance(), instance(), instance())
            }
        })

        presenter.setOnLogoutListener {
            finish()
        }
        presenter.addViewSwitchListener { item ->
            when (item) {
                MainNavigationViewItem.TAREKOMI_BOARD ->
                    supportFragmentManager.commit {
                        active?.let {
                            if (it is TarekomiBoardFragment) {
                                return@commit
                            }
                            hide(it)
                        }
                        show(tarekomiBoardFragment)
                        active = tarekomiBoardFragment
                    }
                MainNavigationViewItem.SEARCH ->
                    supportFragmentManager.commit {
                        active?.let {
                            if (it is SearchFragment) {
                                return@commit
                            }
                            hide(it)
                        }
                        show(searchFragment)
                        active = searchFragment
                    }
                MainNavigationViewItem.MY_PAGE ->
                    supportFragmentManager.commit {
                        active?.let {
                            if (it is MyPageFragment) {
                                return@commit
                            }
                            hide(it)
                        }
                        show(myPageFragment)
                        active = myPageFragment
                    }
            }
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tarekomi_board ->{
                    presenter.showTarekomiBoardView()
                    true
                }
                R.id.search -> {
                    presenter.showSearchView()
                    true
                }
                R.id.myPage -> {
                    presenter.showMyPageView()
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context)
                = Intent(context, MainActivity::class.java)
    }
}
