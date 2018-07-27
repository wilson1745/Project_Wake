package wilson.com.project_wake;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import wilson.com.project_wake.Fragments.AlarmFragment;
import wilson.com.project_wake.Fragments.InstructFragment;
import wilson.com.project_wake.Fragments.ListFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

   private final String TAG = "MainActivity";

   List<Fragment> list = new ArrayList<>();
   BottomNavigationBar bottomNavigationBar;

   AlarmFragment alarmFragment;
   ListFragment listFragment;
   //DiaryFragment diaryFragment;
   InstructFragment instructFragment;
   FragmentManager fm;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      fm = getFragmentManager();

      bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom);
      bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
      bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

      //添加標籤標籤頁
      bottomNavigationBar
              .addItem(new BottomNavigationItem(R.drawable.sleep_pressed, "Home")
                      .setActiveColorResource(R.color.bottombar).setInactiveIconResource(R.drawable.sleep_normal))
              .addItem(new BottomNavigationItem(R.drawable.analysis_pressed, "Sleep Trend")
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
               //初始化alarmFragment
               alarmFragment = new AlarmFragment();
               transaction.add(R.id.layFrame, alarmFragment);
               list.add(alarmFragment);
            }
            else {
               transaction.show(alarmFragment);
            }
            //DiaryFragment.tag = true;
            break;
         case 1:
            if(listFragment != null) {
               listFragment = null;
            }
            listFragment = new ListFragment();
            transaction.add(R.id.layFrame, listFragment).addToBackStack(null);
            list.add(listFragment);
            break;
         case 2:
            Toast.makeText(this, "case 2", Toast.LENGTH_LONG).show();
            break;
         case 3:
            if(instructFragment != null) {
               instructFragment = null;
            }
            instructFragment = new InstructFragment();
            transaction.add(R.id.layFrame, instructFragment).addToBackStack(null);
            list.add(instructFragment);
            //DiaryFragment.tag = true;
            break;

         default:
            break;
      }
      transaction.commit();
   }

   @Override
   public void onTabUnselected(int position) {

   }

   @Override
   public void onTabReselected(int position) {

   }


}
