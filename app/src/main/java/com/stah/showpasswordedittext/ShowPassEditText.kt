package com.stah.showpasswordedittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat


class ShowPassEditText(context: Context, attributes: AttributeSet) :
    AppCompatEditText(context, attributes) {

    private var isShowingPassword = false
    private val visibilityPassword = R.drawable.ic_visibility
    private val invisibilityPassword = R.drawable.ic_invisible

    init {
        isShowingPassword = false
        isSaveEnabled = true

        showPasswordVisibilityIndicator(true)

        maxLines = 1
        isSingleLine = true
        maskPassword()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    showPasswordVisibilityIndicator(true)
                } else {
                    showPasswordVisibilityIndicator(false)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    private fun unmaskPassword() {
        transformationMethod = null
    }


    private fun maskPassword() {
        transformationMethod = PasswordTransformationMethod.getInstance()
    }

    private fun showPasswordVisibilityIndicator(show: Boolean) {
        val existingDrawables = compoundDrawables
        val left = existingDrawables[0]
        val top = existingDrawables[1]
        val right = existingDrawables[2]
        val bottom = existingDrawables[3]
        if (show) {
            val original =
                if (isShowingPassword) ContextCompat.getDrawable(context, visibilityPassword)
                else ContextCompat.getDrawable(context, invisibilityPassword)
            original?.mutate()
            setCompoundDrawablesWithIntrinsicBounds(left, top, original, bottom)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        }
    }


    val visilityIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_visibility)


    private fun togglePasswordVisibility() {
        val selectionStart = this.selectionStart
        val selectionEnd = this.selectionEnd

        if (isShowingPassword) maskPassword() else unmaskPassword()

        this.setSelection(selectionStart, selectionEnd)
        isShowingPassword = !isShowingPassword
        showPasswordVisibilityIndicator(true)
    }

    private val additionalTouchTargetSize: Int = 40
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {

            val bounds = visilityIconDrawable?.bounds
            val x = event.x.toInt()
            //領域判定 paddingがある場合を考慮
            val width = bounds?.width() ?: 0
            val drawableWidthWithPadding = width + paddingRight + additionalTouchTargetSize
            if (x >= this.right - drawableWidthWithPadding || x <= this.left + drawableWidthWithPadding
            ) {
                togglePasswordVisibility()
                // iconをタップしたときにキーボードを出さない
                event.action = MotionEvent.ACTION_CANCEL
            }
        }
        return super.onTouchEvent(event)
    }


}