package wilson.com.project_wake;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import wilson.com.project_wake.Fragments.AdvanceFragment;
import wilson.com.project_wake.Fragments.AlarmFragment;
import wilson.com.project_wake.Fragments.InstructFragment;
import wilson.com.project_wake.Fragments.ListFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

   private final String TAG = "MainActivity";

   List<Fragment> list = new ArrayList<>();
   BottomNavigationBar bottomNavigationBar;
   FragmentManager fm;

   AlarmFragment alarmFragment;
   ListFragment listFragment;
   AdvanceFragment advanceFragment;
   InstructFragment instructFragment;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      fm = getFragmentManager();

      //添加標籤標籤頁
      bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom);
      bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
      bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
      bottomNavigationBar
              .addItem(new BottomNavigationItem(R.drawable.sleep_pressed, "Start Tracking")
                      .setActiveColorResource(R.color.bottombar).setInactiveIconResource(R.drawable.sleep_normal))
              .addItem(new BottomNavigationItem(R.drawable.analysis_pressed, "Sleep Records")
                      .setActiveColorResource(R.color.bottombar).setInactiveIconResource(R.drawable.analysis_normal))
              .addItem(new BottomNavigationItem(R.drawable.diary_pressed, "Advance Options")
                      .setActiveColorResource(R.color.bottombar).setInactiveIconResource(R.drawable.diary_normal))
              .addItem(new BottomNavigationItem(R.drawable.music_pressed, "Instructions")
                      .setActiveColorResource(R.color.bottombar).setInactiveIconResource(R.drawable.music_normal))
              .initialise();

      onTabSelected(0);
      bottomNavigationBar.setTabSelectedListener(this);
   }

   public void hideFragment(FragmentTransaction transaction) {
      for (Fragment fragment : list) {
         transaction.hide(fragment);
      }
   }

   @Override
   public void onTabSelected(int position) {
      FragmentTransaction transaction = fm.beginTransaction();
      //先把所有fragment隱藏
      hideFragment(transaction);

      switch(position) {
         case 0:
            if(alarmFragment == null) {
               alarmFragment = new AlarmFragment(); //初始化alarmFragment
               transaction.add(R.id.layFrame, alarmFragment);
               list.add(alarmFragment);
            }
            else {
               transaction.show(alarmFragment);
            }

            Log.e(TAG, "list size: " + String.valueOf(list.size()));
            break;
         case 1:
            if(listFragment == null) {
               Log.e(TAG, "listFragment == null");
               listFragment = new ListFragment();
               transaction.add(R.id.layFrame, listFragment);
               list.add(listFragment);
            }
            else {
               transaction.remove(listFragment);
               listFragment = null;
               listFragment = new ListFragment();
               transaction.add(R.id.layFrame, listFragment).addToBackStack(null);
               int i = checkList(listFragment);
               list.set(i, listFragment);
            }

            /*if(listFragment != null) {
               listFragment = null;
            }
            listFragment = new ListFragment();
            transaction.add(R.id.layFrame, listFragment);
            list.add(listFragment);*/

            Log.e(TAG, "list size1: " + String.valueOf(list.size()));
            break;
         case 2:
            if(advanceFragment == null) {
               Log.e(TAG, "advanceFragment == null");
               advanceFragment = new AdvanceFragment();
               transaction.add(R.id.layFrame, advanceFragment);
               list.add(advanceFragment);
            }
            else {
               transaction.remove(advanceFragment);
               advanceFragment = null;
               advanceFragment = new AdvanceFragment();
               transaction.add(R.id.layFrame, advanceFragment).addToBackStack(null);
               int i = checkList(advanceFragment);
               list.set(i, advanceFragment);
            }

            /*if(advanceFragment != null) {
               advanceFragment = null;
            }
            advanceFragment = new AdvanceFragment();
            transaction.add(R.id.layFrame, advanceFragment);
            list.add(advanceFragment);*/

            Log.e(TAG, "list size: " + String.valueOf(list.size()));
            break;
         case 3:
            if(instructFragment == null) {
               Log.e(TAG, "instructFragment == null");
               instructFragment = new InstructFragment();
               transaction.add(R.id.layFrame, instructFragment);
               list.add(instructFragment);
            }
            else {
               transaction.remove(instructFragment);
               instructFragment = null;
               instructFragment = new InstructFragment();
               transaction.add(R.id.layFrame, instructFragment).addToBackStack(null);
               int i = checkList(instructFragment);
               list.set(i, instructFragment);
            }

            /*if(instructFragment != null) {
               instructFragment = null;
            }
            instructFragment = new InstructFragment();
            transaction.add(R.id.layFrame, instructFragment);
            list.add(instructFragment);*/

            Log.e(TAG, "list size: " + String.valueOf(list.size()));
            break;
         default:
            break;
      }
      transaction.commit();
   }

   private int checkList(Fragment fragment) {
      int position = 0;
      for(int i = 0; i < list.size(); i++) {
         if(list.get(i) == fragment) {
            position = i;
         }
      }
      return position;
   }

   @Override
   public void onTabUnselected(int position) {

   }

   @Override
   public void onTabReselected(int position) {

   }

   @Override
   protected void onResume() {
      super.onResume();
      //Toast.makeText(this, "MainActivity onResume", Toast.LENGTH_SHORT).show();
      Log.e(TAG, "MainActivity onResume");
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      //Toast.makeText(this, "MainActivity onDestroy", Toast.LENGTH_SHORT).show();
      Log.e(TAG, "MainActivity onDestroy");
   }

   @Override
   protected void onPause() {
      super.onPause();
      //Toast.makeText(this, "MainActivity onPause", Toast.LENGTH_SHORT).show();
      Log.e(TAG, "MainActivity onPause");
   }

   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
      if(keyCode == KeyEvent.KEYCODE_BACK) {
         return true;
      }
      return super.onKeyDown(keyCode, event);
   }

}
