package aib.projektZaliczeniowy.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

//    Outlets:

    private TextView    emailTextView;
    private TextView    passwordTextView;
    private Button      loginButton;
    private Button      registerButton;
    private Button      dontRememberButton;

//    FireBase Variables:

    private FirebaseAuth mAuth;
    private String email;
    private String password;


//    Override Functions:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initOutlets();

        /* Linking auth with firebase */
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart(){
        super.onStart();
        /* checking if user is already logged in */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.i("Current User = ", String.valueOf(currentUser)); // add seguey to automaticly go to next view if loggedin
    }

//    Buttons Functions
    public void logInButtonPressed(View view){
        if(!emailTextView.getText().toString().matches("") && !passwordTextView.getText().toString().matches("")){
            /* email and password are not empty */
            email = String.valueOf(emailTextView.getText());
            password = String.valueOf(passwordTextView.getText());
            Log.i("email",email);
            Log.i("password",password);
            firebaseLogInCheck();
        }else{

        }


//        firebaseLogInCheck();
    }

//    Utilities Functions:

    void initOutlets(){
        emailTextView       = findViewById(R.id.emailTextView);
        passwordTextView    = findViewById(R.id.passwordTextView);
        loginButton         = findViewById(R.id.loginButton);
        registerButton      = findViewById(R.id.registerButton);
        dontRememberButton  = findViewById(R.id.lostPasswordButton);
    }

    void firebaseLogInCheck(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            /* if sing in was success*/
                            Log.i("Log in:","OK");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //transfer user to next view
                        } else {
                            /* if sign in failed*/
                            Log.i("Log in","failed");
                            Toast.makeText(
                                    MainActivity.this,
                                    "Autoryzacja nie powiodła się ",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                });
    }





}
