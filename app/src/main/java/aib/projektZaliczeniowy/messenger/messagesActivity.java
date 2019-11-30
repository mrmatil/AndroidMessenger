package aib.projektZaliczeniowy.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class messagesActivity extends AppCompatActivity {

//    Outlets:
    private Button          logoutButton;
    private TextView        currentUser;
    private TextView        currentMessage;
    private RecyclerView    messagesView;


//    Firebase Variables:
    private FirebaseUser    firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initOutlets();
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
           String user = firebaseUser.getProviderId();
           currentUser.setText(user);
        } catch (NullPointerException error){
            Log.e("Error", String.valueOf(error));
        }
    }





}
