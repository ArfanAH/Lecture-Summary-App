package edu.ewubd.cse4892020160139;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

  EditText nameEditText, emailEditText, phoneEditText, userIDEditText, passwordEditText, rePasswordEditText;
  TextView login;
  Button goButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);
    SharedPreferences localPref = getSharedPreferences("MyLocalPrefs", MODE_PRIVATE);
    if (localPref.getBoolean("isLogin", false)) {
      Intent loginIntent = new Intent(SignupActivity.this, ClassLecturesActivity.class);
      startActivity(loginIntent);
      finish();
    }

    nameEditText = findViewById(R.id.name);
    emailEditText = findViewById(R.id.email);
    phoneEditText = findViewById(R.id.phone);
    userIDEditText = findViewById(R.id.userID);
    passwordEditText = findViewById(R.id.password);
    rePasswordEditText = findViewById(R.id.rePassword);
    login = findViewById(R.id.login);
    goButton = findViewById(R.id.go);

    goButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String userID = userIDEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String rePassword = rePasswordEditText.getText().toString();

        String validationMessage = validateInputs(name, email, phone, userID, password, rePassword);
        if (!validationMessage.isEmpty()) {
          Toast.makeText(SignupActivity.this, validationMessage, Toast.LENGTH_SHORT).show();
        }
        else {

          SharedPreferences localPref = getSharedPreferences("MyLocalPrefs", MODE_PRIVATE);
          SharedPreferences.Editor editor = localPref.edit();

          editor.putString("name", name);
          editor.putString("email", email);
          editor.putString("phone", phone);
          editor.putString("user_id", userID);
          editor.putString("password", password);
          editor.putBoolean("remember-login", false);
          editor.putBoolean("remember-user-id", false);
          editor.putBoolean("isLogin", true);
          editor.apply();

          Intent loginIntent = new Intent(SignupActivity.this, ClassLecturesActivity.class);
          startActivity(loginIntent);
          finish();
        }
      }
    });
    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
          startActivity(loginIntent);
          finish();

      }
    });
  }
  private String validateInputs(String name, String email, String phone, String userID, String password, String rePassword) {
    boolean isNameValid = name.matches("[a-zA-Z ]{4,15}+");
    boolean isEmailValid = email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    boolean isPhoneValid = phone.matches("\\d{11}");
    boolean isUserIDValid = userID.matches("[a-zA-Z0-9]{5,10}");
    boolean isPasswordValid = password.matches("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$");
    boolean passwordsMatch = password.equals(rePassword);
    boolean empty = userID.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || rePassword.isEmpty() || email.isEmpty();
    if (empty) {
      return "You have to fill all the inputs correctly.";
    } else if (!isNameValid) {
      return "Invalid name. It should contain 4 to 15 characters.";
    } else if (!isEmailValid) {
      return "Invalid email address.";
    } else if (!isPhoneValid) {
      return "Invalid phone number.";
    } else if (!isUserIDValid) {
      return "Invalid user ID.";
    } else if (!isPasswordValid) {
      return "Invalid password.";
    } else if (!passwordsMatch) {
      return "Passwords do not match.";
    }

    return "";
  }


}
