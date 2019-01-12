package com.example.shamb.flashchatapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chat extends AppCompatActivity {


    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private Button mSendButton;
    private DatabaseReference databaseReference;
    private ChatListAdapter mAdapter;
    private ImageButton imageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();

         databaseReference = FirebaseDatabase.getInstance().getReference();

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageid);
        imageButton = (ImageButton) findViewById(R.id.sendid);
        mChatListView = (ListView) findViewById(R.id.chatlist);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

            }
        });
        // TODO: Send the message when the "enter" button is pressed


        // TODO: Add an OnClickListener to the sendButton to send a message

    }

    private void setupDisplayName(){

        SharedPreferences prefs = getSharedPreferences(register.CHAT_PREFS, MODE_PRIVATE);

        mDisplayName = prefs.getString(register.DISPLAY_NAME_KEY, null);

        if (mDisplayName == null) mDisplayName = "Anonymous";
    }

    // TODO: Retrieve the display name from the Shared Preferences


    private void sendMessage() {
        Log.d("Flash chat","I sent something");
        String input = mInputText.getText().toString();
        if (!input.equals("")){
            InstantMessage chat = new InstantMessage(input,mDisplayName);
            databaseReference.child("messages").push().setValue(chat);
            mInputText.setText("");
        }

        // TODO: Grab the text the user typed in and push the message to Firebase

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this, databaseReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }



    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Fireb
        // ase event listener on the adapter.

    }

}



