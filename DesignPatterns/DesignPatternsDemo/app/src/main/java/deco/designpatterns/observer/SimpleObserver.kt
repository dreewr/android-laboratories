package deco.designpatterns.observer

class SimpleObserver : Observer<Int> {

    override fun onDataAvailable(data: Int?) {
        println("[SimpleObserver] onDataAvailable:$data")
    }
}