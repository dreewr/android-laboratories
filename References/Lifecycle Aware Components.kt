Lifecycle Aware Components

Encapsulamento para objetos serem cientes do estado de vida
Exemplo: um objeto que precisa ser inicializado e destruido dentro da activity

Activity{

	objeto : MeuObjeto

	onCreate(){

		objeto.create
	}

	onResume(){
		objeto.resume
	}

	onPause(){
		objeto.
	}

	onDestroy(){

	}

Isso pode ficar trabalhoso conforme o numero de objetos aumenta, e levar à bugs


Lifecycle

Lifecycle is a class that holds the information about the lifecycle state of a component (like an activity or a fragment)

	→ Usa EVENT: eventos dispachados pela Activity ou fragment
	→ e STATE o estado atual 


Uma classse pode monitorar esses estados usando annotations
Implementa a interface LifecycleOwner
class MyObserver : LifecycleObserver {

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	fun connectListener() {
	...
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	fun disconnectListener() {
...
}
}

myLifecycleOwner.getLifecycle().addObserver(MyObserver())


LifecycleOwner 

É uma interface de um método que denota que a classe TEM UM LIFECYCLE E.G Activity.

A classe deve Implementar o método getLifecycle()


EXEMPLO DE OBJETO QUE É LIFECYCLE AWARE
internal class MyLocationListener(
        private val context: Context,
        private val lifecycle: Lifecycle,
        private val callback: (Location) -> Unit
) {

    private var enabled = false

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        if (enabled) {
            // connect
        }
    }

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            // connect if not connected
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        // disconnect if connected
    }
}