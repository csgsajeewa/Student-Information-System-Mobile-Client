package com.example.webclient;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.widget.Toast;

public class NewsReceiver extends BroadcastReceiver  {
    static int id=0;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String svcName = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager;
		notificationManager = (NotificationManager)arg0.getSystemService(svcName);
		id++;
		//add pending intent page 411 listing 10-36
		String title=arg1.getStringExtra("title");
		String text=arg1.getStringExtra("text");
		Intent startActivityIntent = new Intent(arg0, NewsWindow.class);
		PendingIntent launchIntent =PendingIntent.getActivity(arg0, 0, startActivityIntent, 0);
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
				//PendingIntent launchIntent=;
				//notification.setLatestEventInfo(MainActivity.this,"Breaking News!!!","You have to sign MAHAPOLA before 3rd October",launchIntent);
				
				//To fire a Notification, pass it in to the notify method of the NotificationManager along with an
				//integer reference ID
				notificationManager.notify(id, notification);
	}

}