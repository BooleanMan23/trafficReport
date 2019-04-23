package com.example.trafficreport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Register");
        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        user =  FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.registerButton){
            String email =emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Check bila ada form yang kosong
            boolean apakahFieldKosong = false;

            if(TextUtils.isEmpty(email)){
                apakahFieldKosong = true;
                emailEditText.setError("Email tidak boleh kosong");
            }



            if(TextUtils.isEmpty(password)){
                apakahFieldKosong = true;
                passwordEditText.setError("Password tidak boleh kosong");
            }
            //Jika field tidak kosong maka akan register
            if(!apakahFieldKosong){
                Log.i("Info Register", email);
                Log.i("Info Register", password);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i("TAG", "createUserWithEmail:success");
                                    Toast.makeText(MainActivity.this, "Register Success.",
                                            Toast.LENGTH_SHORT).show();

                                    //ke activity home
                                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Register failed.",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });

            }
        }
        //Jika memencet button login
        if(v.getId() == R.id.loginButton){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }

    }
}
