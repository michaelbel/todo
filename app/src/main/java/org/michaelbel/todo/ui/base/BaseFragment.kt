package org.michaelbel.todo.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {

    private val disposables = CompositeDisposable()

    fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}