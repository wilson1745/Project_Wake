package wilson.com.project_wake.Fragments;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import wilson.com.project_wake.R;
import wilson.com.project_wake.SQLiteOpenHelper.myDB2;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

   private View view;
   private Context context;
   private Cursor cursor;
   private MyContentObserver mObserver;
   private SimpleCursorAdapter adapter;

   public ListFragment() {
      // Required empty public constructor
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_list, container, false);
      context = getActivity();

      ListView sleep_list = view.findViewById(R.id.sleep_list);
      myDB2 helper = new myDB2(context, "DB2.db", null, 1);
      cursor = helper.getReadableDatabase().query(
              "records",
              null,
              null,
              null,
              null,
              null,
              "_id");

      Log.e("DLA", "onCreateView");

      mObserver = new MyContentObserver();
      //注册观察者
      cursor.registerContentObserver(mObserver);

      adapter = new SimpleCursorAdapter(
              context,
              android.R.layout.simple_list_item_1,
              cursor,
              new String[] {"start_time"},
              new int[] {android.R.id.text1},
              1);

      sleep_list.setAdapter(adapter);

      // Inflate the layout for this fragment
      return view;
   }

   //一个观察者
   private class MyContentObserver extends ContentObserver {

      public MyContentObserver() {
         super(new Handler());
      }

      @Override
      public void onChange(boolean selfChange) {
         super.onChange(selfChange);
         Log.e("DLA", "MyContentObserver onChange!");
//处理数据更新回调

         refreshData();
      }
   }

   private void refreshData() {
      if (cursor.isClosed()) {
         return;
      }

//更新
      cursor.requery();
   }

   @Override
   public void onDestroy() {
      super.onDestroy();

      if(cursor != null) {
         cursor.unregisterContentObserver(mObserver);
         cursor.close();
      }
   }
}
