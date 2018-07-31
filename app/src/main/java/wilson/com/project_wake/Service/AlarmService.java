package wilson.com.project_wake.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import wilson.com.project_wake.MainActivity;
import wilson.com.project_wake.R;

public class AlarmService extends Service {

   private final String TAG = "AlarmService";
   private MediaPlayer media_song;
   int startId;
   boolean isRunning;

   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      final String alarmState = intent.getExtras().getString("extra");

      Log.e(TAG, "Alarm State: " + alarmState);
      // this converts the extra strings from the intent to start IDs, values 0 or 1
      assert alarmState != null;
      switch (alarmState) {
         case "alarm on":
            startId = 1;
            break;
         case "alarm off":
            startId = 0;
            break;
         default:
            startId = 0;
            break;
      }

      // if there is no music playing, and the user pressed "alarm on" music should start playing
      if (!this.isRunning && startId == 1) {
         Log.e(TAG, "There is no music, and you want start");
         this.isRunning = true;
         this.startId = 0;
         // set up the start command for the notification
         //notify_manager.notify(0, notification_popup);
         media_song = MediaPlayer.create(this, R.raw.machiko);
         media_song.setLooping(true);
         media_song.start();
      }

      // if there is music playing, and the user pressed "alarm off" music should stop playing
      else if (this.isRunning && startId == 0) {
         Log.e(TAG, "There is music, and you want end");
         // stop the ringtone
         media_song.stop();
         media_song.reset();
         this.isRunning = false;
         this.startId = 0;
         // 關閉服務
         stopSelf();
      }

      // these are if the user presses random buttons just to bug-proof the app
      // if there is no music playing, and the user pressed "alarm off" do nothing
      else if (!this.isRunning && startId == 0) {
         Log.e(TAG, "There is no music, and you want end");
         this.isRunning = false;
         this.startId = 0;
         // 關閉服務
         stopSelf();
      }

      // if there is music playing and the user pressed "alarm on" do nothing
      else if (this.isRunning && startId == 1) {
         Log.e(TAG, "There is music, and you want start");
         this.isRunning = true;
         this.startId = 1;
      }

      // Can't think of anything else, just to catch the odd event
      else {
         Log.e(TAG, "Else somehow you reached this");
      }

      return Service.START_REDELIVER_INTENT;
   }

   @Override
   public void onDestroy() {
      Log.e(TAG, "AlarmService onDestroy()");
      super.onDestroy();
      this.isRunning = false;
   }
}
