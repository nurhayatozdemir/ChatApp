package com.example.nurhayat.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    EditText messageText;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private ArrayList<String> chatMessages = new ArrayList<>();

    //-----------------Menuyu Bağlama-----------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------Menuye tıklandıgında ne olacak-------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.options_menu_sign_out) {
            mAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.options_menu_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.options_menu_setting){
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //------------------Tanımlamalar-------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //chatMessages.add("nurhayat");
        //chatMessages.add("ozdemir");
        //System.out.println("chatMessages"+ chatMessages);
        messageText = findViewById(R.id.chat_activity_message_text);
        recyclerView = findViewById(R.id.recycler_view);
        //Firebaseden aldığım verileri bu diziye bu diziden aldıgım verileri de recylerviewadaptöre , recylecleviewadaptorden de recycleview e iletmiş olacağız
        recyclerViewAdapter = new RecyclerViewAdapter(chatMessages);
        RecyclerView.LayoutManager recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter); //recyclerView ile recyclerViewAdapter ı birbirine bağladık
        mAuth = FirebaseAuth.getInstance();
    //---------------------database--------------------
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
        getData();     

    //----------------------Mesaj Göndere Tıklandıgında-------------
    public void sendMessage(View view){
  //----------------firebase e verileri kayddetme----------------
         String messageToSend = messageText.getText().toString();
        //databaseReference.child("Chats").child("Chat 1").child("Test Chat").child("Test 2").setValue(messageToSend);
        UUID uuıd = UUID.randomUUID();
        String uuidString = uuıd.toString();
        FirebaseUser user =mAuth.getCurrentUser();
        String userEmail = user.getEmail().toString();
        databaseReference.child("Chats").child(uuidString).child("usermessage").setValue(messageToSend);
        databaseReference.child("Chats").child(uuidString).child("useremail").setValue(userEmail);
        databaseReference.child("Chats").child(uuidString).child("usermessagetime").setValue(ServerValue.TIMESTAMP);
        messageText.setText("");
        getData();
    }

    //-----------------Mesajları databaseden çekme-------------------------
    public void getData() {
        DatabaseReference newReference = database.getReference("Chats");
        Query query = newReference.orderByChild("usermessagetime");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //databasedeki verilerin bir görüntüsünü getir
                //System.out.println("dataSnapshot Children: " + dataSnapshot.getChildren());
                //System.out.println("dataSnapshot Value: " + dataSnapshot.getValue());
                //System.out.println("dataSnapshot Key: " + dataSnapshot.getKey());
                chatMessages.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //System.out.println("data value: " + ds.getValue());
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String useremail = hashMap.get("useremail");
                    String usermessage = hashMap.get("usermessage");
                    chatMessages.add(useremail + ": " + usermessage);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
