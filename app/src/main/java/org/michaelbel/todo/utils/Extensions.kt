@file:Suppress("nothing_to_inline")

package org.michaelbel.todo.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlin.reflect.KProperty

inline fun <F, reified T> argumentDelegate(crossinline provideArguments: (F) -> Bundle?): LazyProvider<F, T> = object: LazyProvider<F, T> {
    override fun provideDelegate(thisRef: F, prop: KProperty<*>): Lazy<T> = lazy {
        val bundle = provideArguments(thisRef)
        bundle?.get(prop.name) as T
    }
}

interface LazyProvider<A, T> {
    operator fun provideDelegate(thisRef: A, prop: KProperty<*>): Lazy<T>
}

inline fun <reified T> Fragment.argumentDelegate(): LazyProvider<Fragment, T> {
    return argumentDelegate { it.requireArguments() }
}

inline fun Float.toDp(context: Context): Float = if (this == 0F) 0F else
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

inline fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

inline fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocusedView = currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun View.shakeView(x: Float, num: Int) {
    val animatorSet = AnimatorSet()
    animatorSet.playTogether(ObjectAnimator.ofFloat(this, View.TRANSLATION_X, x.toDp(context)))
    animatorSet.duration = 50
    animatorSet.addListener(object: AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            this@shakeView.shakeView(if (num == 5) 0F else -x, num + 1)
        }
    })
    animatorSet.start()
}