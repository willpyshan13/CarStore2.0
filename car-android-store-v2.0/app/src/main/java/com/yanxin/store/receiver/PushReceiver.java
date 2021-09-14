package com.yanxin.store.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.yanxin.store.R;
import com.yanxin.store.event.GrabEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.e(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.e(TAG, "接受到推送下来的自定义消息");
            try {
                Bundle bundle = intent.getExtras();
                JSONObject extraJson = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                int type = extraJson.getInt("type");
                if (type == 3) {
                    EventBus.getDefault().post(new GrabEvent());
                }
//                createNotification(context, extraJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.e(TAG, "接受到推送下来的通知");
//            receivingNotification(context,bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.e(TAG, "用户点击打开了通知");
            Bundle bundle = intent.getExtras();
            JSONObject extraJson = null;
            try {
                extraJson = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                String type = extraJson.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //      openNotification(context,bundle);
//            openLaikan(context);
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected:" + connected);
        } else {
            LogUtils.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void createNotification(Context context, JSONObject extraJson) throws JSONException {
        String title = extraJson.getString("title");
        String msg = extraJson.getString("msg");
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, "car_notify");
            nm.createNotificationChannel(new NotificationChannel("car_notify", "新消息通知", NotificationManager.IMPORTANCE_HIGH));
        } else {
            builder = new NotificationCompat.Builder(context);
        }
//        Intent msgIntent = new Intent(context, MsgActivity.class);
//        JSONObject jsonObject = new JSONObject(extra);
//        int notify_msg_type = jsonObject.getInt("notify_msg_type");
//        msgIntent.putExtra("notify_msg_type", notify_msg_type).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 1, msgIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //设置通知栏标题
        builder.setContentTitle(title)
                //设置通知栏显示内容
                .setContentText(msg)
                ////设置通知栏点击意图
//                .setContentIntent(mPendingIntent)
                //通知首次出现在通知栏，带上升动画效果的
                .setTicker("您有新的消息")
                //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setWhen(System.currentTimeMillis())
                //设置该通知优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.icon_back)
//                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getApplication().getResources(), R.mipmap.icon_logo))
                //使用当前的用户默认设置
                .setDefaults(Notification.DEFAULT_VIBRATE);
        nm.notify(0, builder.build());
    }
}
