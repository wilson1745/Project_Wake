package wilson.com.project_wake.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

   private final String TAG = "AlarmReceiver";

   @Override
   public void onReceive(Context context, Intent intent) {
      Log.e(TAG, "進到AlarmReceiver");

      if(intent == null) {
         Log.e(TAG,"intent == null");
         String alarmState = intent.getExtras().getString("extra");
         Log.e(TAG,"alarmState: " + alarmState);
      }


      //Toast.makeText(arg0, "鬧鐘時間到了！", Toast.LENGTH_SHORT).show();
   }
}
