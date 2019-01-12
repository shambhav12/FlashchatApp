package com.example.shamb.flashchatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

   private AutoCompleteTextView username;
   private AutoCompleteTextView emailaddress;
   private EditText password;
   private EditText confirmpassword;
   private Button signup;
   private FirebaseAuth mAuth;
   private FirebaseUser firebaseUser;
    static final String CHAT_PREFS = "ChatPrefs";
    static final String DISPLAY_NAME_KEY = "username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (AutoCompleteTextView) findViewById(R.id.usernameid);
        emailaddress = (AutoCompleteTextView) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.passwordid);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        signup = (Button) findViewById(R.id.signupid);



        // Keyboard sign in action
       signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               attemptRegistration();
           }
       });
        // TODO: Get hold of an instance of FirebaseAuth

       mAuth = FirebaseAuth.getInstance();
    }

    // Executed when Sign Up button is pressed.


    private void attemptRegistration() {

        // Reset errors displayed in the form.
        emailaddress.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String email = emailaddress.getText().toString();
        String passwordp =password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordp) || !isPasswordValid(passwordp)) {

            password.setError("Enter a valid password, It should have atleast 6 characters");
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {

            emailaddress.setError("Enter email address");
                    focusView = emailaddress;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailaddress.setError("Enter a valid email address");
            focusView = emailaddress;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            createFirebaseuser();
            // TODO: Call create FirebaseUser() here

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String confrimpasswordp = confirmpassword.getText().toString() ;
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        if (!confrimpasswordp.equals(password)){
            confirmpassword.setError("Password does not match");

        }
        return  password.length() > 5;
    }
public void showdisplayname(){

        String displayname = username.getText().toString();
    SharedPreferences sharedPreferences = getSharedPreferences(CHAT_PREFS,0);
    sharedPreferences.edit().putString(DISPLAY_NAME_KEY,displayname).apply();
}


    // TODO: Create a Firebase user
     private void createFirebaseuser(){
        final String email = emailaddress.getText().toString();
        String passwordfire = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,passwordfire).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Toast.makeText(getApplicationContext(),"Flashchat"+"succesful",Toast.LENGTH_SHORT).show();


                if (!task.isSuccessful()){

                }
                else
                {
                    saveDisplayName();
                    Intent intent = new Intent(register.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

        });

         }
    private void saveDisplayName() {
        String displayName = username.getText().toString();
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }


}

    // TODO: Save the display name to Shared Preferences


    // TODO: Create an alert dialog to show in case registration failed





