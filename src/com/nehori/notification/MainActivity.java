package com.nehori.notification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity {

	// PendingIntent用ID
	private static final int REQUEST_SAMPLE = 1;
	// 通知用ID
	private static final int NOTIFY_SAMPLE = 100;
	private static final String TAG = "Test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 標準的な通知の送信
	public void sendNotification(View v) {
		// 1. Notificationインスタンスの生成
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		// 2.(ア) ステータスバーに表示する設定を行う		
		builder.setSmallIcon(android.R.drawable.star_big_on);	// アイコンの設定
		builder.setTicker("WWWWWWWWWWWWWWWWWWWWWWWWW");		// ステータスバー上のテキストの設定
		
		// 2.(イ) 通知領域に表示する設定を行う
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentInfo("WWWWWWWWWWWWWWWWWWWWWWWWW");
		// Intentの生成(Googleのサイトを開くIntentの生成）
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.co.jp/"));
		PendingIntent contentIntent = PendingIntent.getActivity(
				this, REQUEST_SAMPLE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		//　3.(ウ) プロパティの設定を行う
		builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6"));
		builder.setLights(Color.BLUE, 1000, 400);
		
		Notification notification = builder.build();
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		
		// 4. 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, notification);
	}
	
	// 実行状態の通知の送信
	public void sendOnGoingNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(android.R.drawable.star_big_on);	// アイコンの設定
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setWhen(System.currentTimeMillis());
		
		// 実行中の状態フラグを付与する
		Notification notification = builder.build();
		notification.flags += Notification.FLAG_ONGOING_EVENT;

		// 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, notification);
	}

	// 大きい画像を表示するスタイルの通知を送信
	public void sendBigPictureStyleNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(android.R.drawable.star_big_on);	// アイコンの設定
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		
		// 大きい画像のスタイルを設定
		NotificationCompat.BigPictureStyle bigPictureNotification =
				new NotificationCompat.BigPictureStyle(builder);
		Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		bigPictureNotification.bigPicture(bigPicture);
		bigPictureNotification.setSummaryText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		
		// 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, bigPictureNotification.build());
	}
	
	// 大きいテキストを表示するスタイルの通知を送信
	public void sendBigTextStyleNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_launcher);	// アイコンの設定
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		
		// 大きいテキストのスタイルを設定
		NotificationCompat.BigTextStyle bigTextNotification =
				new NotificationCompat.BigTextStyle(builder);
		bigTextNotification.bigText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		bigTextNotification.setSummaryText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		
		// 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, bigTextNotification.build());
	}
		
	// 複数行のテキストを表示するスタイルの通知を送信
	public void sendInboxStyleNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_launcher);	// アイコンの設定
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		
		// 複数行のテキストのスタイルを設定
		NotificationCompat.InboxStyle inboxStyleNotification =
				new NotificationCompat.InboxStyle(builder);
		inboxStyleNotification.addLine("WWWWWWWWWWWWWWWWWWWWWWWWW");
		inboxStyleNotification.addLine("WWWWWWWWWWWWWWWWWWWWWWWWW");
		inboxStyleNotification.addLine("WWWWWWWWWWWWWWWWWWWWWWWWW");
		inboxStyleNotification.setSummaryText("WWWWWWWWWWWWWWWWWWWWWWWWW");

		// 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, inboxStyleNotification.build());
	}
		
	// 複数行のテキストを表示するスタイルの通知を送信
	public void sendInboxStyleNotification2(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_launcher);	// アイコンの設定
		builder.setContentTitle("Ratings updated");
		builder.setContentText("Select for details");
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(false);
		//　3.(ウ) プロパティの設定を行う
		builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6"));
		builder.setLights(Color.BLUE, 1000, 400);
			
		// 複数行のテキストのスタイルを設定
		NotificationCompat.InboxStyle inboxStyleNotification =
				new NotificationCompat.InboxStyle(builder);
		inboxStyleNotification.addLine("Downloadable ratings have been updated.");
		inboxStyleNotification.addLine("Select to view updated ratings in Parental");
		inboxStyleNotification.addLine("Lock settings");
		// Intentの生成(Googleのサイトを開くIntentの生成）
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setAction("android.intent.category.LAUNCHER"); // cat
		intent.setClassName("net.buildbox.pokeri.intent_settings", "net.buildbox.pokeri.intent_settings.MainActivity"); // cmp 省略せずに書く
		PendingIntent contentIntent = PendingIntent.getActivity(
				this, REQUEST_SAMPLE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		builder.setContentIntent(contentIntent);
		// 4. 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, inboxStyleNotification.build());
	}
	
	// カスタマイズした通知を送信
	public void sendCustomNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		// 通知のカスタマイズ
		RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_layout);
		remoteView.setImageViewResource(R.id.ivIcon, R.drawable.ic_launcher);
		remoteView.setTextViewText(R.id.tvTitle, "WWWWWWWWWWWWWWWWWWWWWWWWW");
		remoteView.setTextViewText(R.id.tvMessage, "WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContent(remoteView);
		
		builder.setSmallIcon(android.R.drawable.star_big_on);	// アイコンの設定
		builder.setAutoCancel(true);
		
		// 通知の実行
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, builder.build());
	}
	
	// ボタンを追加した通知を送信
	public void sendButtonNotification(View v) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

		builder.setSmallIcon(android.R.drawable.star_big_on);	// アイコンの設定
		builder.setTicker("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");	// ステータスバー上のテキストの設定
		builder.setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentText("WWWWWWWWWWWWWWWWWWWWWWWWWW");
		builder.setContentInfo("WWWWWWWWWWWWWWWWWWWWWWWWWW");
		// Intentの生成
		Intent intent1 = new Intent(Intent.ACTION_MAIN);
		intent1.setAction("android.intent.category.LAUNCHER");
		intent1.setClassName("net.buildbox.pokeri.intent_settings", "net.buildbox.pokeri.intent_settings.MainActivity"); // 省略せずに書く
