RxJava x Coroutines

Referencias: 
[1] https://blog.danlew.net/2021/01/28/rxjava-vs-coroutines/

[2]https://blog.mindorks.com/what-is-flow-in-kotlin-and-how-to-use-it-in-android-project

[3] https://blog.mindorks.com/exception-handling-in-kotlin-flow

-> StateFlow and SharedFlow -> Coroutines comp. to Rx

-> Easier debugging in corout. soon -> https://kotlinlang.org/docs/tutorials/coroutines/debug-coroutines-with-idea.html

-> Rx streaming types [Observable, Flowable...] are modeled to two types in coroutines: suspend fun and "Flow"-> That is represented by the return type of the `suspend fun`. If it's `T`, then it's a Single. If it's `T?`, it's a Maybe. If it's `Unit`, then it's a Completable
	-> just call susp. functions in operator lambdas instead of combining multiple RxJava streams

Structured Concurrency
	-> Easier to manage lifecycle of concurrent code
		-> To create a top-level coroutine, memory is consumed while it runs. Even if we forget to keep a reference to this coroutines, it will still run.
			-> What if this code inside this coroutine delays, we launch a lot of coroutines and run out of memory? -> this is error prone
			-> To solve this structured concurrency is used
			-> Instead of GlobalScope, use a scope specific to the operation 

Backpressure -> Producer/Consumer problem

Incremental transition to flow



-> What is Kotlin Flow?
	-> Rx: Observable + Reativo
	-> É um complemento do coroutines que deixa ele com uma funcionalidade parecida com a do RX no quesito observabilidade
	-> Flow in Kotlin now you can handle a stream of data that emits values sequentially

-> Do mesmo jeito que o código do observable não é executado até ser subscrito por um subscriber, um código dentro de um flow builder não é EXECUTADO até que o flow seja COLETADO

	-> exemplo:

		lateinit var flow: Flow<Int>

		fun setupFlow(){
			flow = flow{
			Log.d(TAG, "Start flow")
			        (0..10).forEach {
			            delay(500)
			            Log.d(TAG, "Emitting $it")
			        }
			    }.flowOn(Dispatchers.Default) //thread que executa
			}
		}
		-> flowOn -> muda o contexto da emissão do flow

private fun setupClick(){
		//thread que recebe
		CoroutineScope(Dispatchers.Main).launch{
			flow.collect{ ---> referencia a variavel
				...
			}

		}
}

!!! Flow starts only when the setupClick() is called (flow is collected) --> flows are cold


-> Builders no Flow
	-> flowOf, asFlow(), flow{}, channelFlow{}
	-> flowOf é tipo um "just" do Observable, valores fixos
	-> As flow é uma extension que converte um tipo em um Flow
	(1..5).asFlow().onEach{ ... }.flowOn(...)

	-> flow{} is a builder function for arbitrary flows
	-> channelFlow{} cold-flow with elements using "send"

-> Zip operator 

	lateinit var flowOne: Flow<String>
	lateinit var flowTwo: Flow<String>
... setup flows

private fun collectFlows(){
	CoroutineScope(Dispatchers.Main).launch{
		flowOne.zip(flowTwo){
			firstFlowString, secondFlowString ->
			...
		}.collect{
			Log.d(TAG, it)
		} 
	}
}

? Como fazer a gerência de exceções no Flow? [3]
	-> Catch exception using "catch"

? Posso fazer um zip de multiplos flows?

? Como substituir os liveData?

? Qual a diferença entre os Dispatchers? [IO, Default...]

? Flow + ViewModel

? Exemplo de caso de uso usando Coroutines dentro da Clean

? POC usando room e coroutines
