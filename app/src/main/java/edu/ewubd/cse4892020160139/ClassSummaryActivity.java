package edu.ewubd.cse4892020160139;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ClassSummaryActivity extends AppCompatActivity {

    EditText date, lectureNum, topic, summary;
    RadioGroup radiogrp1, radiogrp2;
    TextView name, id;
    String course = "";
    RadioButton button, button1;
    String type = "", Name = "", Id = "", Date = "", lecture = "", topics = "", Summary = "";
    Button Save,cancel;
    Integer UniqueID = (int) System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_summary);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        radiogrp1 = findViewById(R.id.radiogrp1);
        radiogrp2 = findViewById(R.id.radiogrp2);
        date = findViewById(R.id.date);
        lectureNum = findViewById(R.id.lectureNum);
        topic = findViewById(R.id.topic);
        summary = findViewById(R.id.summary);

        Save = findViewById(R.id.btnsave);
        cancel = findViewById(R.id.btncancel);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radiobtnId = radiogrp1.getCheckedRadioButtonId();
                int radiobtn1Id = radiogrp2.getCheckedRadioButtonId();

                if (radiobtnId != -1) {
                    button = findViewById(radiobtnId);
                    course = button.getText().toString();
                }

                if (radiobtn1Id != -1) {
                    button1 = findViewById(radiobtn1Id);
                    type = button1.getText().toString();
                }

                if (!date.getText().toString().isEmpty() && !lectureNum.getText().toString().isEmpty()
                    && !topic.getText().toString().isEmpty() && !summary.getText().toString().isEmpty()) {
                    Name = name.getText().toString();
                    Id = id.getText().toString();
                    lecture = lectureNum.getText().toString();
                    topics = topic.getText().toString();
                    Summary = summary.getText().toString();
                    Date = date.getText().toString();

                    LectureSummaryDB myDb = new LectureSummaryDB(ClassSummaryActivity.this);

                    if (myDb.hasRecord(UniqueID)) {
                        // Update method
                        myDb.updateLectureSummaryDB(UniqueID,Name,Id,course,type, Date, lecture, topics, Summary);
                    } else {
                        // Insert method
                        myDb.insertLectureSummaryDB(UniqueID,Name,Id,course,type, Date, lecture, topics, Summary);
//                        System.out.println(UniqueID+Name+Id+course+type+Date+lecture+topics+Summary);
                        String UniqueIDString = Integer.toString(UniqueID);
                        String key[]={"action", "sid", "semester", "id", "course", "type", "topic", "date", "lecture",
                                "summary"};
                        String values[]={"backup", "2020-1-60-139", "2023-3",UniqueIDString, course, type, topics,Date, lecture, topics, Summary};
                        httpRequest(key,values);


                    }

                    finish();
                    Intent i = new Intent(ClassSummaryActivity.this, ClassLecturesActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ClassSummaryActivity.this, "Please input all entries", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classLectureIntent = new Intent(ClassSummaryActivity.this, ClassLecturesActivity.class);
                startActivity(classLectureIntent);

            }
        });

    }
    private void httpRequest(final String keys[],final String values[]){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                for (int i=0; i<keys.length; i++){
                    params.add(new BasicNameValuePair(keys[i],values[i]));
                }
                String url= "https://www.muthosoft.com/univ/cse489/index.php";
                String data="";
                try {
                    data=JSONParser.getInstance().makeHttpRequest(url,"POST",params);
                    System.out.println(data);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){
                if(data!=null){
                    System.out.println(data);
                    System.out.println("Ok2");
//                    updateClassSummaryListByServerData(data);
//                    Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
