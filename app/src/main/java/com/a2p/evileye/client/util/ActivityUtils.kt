package com.a2p.evileye.client.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
object ActivityUtils {

    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        fragmentManager.commit {
            add(frameId, fragment)
        }
    }
}