package com.example.network

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class RXSearchActivity {

    private val querySubject = BehaviorSubject.createDefault("")
    private val compositeDisposable = CompositeDisposable()

    val users: Observable<List<User>> = querySubject
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap { query -> ... }
        .subscribeOn(AndroidSchedulers.mainThread()) // потому что из UI
        .switchMap { query ->
            if (query.isBlank()) {
                repository.getAllUsers()
            } else {
                repository.searchUsers(query)
            }
        }
        .observeOn(AndroidSchedulers.mainThread())

    fun search(query: String) {
        querySubject.onNext(query)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}