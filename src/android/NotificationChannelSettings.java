package io.ammerlaan.plugins;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.os.Build;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

public class NotificationChannelSettings extends CordovaPlugin {

    private final int ERROR_CHANNEL_DOES_NOT_EXIST = 0;
    private final int ERROR_INVALID_SDK = 1;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Context context = this.cordova.getActivity().getApplicationContext();

        if (action.equals("getCanBypassDnd")) {
            String channelName = args.getString(0);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = notificationManager.getNotificationChannel(channelName);
            
            if (channel != null) {
                PluginResult result = new PluginResult(PluginResult.Status.OK, channel.canBypassDnd());
                callbackContext.sendPluginResult(result);
            } else {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, ERROR_CHANNEL_DOES_NOT_EXIST);
                callbackContext.sendPluginResult(result);
            }
            
            return true;

        } else if (action.equals("openSettings")) {
            String channelName = args.getString(0);

            Intent intent = new Intent();

			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
				intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
				intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                
                context.startActivity(intent);

                PluginResult result = new PluginResult(PluginResult.Status.OK);
                callbackContext.sendPluginResult(result);
			} else {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, ERROR_INVALID_SDK);
                callbackContext.sendPluginResult(result);
            }

            return true;
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
        return false;
    }

}

