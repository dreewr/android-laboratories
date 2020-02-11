package deco.designpatterns.observer

interface Observable<T> {

    fun addObserver(observer: Observer<T>)

    fun notifyObservers()
}