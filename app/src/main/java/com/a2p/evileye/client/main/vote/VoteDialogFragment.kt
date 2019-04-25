package com.a2p.evileye.client.main.vote


import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class VoteDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context ?: error("context require not null")
        val builder = AlertDialog.Builder(context)
        return builder.create()
    }

    companion object {
        const val REQUEST_CODE = 100
        const val VOTE_VIEW = "VOTE_VIEW"
    }
}
