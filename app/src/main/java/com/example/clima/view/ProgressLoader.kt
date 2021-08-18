package com.example.clima.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.example.clima.R

class ProgressLoader(
    private var context: Context
) {
    private var dialog: Dialog = Dialog(context, R.style.ProgressLoader)

    fun setVisibility(isVisible: Boolean?) {
        when (isVisible) {
            true -> show()
            else -> dismiss()
        }
    }

    private fun show() {
        if (dialog.isShowing) return

        val view =
            View.inflate(context, R.layout.dialog_progress, null)

        dialog.setContentView(view)

        dialog.setCancelable(false)
        val window = dialog.window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)

        try {
            dialog.show()
        } catch (ex: RuntimeException) {
            ex.printStackTrace()
        }
    }

    private fun dismiss() {
        try {
            if (dialog.isShowing)
                dialog.dismiss()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}