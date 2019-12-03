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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import aib.projektZaliczeniowy.messenger.messagesutils.CustomAdapter;
import aib.projektZaliczeniowy.messenger.messagesutils.messagesClass;

/*
TODO: Sending messages that user typed + while editing is enabled make text view with input message go above keyboard (like in fb messenger)
TODO: make logout btn working
TODO: make current user label displaying current user => now error
TODO: make message cell prettier
*/

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
    public  ArrayList<messagesClass>    allMessages = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initOutlets();
        customizeMessagesView();
//        sendTestingDataToFirebase();
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
           currentUser.setText(String.valueOf(firebaseUser.getDisplayName())); //TODO: WHY THE HELL IS HERE NULL POINTER EXCEPTION????
        } catch (NullPointerException error){
            Log.e("Error", String.valueOf(error));
        }
    }


    private void customizeMessagesView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesView.setLayoutManager(linearLayoutManager);


        updateMessagesViewData(allMessages);
        CustomAdapter customAdapter = new CustomAdapter(allMessages, messagesActivity.this);
        customAdapter.notifyDataSetChanged();
        messagesView.setAdapter(customAdapter);
        messagesView.invalidate();

    }

    private void sendTestingDataToFirebase(){
        /* For testing purposes only */
        sendMessageToFirebase("Wiadomosc 1");
        sendMessageToFirebase("Wiadomosc 2");
        sendMessageToFirebase("Wiadomosc 3");
        /* End*/
    }

    private void updateMessagesViewData(ArrayList<messagesClass> messagesClass){

        /*Segregating messagess by date*/
        messagesClass.sort(
                (p1,p2)->
                        p1.getDate().compareTo(p2.getDate())
        );
        allMessages = messagesClass;
    }


    private void sendMessageToFirebase(String message){
        List<messagesClass> fullMessage = new ArrayList<>();
        Date date = new Date();
        fullMessage.add(new messagesClass(String.valueOf(firebaseUser.getEmail()),message, date.getTime()));
        reference.push().setValue(fullMessage, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                //TODO:what happens after update was pushed
            }
        });
    }

    private void getMessagesFromFirebase(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    int counter = 0; // for debug purposes
                    allMessages.clear();

                    HashMap<String, Object> messages = (HashMap<String, Object>) dataSnapshot.getValue();

                    for(Map.Entry<String,Object> message : messages.entrySet()){
                        ArrayList<Objects> messageTemp = (ArrayList<Objects>) message.getValue();
                        for(Object temp : messageTemp){
                            Log.i(String.valueOf(counter),String.valueOf(temp));
                            counter++;
                            HashMap<String,Object> temp2 = (HashMap<String, Object>) temp;

                            //TODO: get from temp2.get("date") date
                            Long    messageDate = (Long) temp2.get("date");
                            String  author = (String) temp2.get("author");
                            String  trueMessage = (String) temp2.get("message");

                            allMessages.add(new messagesClass(author,trueMessage,messageDate));


                        }
                    }

                    customizeMessagesView();



                }
                catch (NullPointerException error){
                    Log.e("Get message from database error", String.valueOf(error));
                } catch (Error e){
                    Log.e("Error",e.getLocalizedMessage());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO:alert for error with reading from database - some alert idk
            }
        });
    }




}
