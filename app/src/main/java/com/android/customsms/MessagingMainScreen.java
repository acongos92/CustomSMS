package com.android.customsms;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.MessageDisplayAdapter;
import Backend.MessageSender;

public class MessagingMainScreen extends AppCompatActivity {
    MessageSender messageList = new MessageSender();
    RecyclerView recyclerView;
    MessageDisplayAdapter messageDisplayAdapter;
    Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_main_screen);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeToast("Send was clicked");
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            //refreshSmsInbox();
        }
        /*
         * Recycler view setup
         */

        recyclerView = (RecyclerView) this.findViewById(R.id.rv_message_list);
        messageDisplayAdapter = new MessageDisplayAdapter(this, messageList);
        recyclerView.setAdapter(messageDisplayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshSmsInbox();
    }


    /*
     * permission check and requests
     */

    public void getPermissionToReadSMS(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)){
                makeToast("Please allow permission");
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                //refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Convenience methods

    private void makeToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()){
            return;
        }
        do {
            String name = "SMS From: " + smsInboxCursor.getString(indexAddress);
            String message = smsInboxCursor.getString(indexBody);
            messageList.addNewMessage(name,message);
            messageDisplayAdapter.notifyDataSetChanged();
        } while (smsInboxCursor.moveToNext());
    }
}
