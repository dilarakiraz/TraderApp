package com.dilarakiraz.traderapp.common

import android.content.Context
import android.widget.Toast

/**
 * Created on 6.01.2024
 * @author Dilara Kiraz
 */

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}