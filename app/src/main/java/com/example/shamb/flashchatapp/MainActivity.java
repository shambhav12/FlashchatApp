package com.example.shamb.flashchatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView email;
    private TextView password;
    private Button register;
    private Button login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.registerid);
        login = (Button) findViewById(R.id.loginid);
        email = (TextView) findViewById(R.id.emailid);
        password = (TextView) findViewById(R.id.passwordid);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptlogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);



            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

  private void attemptlogin(){
        String emailp = email.getText().toString();
        String passwordp = password.getText().toString();
        if (emailp.equals("")||passwordp.equals("")){
            return;
        }

      Toast.makeText(this,"Login in progress...",Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(emailp,passwordp).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    Log.d("Flash chat", "problem signing in" + task.getException());

                } else {


                    Intent intent = new Intent(MainActivity.this, chat.class);
                    finish();
                    startActivity(intent);
                }

            }
        });

  }

}
