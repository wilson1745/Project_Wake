package wilson.com.project_wake.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

   final String TAG = "AlarmService";

   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      final String state = intent.getExtras().getString("extra");

      if(state.equals("alarm on")) {
         Log.e(TAG, "State: " + state);
      }
      else if(state.equals("alarm off")) {
         Log.e(TAG, "State: " + state);

         // 關閉服務
         stopSelf();
      }

      return Service.START_REDELIVER_INTENT;
   }

   @Override
   public void onDestroy() {
      Log.e(TAG, "AlarmService onDestroy()");
      super.onDestroy();
   }
}
