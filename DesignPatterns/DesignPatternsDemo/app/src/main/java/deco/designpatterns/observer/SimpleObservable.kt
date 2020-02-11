package deco.designpatterns.observer


class SimpleObservable : Observable<Int> {
    private val observers = arrayListOf<Observer<Int>>()

    override fun addObserver(observer: Observer<Int>) {
        observers.add(observer)
    }

    override fun notifyObservers() {
        for (i in 0..4) {
            for (observer in observers) {
                observer.onDataAvailable(i)
            }
        }
    }
}