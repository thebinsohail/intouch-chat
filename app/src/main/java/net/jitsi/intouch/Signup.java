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
import com.google.firebase.firestore.FirebaseFirestore;

import net.jitsi.intouch.entity.User;

public class Signup extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    private EditText emailBox,passwordBox,fullNameBox;
    private Button loginRedirect,signUpButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        emailBox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);
        fullNameBox=findViewById(R.id.fullName);



        signUpButton=findViewById(R.id.loginButton);
        loginRedirect=findViewById(R.id.singupRedirect);

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressBar spinner=(ProgressBar)findViewById(R.id.progressBar);
                spinner.setVisibility(View.VISIBLE);


                String email,password,fullName;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                fullName=fullNameBox.getText().toString();

                // getting the user entity
                User user=new User();
                user.setEmail(email);
                user.setFullName(fullName);
                user.setPassword(password);



                firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if(task.isSuccessful()){
                            spinner.setVisibility(View.INVISIBLE);
                            firebaseFirestore.collection("Users")
                                    .document()
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Signup.this,"User "+fullName+" was created!"
                                                    ,Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Signup.this,Login.class));
                                        }
                                    })
                            ;


                        }
                        else{
                            spinner.setVisibility(View.INVISIBLE);
                            Toast.makeText(Signup.this,task.getException()
                                    .getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}