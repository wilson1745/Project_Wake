package wilson.com.project_wake.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wilson.com.project_wake.R;
import wilson.com.project_wake.SQLiteOpenHelper.myDB;

public class CalculateAvtivity extends AppCompatActivity {

   EditText per_age, per_sex, per_height, per_weight;
   Button btn_cancel, btn_save;
   int age ;
   String sex, evaluation;
   double height, weight, bmi;
   String TAG = "CalculateAvtivity";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_calculate_avtivity);
      findView();

      btn_cancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(CalculateAvtivity.this, PersonalActivity.class);
            startActivity(intent);
            finish();
         }
      });

      btn_save.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(per_age.getText().toString().isEmpty() || per_sex.getText().toString().isEmpty() || per_weight.getText().toString().isEmpty() || per_height.getText().toString().isEmpty()) {
               Toast.makeText(CalculateAvtivity.this, "Toast：Something cannot be null", Toast.LENGTH_LONG).show();
            }
            else {
               BmiEvaluation();
               Log.e(TAG, "age: " + age + " sex: " + sex + " height: " + height + " weight: " + weight + " BMI: " + bmi + " Evaluation: " + evaluation);
               myDB dbHelp = new myDB(CalculateAvtivity.this);
               final SQLiteDatabase sqLiteDatabase = dbHelp.getWritableDatabase();
               Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM person Where _id=?", new String[]{String.valueOf(1)});

               ContentValues cv = new ContentValues();
               cv.put("age", age);
               cv.put("sex", sex);
               cv.put("height", height);
               cv.put("weight", weight);
               cv.put("bmi", bmi);
               cv.put("evaluation", evaluation);

               if (c.getCount() == 0) {
                  sqLiteDatabase.insert("person", null, cv);
               }
               else if (c.getCount() == 1) {
                  sqLiteDatabase.update("person", cv, "_id=" + String.valueOf(1), null);
               }

               Intent intent = new Intent(CalculateAvtivity.this, PersonalActivity.class);
               startActivity(intent);
               finish();
            }
         }
      });
   }

   private void findView() {
      per_age = findViewById(R.id.per_age);
      per_sex = findViewById(R.id.per_sex);
      per_height = findViewById(R.id.per_height);
      per_weight = findViewById(R.id.per_weight);
      btn_cancel = findViewById(R.id.btn_cancel);
      btn_save = findViewById(R.id.btn_save);
   }

   private void BmiEvaluation() {
      age = Integer.parseInt(per_age.getText().toString());
      sex = per_sex.getText().toString();
      height = Double.parseDouble(per_height.getText().toString());
      weight = Double.parseDouble(per_weight.getText().toString());

      double heights = height/100;
      bmi = weight/(heights*heights);

      //下面依照BMI給予Evaluation指示
      if(bmi < 18.5) evaluation = "Underweight";
      else if(bmi >= 18.5 && bmi <= 23.9) evaluation = "Normal weight";
      else if(bmi >= 24 && bmi <= 27.9) evaluation = "Overweight";
      else if(bmi >= 28) evaluation = "Obesity";
   }

   @Override
   public void onBackPressed() {
      Intent intent = new Intent(CalculateAvtivity.this, PersonalActivity.class);
      startActivity(intent);
      finish();
   }
}
