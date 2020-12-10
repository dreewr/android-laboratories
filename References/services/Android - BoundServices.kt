Android - BoundServices

Services have their own lifecycler, independent of the activity or fragment that they were created in.
→ Unaffected by lifecycle events such as onDestroy, on Pause()...


! By default they do not run in background thread, this may cause some trouble. They operate in the same thread that are instantiated on.

Wich to use: bound services or started services?
A bound service can be a started service but a started service cannot be a bound service.

→ StartService is started by "startService" or "startForegroundService"

→ BoundService is started by:
○ When other component binds to it
○ Can be startedby by "startService" or "startForegroundService" as well THEN bind to it


WHAT IS A BOUND SERVICE AND WHY SHOULD I CARE

"You should compare it with a server"
You bind a SERVER with a CLIENT → CLIENT - SERVER interaction
CLIENT could be a fragment, activity or other application

WHEN TO USE: Consistent communication between some client and the service

EXAMPLE: Streaming Music
Playback progress needs to be sent consistently to activity or fragment to update the progress bar 

By binding to it, you can communicate very easily 



CodeExample: https://github.com/mitchtabian/Bound-Services-with-MVVM

Maninfest:

add
<service android:name=".MyService"/>

//PseudoCode
class MyService : Service{

	private IBinder mBinder = new MyBinder()
	private Handler mHandler
	private int progress, maxValue;
	private Boolean isPaused


	override fun onBind(intent:Intent): IBinder{
		return null	
	}

//Binder is an Acess point to retrieve a Service instance
//from whatever you want to bind it to, activity or fragment
înner class MyBinder : Binder {

		//SIngleton reference to this service class
		MyService getService(){
			return MyService.this
		}

	}

	public void startPretendLongRunningTask(){
		final Runnable runnaable = new Runnable(){

			@override public void run(){
				if(mProgress>= maxValue || isPaused){
					mHandler.removeCallbacks(this)
					pausePretendLongRunningTask();
				} else{

					mProgress +=100
					mHnalder.postDelayed(this, 100)
				}
			}
		}
		mHandler.postDelayed(runnable, 100)
	}

	private void pausePretendLongRunningTask(){
		isPaused = true
	}

}
}