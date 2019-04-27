package com.a2p.evileye.client.main.vote


import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.a2p.evileye.client.R
import kotlinx.android.synthetic.main.fragment_vote_dialog.view.*

class VoteDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity!!
        val view = activity.layoutInflater.inflate(R.layout.fragment_vote_dialog, null, false)
        val builder = AlertDialog.Builder(activity).apply {
            setTitle("Vote")
            setView(view)
            val listener = DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent().apply {
                    val desc = view.voteDescEditText.text.toString()
                    putExtra(VOTE_DESC, desc)
                }
                targetFragment?.onActivityResult(targetRequestCode, which, intent)
            }
            setPositiveButton("OK", listener)
            setNegativeButton("CANCEL", listener)
        }
        return builder.create()
    }

    companion object {
        const val REQUEST_CODE = 100
        const val VOTE_VIEW = "VOTE_VIEW"
        const val VOTE_DESC = "VOTE_DESC"
    }
}
