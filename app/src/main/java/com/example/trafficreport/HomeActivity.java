package com.example.trafficreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        setTitle("Home");
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logoutButton){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.seeTrafficButton){
            Intent intent = new Intent(HomeActivity.this,SeeTrafficAcitivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.reportTrafficButton){
            Intent intent = new Intent(HomeActivity.this,ReportAcitivityClass.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.editTraffic){
            Intent intent = new Intent(HomeActivity.this,SeeEditTrafficActivity.class);
            startActivity(intent);
        }

    }
}
