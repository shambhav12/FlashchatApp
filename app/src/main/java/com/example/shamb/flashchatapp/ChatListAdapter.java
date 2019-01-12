package com.example.shamb.flashchatapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ChatListAdapter extends BaseAdapter {
    private Activity mactivity;
    private DatabaseReference mdatareference;
    private String mDisplayname;
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {

        mactivity = activity;
        mDisplayname = name;
        // common error: typo in the db location. Needs to match what's in MainChatActivity.
        mdatareference = ref.child("messages");
        mdatareference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();
    }

    private static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {


        return mSnapshotList.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(InstantMessage.class);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_message_row, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.authorid);
            holder.body = (TextView) convertView.findViewById(R.id.messageidt);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }

        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = message.getAuthor().equals(mDisplayname);

        setChatRowAppearance(isMe, holder);


        String author = message.getAuthor();
        holder.authorName.setText(author);

        String msg = message.getMessage();
        holder.body.setText(msg);


        return convertView;

    }

    private void setChatRowAppearance(boolean isItMe, ViewHolder holder) {

        if (isItMe) {
            holder.params.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.BLACK);

        } else {
            holder.params.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLACK);

        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);

    }



    void cleanup(){
        mdatareference.removeEventListener(mListener);

    }
}
