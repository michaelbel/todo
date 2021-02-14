package org.michaelbel.todo.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel: ViewModel() {

    val toastEvent: PublishSubject<Int> = PublishSubject.create()

    private val disposables = CompositeDisposable()

    fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.clear()
    }
}