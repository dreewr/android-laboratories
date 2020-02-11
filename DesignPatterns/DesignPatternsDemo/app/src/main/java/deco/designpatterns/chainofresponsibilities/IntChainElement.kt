package deco.designpatterns.chainofresponsibilities

import android.util.Log


class IntChainElement(private val managedRequest: Int) : BaseElement<Int>() {
    override fun manage(request: Int) {
        val message = "$memoryAddress managing request $request"
        Log.d(TAG, message)
    }

    override fun canManage(request: Int): Boolean {
        return request == managedRequest
    }

    private val memoryAddress: String
        get() {
            val fullName = this.toString()
            val atPosition = fullName.indexOf("@") + 1
            return fullName.substring(atPosition)
        }


    companion object {
        private val TAG = "[" + IntChainElement::class.java.simpleName + "]"
    }
}