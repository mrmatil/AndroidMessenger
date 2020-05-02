package aib.projektZaliczeniowy.messenger;

import org.json.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.tbruyelle.rxpermissions2.RxPermissions;

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

import aib.projektZaliczeniowy.messenger.mapsUtils.Coordinates;
import aib.projektZaliczeniowy.messenger.messagesutils.CustomAdapter;
import aib.projektZaliczeniowy.messenger.messagesutils.messagesClass;

/*
TODO: make message cell prettier
*/

public class messagesActivity extends AppCompatActivity implements CustomAdapter.ItemClickListener{

    //    Outlets:
    private Button          logoutButton;
    private TextView        currentUser;
    private TextView        currentMessage;
    private RecyclerView    messagesView;
    private SharedPreferences prefs;

    //    Firebase Variables:
    private FirebaseUser                firebaseUser;
    private FirebaseAuth                mAuth;
    private FirebaseDatabase            database;
    private DatabaseReference           reference;
    public  ArrayList<messagesClass>    allMessages = new ArrayList<>();

    //    RxPermissions
    private RxPermissions rxPermissions = new RxPermissions(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initOutlets();
        customizeMessagesView();
//        sendTestingDataToFirebase();
        getMessagesFromFirebase();
        getPermissions();
        prefs =  this.getSharedPreferences("Coordinates", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            sendMessageToFirebase(String.valueOf(currentMessage.getText()));
            currentMessage.setText("");

            return super.onKeyUp(keyCode, event);
        }else{
            return super.onKeyUp(keyCode, event);
        }

    }

    public void logoutButtonPressed(View view){
        try {
            mAuth.signOut();
            this.finish();
        } catch (Error e){
            Toast.makeText(messagesActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    }
//    Utilities Functions

    private void initOutlets(){
        logoutButton    = findViewById(R.id.loguotButton);
        currentMessage  = findViewById(R.id.inputMessageTextView);
        messagesView    = findViewById(R.id.messagesRecyclerView);
        currentUser     = findViewById(R.id.whoAmITextView);
        mAuth           = FirebaseAuth.getInstance();
        database        = FirebaseDatabase.getInstance();
        reference       = database.getReference().child("messages");
        firebaseUser    = mAuth.getCurrentUser();

        if (firebaseUser == null){
            Log.i("Jest nullem?","TAK");
        }

        try {
           String tempUser = firebaseUser.getEmail();
           currentUser.setText(String.valueOf(tempUser));
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
        if(message.equals("")) return;
        List<messagesClass> fullMessage = new ArrayList<>();
        Date date = new Date();

        Float longt = prefs.getFloat("Longitude",(float) 1);
        Float latit = prefs.getFloat("Latitude", (float) 2);

        fullMessage.add(new messagesClass(String.valueOf(firebaseUser.getEmail()),message, date.getTime(),longt.toString(),latit.toString()));

        reference.push().setValue(fullMessage, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                getMessagesFromFirebase();
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
                            String longitude = temp2.get("langitude").toString();
                            String latitude = temp2.get("lognitude").toString();

                            allMessages.add(new messagesClass(author,trueMessage,messageDate,longitude,latitude));

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

    public void getPermissions(){
        //TODO: Do something if permission is not granted
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe( granted ->{
                    if (granted){
                        //permission granted

                    } else{
                        //permission declined
                    }
                });

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Coordinates coordinates = new Coordinates(this, locationManager);
        coordinates.startCoordinatesListener();

    }


    @Override
    public void onItemClick(View view, int position) {
        Log.i("Position: ", String.valueOf(position));
    }
}
