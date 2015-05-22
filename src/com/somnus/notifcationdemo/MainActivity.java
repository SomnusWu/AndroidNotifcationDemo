package com.somnus.notifcationdemo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity {

	private static final int requestCode = 1;
	private int notification_id = 0;
	NotificationManager manager;
	Notification notification;

	NotificationCompat.Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initNotification();
		initNotification1();
	}

	@SuppressWarnings("deprecation")
	private void initNotification() {
		// 获得通知管理器
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// 构建一个通知对象 （需要传递的参数有3个 ， 分别是图标， 标题 和时间）
		notification = new Notification(R.drawable.ic_launcher, "这是我的通知",
				System.currentTimeMillis());
		Intent intent = new Intent(MainActivity.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendinIntent = PendingIntent.getActivity(
				MainActivity.this, requestCode, intent, 0);
		notification.setLatestEventInfo(getApplicationContext(), "通知标题",
				"通知显示的内容", pendinIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击之后自动消失
		notification.defaults |= Notification.DEFAULT_SOUND;// 默认声音
		notification.defaults |= Notification.DEFAULT_LIGHTS;// 默认闪烁灯
		notification.defaults |= Notification.DEFAULT_VIBRATE;
	}

	@SuppressLint({ "NewApi", "ServiceCast" })
	private void initNotification1() {
		mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("My notification")
				.setContentText("Hello World!");
		mBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);
		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_CANCEL_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		// mNotificationManager.notify(notification_id, mBuilder.build());
	}

	@SuppressLint("InlinedApi")
	private void initNotification3() {
		// 先设定RemoteViews
		RemoteViews view_custom = new RemoteViews(getPackageName(),
				R.layout.custem_notification_layout);
		view_custom.setImageViewResource(R.id.custom_icon,
				R.drawable.ic_launcher);
		view_custom.setTextViewText(R.id.tv_custom_title, "我的APP Title");
		view_custom.setTextViewText(R.id.tv_custom_content, "这是我的自定义的内容。这是内容！");
		mBuilder = new Builder(this);
		Intent intent = new Intent(MainActivity.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendinIntent = PendingIntent.getActivity(
				MainActivity.this, requestCode, intent, 0);
		mBuilder.setContent(view_custom).setContentIntent(pendinIntent)
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setTicker("新消息").setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setOngoing(false)// 不是正在进行的 true为正在进行 效果和.flag一样
				.setSmallIcon(R.drawable.ic_launcher);
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(notification_id, notify);
	}

	public void onAction(View v) {
		switch (v.getId()) {
		case R.id.btn_send_notifacation:
			manager.notify(notification_id, notification);//
			break;
		case R.id.btn_send_notifacation1:
			// //发动通知,id由自己指定，每一个Notification对应的唯一标志
			manager.notify(notification_id, mBuilder.build());
			break;
		case R.id.btn_send_notifacation2:
			initNotification3();
			break;

		default:
			break;
		}

	}
}
