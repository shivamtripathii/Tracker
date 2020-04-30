package com.noprofit.tracker19.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import de.mateware.snacky.Snacky
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://corona.lmao.ninja/v2/"
const val BASE_URL_INDIA = "https://api.rootnet.in/covid19-in/"

var c = -1
var s = -1
class Check {
    companion object {
        fun getInfo():Int { return c }
        fun setInfo(value:Int) { c=value }
        fun getSearchInfo():Int { return s }
        fun setSearchInfo(value:Int) { s=value }
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun snackbar(activity: Activity, text:String, type:Int){
    if(type == 0)
        Snacky.builder().setActivity(activity).setDuration(3000).setText(text).info().show()
    if(type == 1)
        Snacky.builder().setActivity(activity).setDuration(3000).setText(text).success().show()
    if(type == -1)
        Snacky.builder().setActivity(activity).setDuration(3000).setText(text).error().show()
}

fun guide(context: Context, view: View, title:String, desc:String) : GuideView.Builder
{
    return GuideView.Builder(context)
        .setTitle(title)
        .setContentText(desc)
        .setTargetView(view)
        .setContentTextSize(16)
        .setTitleTextSize(20)
        .setDismissType(DismissType.anywhere)
}

@SuppressLint("SimpleDateFormat")
fun timestampIntoDate(data:Long) : String
{
    try {
        val longDate = data.toLong()
        val date = Date(longDate)
        val format: DateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
        format.timeZone = TimeZone.getDefault()
        val formatted: String
        format.timeZone = TimeZone.getDefault() //your zone
        formatted = format.format(date)
        return formatted
    }catch (e: Exception)
    {
        return "Today"
    }
}

