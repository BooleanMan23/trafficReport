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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private  EditText passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        mAuth = FirebaseAuth.getInstance();
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
     }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginButton){
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            boolean apakahFieldKosong = false;

            if(TextUtils.isEmpty(email)){
                apakahFieldKosong = true;
                emailEditText.setError("Email tidak boleh kosong");
            }



            if(TextUtils.isEmpty(password)){
                apakahFieldKosong = true;
                passwordEditText.setError("Password tidak boleh kosong");
            }

            if(!apakahFieldKosong){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i("TAG", "signInWithEmail:success");
                                    Toast.makeText(LoginActivity.this, "Authentication Success.",
                                            Toast.LENGTH_SHORT).show();
                                    //ke activity home
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("TAG", "signInWithEmail:failed");
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

        }
        if(v.getId() == R.id.registerButton){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

}
