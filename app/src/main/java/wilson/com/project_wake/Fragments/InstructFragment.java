package wilson.com.project_wake.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

//這個會導致MainActivity的transaction.add無法compile
//import android.support.v4.app.Fragment;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wilson.com.project_wake.Expand_List.ParentLevelAdapter;
import wilson.com.project_wake.R;

public class InstructFragment extends Fragment {

   public static final String TAG = "InstructFragment";

   private View view;
   private Context context;
   private TextView textView;

   public InstructFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_instruct, container, false);
      context = getActivity();
      Log.e(TAG, "Come into InstructFragment");

      // Init top level data
      List<String> listDataHeader = new ArrayList<>();
      final String[] mItemHeaders = getResources().getStringArray(R.array.instructions_main);
      Collections.addAll(listDataHeader, mItemHeaders);
      final ExpandableListView mExpandableListView = view.findViewById(R.id.expandableListView_Parent); //expand_main.xml

      if(mExpandableListView != null) {
         ParentLevelAdapter parentLevelAdapter = new ParentLevelAdapter(context, listDataHeader);
         mExpandableListView.setAdapter(parentLevelAdapter);
      }

      mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
         @Override
         public void onGroupExpand(int groupPosition) {
            for (int i = 0; i < mItemHeaders.length; i++) {
               if (groupPosition != i) {
                  mExpandableListView.collapseGroup(i);
               }
            }
         }
      });

      // Inflate the layout for this fragment
      return view;
   }
}
