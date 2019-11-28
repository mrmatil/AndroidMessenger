package aib.projektZaliczeniowy.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
    }
}
