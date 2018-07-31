package wilson.com.project_wake.Receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

   public static int num2 =  0;
   private String TAG = "MyReceiver";

   @Override
   public void onReceive(Context context, Intent intent) {
      //解鎖
      if(intent != null && Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
         num2++;
         Toast.makeText(context, "螢幕已解鎖", Toast.LENGTH_SHORT).show();
         Log.e(TAG, "進到MyReceiver，螢幕已解鎖: " + num2 + " 次");
      }
   }

   public static int getNum2() {
        return num2;
   }
}

