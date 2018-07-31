package wilson.com.project_wake.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import wilson.com.project_wake.Activities.DataActivity;
import wilson.com.project_wake.R;
import wilson.com.project_wake.SQLiteOpenHelper.myDB;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

   private String TAG = "ListFragment";
   private View view;
   private Context context;
   private Cursor cursor;
   private SimpleCursorAdapter adapter;
   private SQLiteDatabase db;
   myDB helper;
   ListView sleep_list;

   public ListFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      Log.e(TAG, "ListFragment 初始化");
      view = inflater.inflate(R.layout.fragment_list, container, false);
      context = getActivity();

      sleep_list = view.findViewById(R.id.sleep_list);
      helper = new myDB(context, "DB.db", null, 1);
      db = helper.getReadableDatabase();

      cursor = helper.getReadableDatabase().query(
              "records",
              null,
              null,
              null,
              null,
              null,
              "_id");

      adapter = new SimpleCursorAdapter(
              context,
              android.R.layout.simple_list_item_1,
              cursor,
              new String[] {"start_time"},
              new int[] {android.R.id.text1},
              1);

      sleep_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
            //Toast.makeText(context, "position: " + position + " id: " + id, Toast.LENGTH_LONG).show();
            final long ids = id;
            Toast.makeText(context, "id: " + id, Toast.LENGTH_SHORT).show();

            new AlertDialog.Builder(context)
                    .setTitle("Warning!")
                    .setMessage("Delete this record?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          deleteData(ids);

                          cursor = helper.getReadableDatabase().query(
                                  "records",
                                  null,
                                  null,
                                  null,
                                  null,
                                  null,
                                  "_id");

                          SimpleCursorAdapter adapter1 = new android.widget.SimpleCursorAdapter(
                                  context,
                                  android.R.layout.simple_list_item_1,
                                  cursor,
                                  new String[] {"start_time"},
                                  new int[] {android.R.id.text1}
                          );

                          sleep_list.setAdapter(adapter1);
                       }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          //Do nothing!!!
                       }
                    }).show();

            return true;
         }
      });

      sleep_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor c = db.rawQuery("SELECT * FROM records Where _id=?", new String[]{String.valueOf(id)});
            String start_time = null, end_time = null, hsleep = null, tsleep = null, grade = null, suggestion = null;

            while(c.moveToNext()) {
               start_time = c.getString(1);
               end_time = c.getString(2);
               hsleep = c.getString(3);
               tsleep = c.getString(4);
               grade = c.getString(5);
               suggestion = c.getString(6);
               //Log.e(TAG, "Start: " + start_time);
               //Log.e(TAG, "Grad: " + grade);
            }

            Bundle bundle = new Bundle();
            bundle.putString("start_time", start_time);
            bundle.putString("end_time", end_time);
            bundle.putString("sleepHour", hsleep);
            bundle.putString("timeOfSleep", tsleep);
            bundle.putString("grade", grade);
            bundle.putString("suggestion", suggestion);

            Intent intent = new Intent(context, DataActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
         }
      });

      sleep_list.setAdapter(adapter);

      // Inflate the layout for this fragment
      return view;
   }

   //刪除資料，刪除id為id的資料
   private void deleteData(long id){
      myDB dbHelp = new myDB(getActivity());
      SQLiteDatabase db = dbHelp.getWritableDatabase();
      db.delete("records", "_id" + " = " + id, null);
   }

   @Override
   public void onResume() {
      super.onResume();
      //Toast.makeText(getActivity(), "ListFragment onResume", Toast.LENGTH_SHORT).show();
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      //Toast.makeText(getActivity(), "ListFragment onDestroy", Toast.LENGTH_SHORT).show();
   }
}
