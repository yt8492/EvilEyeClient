package com.a2p.evileye.client.main.tarekomi


import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

import com.a2p.evileye.client.R
import kotlinx.android.synthetic.main.fragment_tarekomi_dialog.view.*

class TarekomiDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity!!
        val view = activity.layoutInflater.inflate(R.layout.fragment_tarekomi_dialog, null, false)
        arguments?.getString(TAREKOMI_URL)?.let {
            view.tarekomiUrlEditText.setText(it)
        }
        val builder = AlertDialog.Builder(activity).apply {
            setTitle("投稿")
            setView(view)
            val listener = DialogInterface.OnClickListener { _, which ->
                val intent = Intent().apply {
                    val url = view.tarekomiUrlEditText.text.toString()
                    val user = view.tarekomiUserNameEditText.text.toString()
                    val desc = view.tarekomiDescEditText.text.toString()
                    putExtra(TAREKOMI_URL, url)
                    putExtra(TAREKOMI_USER, user)
                    putExtra(TAREKOMI_DESC, desc)
                }
                targetFragment?.onActivityResult(targetRequestCode, which, intent)
            }
            setPositiveButton("タレコミ", listener)
            setNegativeButton("キャンセル", null)
        }
        return builder.create()
    }

    companion object {
        const val REQUEST_CODE = 101
        const val TAREKOMI_VIEW = "TAREKOMI_VIEW"
        const val TAREKOMI_URL = "TAREKOMI_URL"
        const val TAREKOMI_USER = "TAREKOMI_USER"
        const val TAREKOMI_DESC = "TAREKOMI_DESC"

        @JvmStatic
        fun newInstance() = TarekomiDialogFragment()

        @JvmStatic
        fun newInstance(url: String) =
            TarekomiDialogFragment().apply {
                val bundle = Bundle()
                bundle.putString(TAREKOMI_URL, url)
                arguments = bundle
            }
    }
}
