package com.devgenius.exchanger.presentation.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.devgenius.exchanger.R

/**
 * Диалог фрагмент
 *
 * @author Evgeniia Grigorvich
 */
class RemoveDialogFragment : DialogFragment() {
    var onDeleteCurrencyClickListener: (() -> Unit)? = null
    var onCancelClickListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_action))
            .setPositiveButton(getString(R.string.delete)) { _, _ -> onDeleteCurrencyClickListener?.invoke() }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> onCancelClickListener?.invoke() }
            .create()

    companion object {
        const val TAG = "RemoveDialogFragment"
    }
}