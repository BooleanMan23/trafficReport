package com.example.trafficreport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrafficDetailActivity extends AppCompatActivity {

    private TextView namaJalanTextView;
    private TextView deskripsiJalanTextView;
    private TextView tanggalDibuatTextView;
    public  String idTrafficReport;
    public  String namaJalan;
    public  String deskripsiJalan;
    public  String tanggalReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_detail);

        setTitle("Detail Traffic");

        namaJalanTextView = (TextView) findViewById(R.id.detailNamaJalanTextView);
        deskripsiJalanTextView = (TextView) findViewById(R.id.detailDeskripsiJalanTextView);
        tanggalDibuatTextView = (TextView) findViewById(R.id.detailTanggalDibuatTextView);

        Intent intent = getIntent();
        idTrafficReport = intent.getStringExtra("trafficReportId");
        Log.i("Traffic Report ID", idTrafficReport);


        //download traffic data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Class FirebaseDatabase merujuk ke database dari projek
        DatabaseReference myRef = database.getReference();
        myRef.child("Traffic Reports").child(idTrafficReport).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                namaJalan = dataSnapshot.child("namaJalan").getValue().toString();
                tanggalReport = dataSnapshot.child("tanggalReport").getValue().toString();
                deskripsiJalan = dataSnapshot.child("deskripsi").getValue().toString();
                namaJalanTextView.setText(namaJalan);
                deskripsiJalanTextView.setText(deskripsiJalan);
                tanggalDibuatTextView.setText(tanggalReport);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
