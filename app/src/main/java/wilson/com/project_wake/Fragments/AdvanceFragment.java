package wilson.com.project_wake.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import wilson.com.project_wake.Activities.EmailActivity;
import wilson.com.project_wake.Activities.PersonalActivity;
import wilson.com.project_wake.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvanceFragment extends Fragment {

   private final String TAG = "AdvanceFragment";
   private View view;
   private ListView listView;
   private ArrayAdapter adapter;
   private Intent intent;
   private Context context;

   public AdvanceFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      Log.e(TAG, "AdvanceFragment 初始化");
      final String[] Item = getResources().getStringArray(R.array.advance);
      context = getActivity();
      view = inflater.inflate(R.layout.fragment_advance, container, false);
      listView = view.findViewById(R.id.ad_list);
      adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, Item);
      listView.setAdapter(adapter);

      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
               case 0:
                  Toast.makeText(getActivity(), "Memo", Toast.LENGTH_SHORT).show();
                  break;
               case 1:
                  intent = new Intent(getActivity(), PersonalActivity.class);
                  startActivity(intent);
                  Toast.makeText(getActivity(), "Personal Information", Toast.LENGTH_SHORT).show();
                  break;
               case 2:
                  Toast.makeText(getActivity(), "Output Result", Toast.LENGTH_SHORT).show();
                  break;
               case 3:
                  /*intent = new Intent(getActivity(), LanguageActivity.class);
                  startActivity(intent);*/
                  Toast.makeText(getActivity(), "Languages", Toast.LENGTH_SHORT).show();
                  break;
               case 4:
                  intent = new Intent(getActivity(), EmailActivity.class);
                  startActivity(intent);
                  Toast.makeText(getActivity(), "Feedback", Toast.LENGTH_SHORT).show();
                  break;
            }
         }
      });

      return view;
   }

   @Override
   public void onResume() {
      super.onResume();
      //Toast.makeText(getActivity(), "AdvanceFragment onResume", Toast.LENGTH_SHORT).show();
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      //Toast.makeText(getActivity(), "AdvanceFragment onDestroy", Toast.LENGTH_SHORT).show();
   }
}
