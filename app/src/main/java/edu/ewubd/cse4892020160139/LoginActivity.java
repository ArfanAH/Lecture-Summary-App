package edu.ewubd.cse4892020160139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

  EditText userIDEditText, passwordEditText;
  TextView signup;
  Button goButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    SharedPreferences localPref = getSharedPreferences("MyLocalPrefs", MODE_PRIVATE);
    if (localPref.getBoolean("isLogin", false)) {
      Intent loginIntent = new Intent(LoginActivity.this, ClassLecturesActivity.class);
      startActivity(loginIntent);
      finish();
    }


    userIDEditText = findViewById(R.id.userID);
    passwordEditText = findViewById(R.id.password);
    signup = findViewById(R.id.signup);

    goButton = findViewById(R.id.go);

    goButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String userID = userIDEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        String validationMessage = validateInputs(userID,password);
        if (!validationMessage.isEmpty()) {
          Toast.makeText(LoginActivity.this, validationMessage, Toast.LENGTH_SHORT).show();
        }

        else {

          SharedPreferences localPref = getSharedPreferences("MyLocalPrefs", MODE_PRIVATE);
          String savedUserID = localPref.getString("user_id", "");
          String savedPassword = localPref.getString("password", "");

          if (userID.equals(savedUserID) && password.equals(savedPassword)) {
            SharedPreferences.Editor editor = localPref.edit();
            editor.putBoolean("isLogin", true);
            editor.apply();

            Intent classLectureIntent = new Intent(LoginActivity.this, ClassLecturesActivity.class);
            startActivity(classLectureIntent);
            finish();
          } else {
            Toast.makeText(LoginActivity.this, "Invalid input. Please check your entries.", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
    signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
            Intent classLectureIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(classLectureIntent);

      }
    });
  }
  private String validateInputs(String userID, String password) {
    boolean isUserIDValid = userID.matches("[a-zA-Z0-9]{5,10}");
    boolean isPasswordValid = password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");


    if (!isUserIDValid) {
      return "Invalid UserID.";
    } else if (!isPasswordValid) {
      return "Invalid Password.";
    }

    return "";
  }
}