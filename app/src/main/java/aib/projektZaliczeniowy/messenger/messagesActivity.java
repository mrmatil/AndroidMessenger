package aib.projektZaliczeniowy.messenger;

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

import java.util.ArrayList;

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
    public  ArrayList<messagesClass>    messages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initOutlets();
        customizeMessagesView();
    }


//    Utilities Functions

    private void initOutlets(){
        logoutButton = findViewById(R.id.loguotButton);
        currentMessage = findViewById(R.id.inputMessageTextView);
        messagesView = findViewById(R.id.messagesRecyclerView);
        firebaseUser = (FirebaseUser) getIntent().getSerializableExtra("user");

        if (firebaseUser == null){
            Log.i("Jest nullem?","TAK");
        }

        try {
//           String user = firebaseUser.getProviderId();
//           currentUser.setText(user);
        } catch (NullPointerException error){
            Log.e("Error", String.valueOf(error));
        }
    }


    private void customizeMessagesView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesView.setLayoutManager(linearLayoutManager);

        /* For testing Recycler View only */
            messages = new ArrayList<messagesClass>();
            messages.add(new messagesClass("Autor1","Wiadomość 1"));
            messages.add(new messagesClass("Autor2","Wiadomość 2"));
            messages.add(new messagesClass("Autor3","Wiadomość 3"));
            messages.add(new messagesClass("Autor1","Wiadomość 1"));
            messages.add(new messagesClass("Autor2","Wiadomość 2"));
            messages.add(new messagesClass("Autor3","Wiadomość 3"));
            messages.add(new messagesClass("Autor1","Wiadomość 1"));
            messages.add(new messagesClass("Autor2","Wiadomość 2"));
            messages.add(new messagesClass("Autor3","Wiadomość 3"));

        /* End*/

        CustomAdapter customAdapter = new CustomAdapter(messages, messagesActivity.this);
        messagesView.setAdapter(customAdapter);

    }




}
