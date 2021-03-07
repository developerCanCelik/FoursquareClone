package com.cancelik.foursquareclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText userName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userName = findViewById(R.id.user_name_signup_activity);
        password = findViewById(R.id.password_text_signup_activity);

        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser != null){
            Intent intent = new Intent(SignUpActivity.this,LocationsActivity.class);
            startActivity(intent);
        }
    }
    public void signIn(View view){
        ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignUpActivity.this, "Welcome:"+" "+user.getUsername(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LocationsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }
        });
    }
    public void signUp(View view){
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(userName.getText().toString());
        parseUser.setPassword(password.getText().toString());
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignUpActivity.this, "User Signed Up!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LocationsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }
        });
    }
}