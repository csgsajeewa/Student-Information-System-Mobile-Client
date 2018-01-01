/*
 * generate new notification when a new message is received from the server
 */


package com.example.webclient;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;

develop change 1
feature changes

test 111222
public class NewsReceiver extends BroadcastReceiver  {
    static int id=0;// to generate multiple notifications
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String svcName = Context.NOTIFICATION_SERVICE;
		String APP_PREFS="user_details";
		NotificationManager notificationManager;
		notificationManager = (NotificationManager)arg0.getSystemService(svcName);
		id++;
		
		String title=arg1.getStringExtra("title");// get details from the message
		String text=arg1.getStringExtra("text");
		
		/////////////////shared prferences/////////////////////////////
		SharedPreferences settings = arg0.getSharedPreferences(APP_PREFS, 0);
		String index=settings.getString("index", "");
		
		//////////////////////////////////////////////////////////////////////
		
		Intent startActivityIntent;
		PendingIntent launchIntent;
		// if user has logout, display the login window
		if(index.equals("")){
			startActivityIntent = new Intent(arg0, SignInWindow.class);
		}
		// else diplay the news window
		else{
			
		 startActivityIntent = new Intent(arg0, NewsWindow.class);
		
		}
		 launchIntent =PendingIntent.getActivity(arg0, 0, startActivityIntent, 0);
		
		
		//build the notification
		
		Notification.Builder builder =new Notification.Builder(arg0);
				builder.setSmallIcon(R.drawable.logo)
				.setTicker("News From UOM Info System")
				.setWhen(System.currentTimeMillis())
				.setContentTitle(title)
                .setContentText(text)
                .setContentInfo("Info")
				.setDefaults(Notification.DEFAULT_SOUND |Notification.DEFAULT_VIBRATE)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
				.setAutoCancel(true)
				.setContentIntent(launchIntent)
				.setLights(Color.RED, 0, 1);
				Notification notification = builder.getNotification();
				
				
				//To fire a Notification, pass it in to the notify method of the NotificationManager along with an
				//integer reference ID
				notificationManager.notify(id, notification);
	}

}