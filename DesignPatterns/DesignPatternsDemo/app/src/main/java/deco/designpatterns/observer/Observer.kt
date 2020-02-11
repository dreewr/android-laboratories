package deco.designpatterns.observer

interface Observer <T> {

  fun onDataAvailable(data: T?)
}