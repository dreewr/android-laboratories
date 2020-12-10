Entendendo Window no Android

https://proandroiddev.com/android-full-screen-ui-with-transparent-status-bar-ef52f3adde63

[statusBar] <- Window 1
[Activity ] <- Window 2
|		  |			
|		  |	        
|		  |	
|		  |	|
[navig Bar] <- Window 3

Elas são controlas pelo WindowManager

FLAG_TRANSLUCENT_STATUS → Status bar acinzentada, disabilitar para ficar transparente

FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS → a window em questão vai ser a responsável pelo desenho do system background

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

Deslocando o 