//		intent1.setFlgs(0x10200000) //flgs ここはIntentの定数を使用するのがいい
		PendingIntent contentIntent1 = PendingIntent.getActivity(
				this, REQUEST_SAMPLE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.addAction(android.R.drawable.star_off, "SET", contentIntent1);
		Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yahoo.co.jp/"));
		PendingIntent contentIntent2 = PendingIntent.getActivity(
				this, REQUEST_SAMPLE, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.addAction(android.R.drawable.star_off, "Yahoo", contentIntent2);
		Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.msn.co.jp/"));
		PendingIntent contentIntent3 = PendingIntent.getActivity(
				this, REQUEST_SAMPLE, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.addAction(android.R.drawable.star_off, "MSN", contentIntent3);

		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFY_SAMPLE, builder.build());
	}

	// Toast 送信
	public void sendToast(View v) {
	        // トーストを表示する
	        Toast.makeText(this, "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW" , Toast.LENGTH_SHORT).show();
	}

	// Toast 送信
	public void sendToast2(View v) {
	        // インフレータ取得
	        LayoutInflater inflater = getLayoutInflater();
	        // xmlを指定
	        View layout = inflater.inflate(R.layout.toast_layout, null);
	        // 画像を設定
	        ImageView toastImage = (ImageView) layout.findViewById(R.id.toastImage);
	        toastImage.setImageResource(R.drawable.ic_launcher);
	        // テキストを設定
	        TextView toastText = (TextView) layout.findViewById(R.id.toastText);
	        toastText.setText("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
	        Toast toast = new Toast(this);
	        toast.setView(layout);
	        // 中央に表示
	        toast.setGravity( Gravity.CENTER, 0, 0 );
	        toast.show();
	}

	public void showHeadsupNotification(View v) {
            Log.i(TAG, Here.at() + "Heads-up Notification");
	    if (Build.VERSION.SDK_INT < 20) {
	        Log.i(TAG, "Heads-up Notification is not supported.");
	        return;
	    }

	    Bundle b = new Bundle();
//	    b.putInt(Notification.EXTRA_AS_HEADS_UP, HEADS_UP_REQUESTED);
	    b.putInt("headsup", 2);
	    Notification.Builder nb = new Notification.Builder(getApplicationContext());
	    try {
	        Method m = Notification.Builder.class.getMethod("setExtras", Bundle.class);
	        m.invoke(nb, b);
	    } catch (NoSuchMethodException e) {
	        Log.e(TAG, e.getMessage(), e);
	    } catch (IllegalArgumentException e) {
	        Log.e(TAG, e.getMessage(), e);
	    } catch (IllegalAccessException e) {
	        Log.e(TAG, e.getMessage(), e);
	    } catch (InvocationTargetException e) {
	        Log.e(TAG, e.getMessage(), e);
	    }

	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.co.jp/"));
	PendingIntent contentIntent = PendingIntent.getActivity(
			this, REQUEST_SAMPLE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

	    Notification n = nb
	            .setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setPriority(Notification.PRIORITY_HIGH)
	            .setCategory(Notification.CATEGORY_RECOMMENDATION)
	            .setAutoCancel(true)
//                    .setLargeIcon(R.drawable.ic_launcher)
//                    .setExtras(extras)
                    .setContentIntent(contentIntent)
	            .setFullScreenIntent(
	                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0),
	                    true)
	            .setOngoing(true)
	            .build();

	    NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(
				      Context.NOTIFICATION_SERVICE);
	    nm.notify(NOTIFY_SAMPLE, n);
	}

	public void showHeadsupNotification1(View v) {

	    Notification.Builder nb = new Notification.Builder(getApplicationContext());
	    Notification n = nb
	            .setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setPriority(Notification.PRIORITY_HIGH)
	            .setCategory(Notification.CATEGORY_RECOMMENDATION)
	            .setAutoCancel(true)
	            .setFullScreenIntent(
	                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0),
	                    true)
	            .setOngoing(true)
	            .build();

	    NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(
				      Context.NOTIFICATION_SERVICE);
	    nm.notify(NOTIFY_SAMPLE, n);
	}

	public void showHeadsupNotification2(View v) {

	    Notification.Builder nb = new Notification.Builder(getApplicationContext());
	    Notification n = nb
	            .setContentText("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setPriority(Notification.PRIORITY_HIGH)
	            .setCategory(Notification.CATEGORY_RECOMMENDATION)
	            .setAutoCancel(true)
	            .setFullScreenIntent(
	                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0),
	                    true)
	            .setOngoing(true)
	            .build();

	    NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(
				      Context.NOTIFICATION_SERVICE);
	    nm.notify(NOTIFY_SAMPLE, n);
	}

	public void showHeadsupNotification3(View v) {

	    Notification.Builder nb = new Notification.Builder(getApplicationContext());
	    Notification n = nb
	            .setContentTitle("WWWWWWWWWWWWWWWWWWWWWWWWW")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setPriority(Notification.PRIORITY_HIGH)
	            .setCategory(Notification.CATEGORY_RECOMMENDATION)
	            .setAutoCancel(true)
	            .setFullScreenIntent(
	                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0),
	                    true)
	            .setOngoing(true)
	            .build();

	    NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(
				      Context.NOTIFICATION_SERVICE);
	    nm.notify(NOTIFY_SAMPLE, n);
	}
	public void showHeadsupNotification4(View v) {

	    Notification.Builder nb = new Notification.Builder(getApplicationContext());
	    Notification n = nb
	            .setContentTitle("あいうえおかきくけこさしすせそたちつてとなにぬねの")
	            .setContentText("あいうえおかきくけこさしすせそたちつてとなにぬねの")
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setPriority(Notification.PRIORITY_HIGH)
	            .setCategory(Notification.CATEGORY_RECOMMENDATION)
	            .setAutoCancel(true)
	            .setFullScreenIntent(
	                    PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0),
	                    true)
	            .setOngoing(true)
	            .build();

	    NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(
				      Context.NOTIFICATION_SERVICE);
	    nm.notify(NOTIFY_SAMPLE, n);
	}
}

