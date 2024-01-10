package edu.ewubd.cse4892020160139;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
public class ClassLecturesActivity extends AppCompatActivity {


  Button btnBack, btnNew;

  private ArrayList<ClassSummary> classes;
  private ClassSummaryAdapter adapter;
  private ListView lvClasses;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_class_lectures);
    lvClasses = findViewById(R.id.lectureListView);
    classes = new ArrayList<>();


    btnNew = findViewById(R.id.btnNew);
    btnBack = findViewById(R.id.btnBack);

    btnNew.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent classLectureIntent = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
        startActivity(classLectureIntent);

      }
    });
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("MyLocalPrefs", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", false);
        editor.apply();
        Intent classLectureIntent = new Intent(ClassLecturesActivity.this, LoginActivity.class);
        startActivity(classLectureIntent);
        finish();

      }
    });


  }

  @Override
  protected void onStart() {
    super.onStart();
    loadData();
  }

  private void loadData() {
    classes.clear();
    LectureSummaryDB db = new LectureSummaryDB(this);
    Cursor rows = db.selectLectureSummaryDB("SELECT * FROM LectureTable");


    if (rows.getCount() > 0) {
      while (rows.moveToNext()) {

        String UniqueID = rows.getString(0);
        String name = rows.getString(1);
        String id = rows.getString(2);
        String course = rows.getString(3);
        String type = rows.getString(4);
        String date = rows.getString(5);
        String lecture = rows.getString(6);
        String topic = rows.getString(7);
        String summary = rows.getString(8);
        ClassSummary cs = new ClassSummary(UniqueID, name, id, course, type, date, lecture, topic, summary);
        classes.add(cs);
      }
    }
    db.close();
    adapter = new ClassSummaryAdapter(this, classes);
    lvClasses.setAdapter(adapter);


    // handle the click on an class-summary-list item
    lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        // String item = (String) parent.getItemAtPosition(position);
        System.out.println(position);
        Intent i = new Intent(ClassLecturesActivity.this, ClassSummaryActivity.class);
        i.putExtra("UniqueID", classes.get(position).UniqueID);
        i.putExtra("Name", classes.get(position).name);
        i.putExtra("ClassSummaryKey", classes.get(position).id);
        i.putExtra("CourseCode", classes.get(position).course);
        i.putExtra("Type", classes.get(position).type);
        i.putExtra("Date", classes.get(position).date);
        i.putExtra("Lecture", classes.get(position).lecture);
        i.putExtra("topic", classes.get(position).topic);
        i.putExtra("Summary", classes.get(position).summary);

        startActivity(i);
      }
    });

    // handle the long-click on an class-summary-list item
    lvClasses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String info = classes.get(position).course + ", " + classes.get(position).topic;
        String message = "Do you want to delete class-summary - " + info + " ?";
        System.out.println(message);
        //showDialog(message, "Delete Event", events.get(position).key);
        return true;
      }
    });

  }
}




