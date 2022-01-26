package com.aisier.architecture.util

/**
 * <pre>
 *     @author : wutao
 *     time   : 2020/10/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
/*
inline fun <reified T : Activity> Activity.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun EditText.getNotNullText(): String = text?.toString()?.trim() ?: ""

fun EditText.getNotNullUpperCaseText(): String = getNotNullText().toUpperCase(Locale.ENGLISH)


//may only available on real device
fun EditText.openKeyBoard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.RESULT_SHOWN)
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

//may only available on real device
fun EditText.hideKeyBoard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.clickWithLimit(intervalMill: Int = 500, block: ((v: View?) -> Unit)) {
    setOnClickListener(object : View.OnClickListener {
        var last = 0L
        override fun onClick(v: View?) {
            if (System.currentTimeMillis() - last > intervalMill) {
                block(v)
                last = System.currentTimeMillis()
            }
        }
    })
}


/**
 * 自定义圆角矩形
 */
fun View.setRoundRectBg(color: Int, cornerRadius: Int) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius.toFloat())
    }
}
*/