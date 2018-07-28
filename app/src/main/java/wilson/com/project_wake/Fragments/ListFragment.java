package wilson.com.project_wake.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import wilson.com.project_wake.R;
import wilson.com.project_wake.SQLiteOpenHelper.myDB2;

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

   public ListFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_list, container, false);
      context = getActivity();

      final ListView sleep_list = view.findViewById(R.id.sleep_list);
      final myDB2 helper = new myDB2(context, "DB2.db", null, 1);
      db = helper.getReadableDatabase();

      cursor = helper.getReadableDatabase().query(
              "records",
              null,
              null,
              null,
              null,
              null,
              "_id");

      Log.e("DLA", "onCreateView");

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
            while(c.moveToNext()) {
               String start_time = c.getString(1);
               String end_time = c.getString(2);
               String grade = c.getString(5);
               Log.e(TAG, "Start: " + start_time);
               Log.e(TAG, "Grad: " + grade);
            }

         }
      });

      sleep_list.setAdapter(adapter);

      // Inflate the layout for this fragment
      return view;
   }

   //刪除資料，刪除id為id的資料
   private void deleteData(long id){
      myDB2 dbHelp = new myDB2(getActivity());
      SQLiteDatabase db = dbHelp.getWritableDatabase();
      db.delete("records", "_id" + " = " + id, null);
   }
}
