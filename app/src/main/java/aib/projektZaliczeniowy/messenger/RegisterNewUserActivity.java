package aib.projektZaliczeniowy.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterNewUserActivity extends AppCompatActivity {


//    Outlets:

    private TextView    emailTextView;
    private TextView    passwordTextView;
    private Button      loginButton;
    private Button      registerButton;

//    FireBase Variables:

    private FirebaseAuth    mAuth;
    private String          email;
    private String          password;


//    Override Functions:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        initOutlets();

        /* Linking auth with firebase */
        mAuth = FirebaseAuth.getInstance();

    }

//    Buttons Functions
    public void registerButtonPressed(View view){
        if(!emailTextView.getText().toString().matches("") && !passwordTextView.getText().toString().matches("")){
            /* email and password are not empty */
            email = String.valueOf(emailTextView.getText());
            password = String.valueOf(passwordTextView.getText());
            Log.i("email",email);
            Log.i("password",password);
            firebaseSignUpCheck();
            // maybe add some animation while waiting for result

        }else{
            Toast.makeText(
                    RegisterNewUserActivity.this,
                    "Proszę wpisać poprawne dane",
                    Toast.LENGTH_LONG
            );
        }
    }

    public void loginButtonPressed(View view){
        this.finish();
    }


//    Utilities Functions
    private void initOutlets(){
        emailTextView       = findViewById(R.id.emailTextViewR);
        passwordTextView    = findViewById(R.id.passwordTextViewR);
        loginButton         = findViewById(R.id.loginButtonR);
        registerButton      = findViewById(R.id.RegisterButtonR);
    }

    private void firebaseSignUpCheck(){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            /* if sign in was success*/
                            Log.i("sign in:","OK");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //seguey to next view
                            segueyToMessages(user);

                        }else{
                            /* if sign in failed*/
                            Log.i("Sign in","failed" + task.getException());
                            Toast.makeText(
                                    RegisterNewUserActivity.this,
                                    "Autoryzacja nie powiodła się , bo " + task.getException(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                });
    }

    private void segueyToMessages(FirebaseUser user){
        Intent goToMessages = new Intent(RegisterNewUserActivity.this, messagesActivity.class);
        RegisterNewUserActivity.this.startActivity(goToMessages);
    }


}
