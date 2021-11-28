package net.jitsi.intouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText emailBox,passwordBox;
    private Button loginButton,signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        emailBox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);

        loginButton=findViewById(R.id.loginButton);
        signUpButton=findViewById(R.id.singupRedirect);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressBar spinner=findViewById(R.id.progressBarLogin);
                spinner.setVisibility(View.VISIBLE);

                String email,password;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();



                firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            spinner.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this,"Logged in as "+email,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,Dashboard.class));
                        }
                        else{
                            spinner.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this,task.getException()
                                    .getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

    }
}