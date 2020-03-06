package com.mrapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GeolocationMonitorModule extends ReactContextBaseJavaModule implements  BackgroundLocationService.LocationMonitorListener {

    private static final int PERMISSION_REQUEST_CODE = 200 ;
    private final ReactApplicationContext reactContext;
    private BackgroundLocationService gpsService;
    private Callback mOnLocationChangedCallback;
    private Callback mOnReadyCallback;
    private Callback mOnErrorCallback;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("BackgroundLocationService")) {
                gpsService = ((BackgroundLocationService.LocationServiceBinder) service).getService();

                // TODO: Implement Emit JS event of onServiceConnected
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("BackgroundLocationService")) {
                gpsService = null;
                // TODO: Implement Emit JS event of onServiceDisconnected
            }
        }
    };

    public GeolocationMonitorModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "GeolocationMonitor";
    }

    @ReactMethod
    public void init() {

        final Intent intent = new Intent(reactContext, BackgroundLocationService.class);
        this.reactContext.startService(intent);
        this.reactContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @ReactMethod
    public void startTracking(Callback onReadyCallback, Callback onErrorCallback) {

        mOnReadyCallback = onReadyCallback;
        mOnErrorCallback = onErrorCallback;
s
        // Check for permission
        if (LocationUtils.hasLocationPermission(reactContext)) {
            gpsService.startTracking();
        } else {
            onErrorCallback.invoke("Location permissions denied");
        }
    }

    @ReactMethod
    public void stopTracking() {
        gpsService.stopTracking();
    }

    @ReactMethod
    public void registerForLocationChanges(Callback onLocationChangedCallback) {
        mOnLocationChangedCallback = onLocationChangedCallback;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(mOnLocationChangedCallback != null) {
            mOnLocationChangedCallback.invoke(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onReady() {
        mOnReadyCallback.invoke();
    }

    @Override
    public void onError(String error) {
        mOnErrorCallback.invoke(error);
    }
}
