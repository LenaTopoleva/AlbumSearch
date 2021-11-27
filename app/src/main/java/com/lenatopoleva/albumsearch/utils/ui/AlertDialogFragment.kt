package com.lenatopoleva.albumsearch.utils.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lenatopoleva.albumsearch.R
import com.lenatopoleva.albumsearch.viewmodel.activity.MainActivityViewModel
import org.koin.android.ext.android.getKoin

class AlertDialogFragment : AppCompatDialogFragment() {

    private val model by lazy {
        ViewModelProvider(requireActivity(),  getKoin().get())[MainActivityViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var alertDialog = getStubAlertDialog(requireActivity())
        val args = arguments
        if (args != null) {
            val title = args.getString(TITLE_EXTRA)
            val message = args.getString(MESSAGE_EXTRA)
            alertDialog = getAlertDialog(requireActivity(), title, message)
        }
        return alertDialog
    }

    companion object {
        private const val TITLE_EXTRA = "89cbce59-e28f-418f-b470-ff67125c2e2f"
        private const val MESSAGE_EXTRA = "0dd00b66-91c2-447d-b627-530065040905"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()
            val args = Bundle()
            args.putString(TITLE_EXTRA, title)
            args.putString(MESSAGE_EXTRA, message)
            dialogFragment.arguments = args
            return dialogFragment
        }
    }

    private fun getStubAlertDialog(context: Context): AlertDialog {
        return getAlertDialog(context, null, null)
    }

    private fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog {
        val builder = AlertDialog.Builder(context)
        var finalTitle: String? = context.getString(R.string.dialog_title_stub)
        if (!title.isNullOrBlank()) {
            finalTitle = title
        }
        builder.setTitle(finalTitle)
        if (!message.isNullOrBlank()) {
            builder.setMessage(message)
        }
        builder.setCancelable(true)
        builder.setPositiveButton("OK") { dialog, _ ->
            run {
                model.alertDialogBtnOkClicked()
                dialog.dismiss()
            }
        }
        return builder.create()
    }
}

