package deco.designpatterns.chainofresponsibilities

abstract class BaseElement<T> {
    private var next: BaseElement<T>? = null

    fun setNext(next: BaseElement<T>) {
        this.next = next
    }

    fun onRequest(request: T) {
        if (canManage(request)) {
            manage(request)
        } else {
            next!!.onRequest(request)
        }
    }

    protected abstract fun manage(request: T)

    protected abstract fun canManage(request: T): Boolean
}