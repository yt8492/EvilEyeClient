package com.a2p.evileye.client.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.my_page.MyPageFragment
import com.a2p.evileye.client.main.search.SearchFragment
import com.a2p.evileye.client.main.tarekomi.TarekomiDialogFragment
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
        initPresenter()
        initListener()
        when (intent.action) {
            Intent.ACTION_SEND -> {
                val url = intent.getStringExtra(Intent.EXTRA_TEXT)
                presenter.tarekomiFromOutside(url)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            TarekomiDialogFragment.REQUEST_CODE -> {
                if (resultCode == DialogInterface.BUTTON_POSITIVE && data != null) {
                    val url = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_URL) ?: return
                    val userName = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_USER) ?: return
                    val desc = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_DESC)
                    presenter.tarekomi(url, userName, desc)
                    presenter.listTarekomiSummaries()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initPresenter() {
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
                        presenter.listTarekomiSummaries()
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
    }

    private fun initListener() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tarekomi_board ->{
                    presenter.showTarekomiBoardView()
                    tarekomiFab.visibility = View.VISIBLE
                    true
                }
                R.id.search -> {
                    presenter.showSearchView()
                    tarekomiFab.visibility = View.GONE
                    true
                }
                R.id.myPage -> {
                    presenter.showMyPageView()
                    tarekomiFab.visibility = View.GONE
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
