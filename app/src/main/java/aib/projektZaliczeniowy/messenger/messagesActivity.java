package aib.projektZaliczeniowy.messenger;

import org.json.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aib.projektZaliczeniowy.messenger.messagesutils.CustomAdapter;
import aib.projektZaliczeniowy.messenger.messagesutils.messagesClass;

public class messagesActivity extends AppCompatActivity {

//    Outlets:
    private Button          logoutButton;
    private TextView        currentUser;
    private TextView        currentMessage;
    private RecyclerView    messagesView;


//    Firebase Variables:
    private FirebaseUser                firebaseUser;
    private FirebaseAuth                mAuth;
    private FirebaseDatabase            database;
    private DatabaseReference           reference;
    public  ArrayList<messagesClass>    messages = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initOutlets();
        customizeMessagesView();
        getMessagesFromFirebase();
    }


//    Utilities Functions

    private void initOutlets(){
        logoutButton = findViewById(R.id.loguotButton);
        currentMessage = findViewById(R.id.inputMessageTextView);
        messagesView = findViewById(R.id.messagesRecyclerView);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("messages");
        firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null){
            Log.i("Jest nullem?","TAK");
        }

        try {
           currentUser.setText(String.valueOf(firebaseUser));
        } catch (NullPointerException error){
            Log.e("Error", String.valueOf(error));
        }
    }


    private void customizeMessagesView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesView.setLayoutManager(linearLayoutManager);

        /* For testing Recycler View only */
        sendMessageToFirebase("Wiadomosc 1");
        sendMessageToFirebase("Wiadomosc 2");
        sendMessageToFirebase("Wiadomosc 3");


        /* End*/

        CustomAdapter customAdapter = new CustomAdapter(messages, messagesActivity.this);
        messagesView.setAdapter(customAdapter);

    }

    private void sendMessageToFirebase(String message){
        List<messagesClass> fullMessage = new ArrayList<>();
        fullMessage.add(new messagesClass(String.valueOf(firebaseUser.getEmail()),message));
//        fullMessage.put(String.valueOf(firebaseUser.getEmail()),message);
        reference.push().setValue(fullMessage, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

            }
        });
    }

    private void getMessagesFromFirebase(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
//                    Map<String ,String> temp = (Map<String, String>) dataSnapshot.getValue();
//                    Log.i("dane", String.valueOf(temp));
////                    messagesClass tempMessage = new messagesClass(temp);
////                    messages.add(tempMessage);

                    //parsowanie danych z JSONA do obiektów typu messagesClass i potem dodanie ich do ArrayListy messages

                }
                catch (NullPointerException error){
                    Log.e("Get message from database error", String.valueOf(error));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //alert for error with reading from database
            }
        });
    }




}
