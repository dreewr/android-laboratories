

Apps that use location services must request location permissions. Android offers the following location permissions:

<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

If you target Android 10 (API level 29) or higher and need to access the device location while your app is in the background, you must also declare the ACCESS_BACKGROUND_LOCATION permission.

<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>



private lateinit var fusedLocationClient: FusedLocationProviderClient

override fun onCreate(savedInstanceState: Bundle?) {
    // ...

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
}

////////////////

fusedLocationClient.lastLocation
        .addOnSuccessListener { location : Location? ->
            // Got last known location. In some rare situations this can be null.
        }




→ Location is turned off in the device settings. The result could be null even if the last location was previously retrieved because disabling location also clears the cache.
→ The device never recorded its location, which could be the case of a new device or a device that has been restored to factory settings.
→ Google Play services on the device has restarted, and there is no active Fused Location Provider client that has requested location after the services restarted. To avoid this situation you can create a new client and request location updates yourself. For more information, see Receiving Location Updates.





private lateinit var locationCallback: LocationCallback

// ...

override fun onCreate(savedInstanceState: Bundle?) {
    // ...

    locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                // Update UI with location data
                // ...
            }
        }
    }
}


