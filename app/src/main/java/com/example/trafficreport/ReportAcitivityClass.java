package com.example.trafficreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportAcitivityClass extends AppCompatActivity implements View.OnClickListener {

    private String lokasiPeta;
    private EditText namaJalanEditText;
    private EditText deskripsiJalanEditText;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private TextView lokasiPadaMapTextView;
    private  String alamatDariMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_acitivity_class);

        Intent intent = getIntent();
       lokasiPeta = intent.getStringExtra("alamat");


      namaJalanEditText = (EditText) findViewById(R.id.namaJalanEditText);
      deskripsiJalanEditText = (EditText) findViewById(R.id.deskripsiEditText);
      lokasiPadaMapTextView = (TextView) findViewById(R.id.lokasiDiMapTextView);
        if(lokasiPeta != null && !lokasiPeta.isEmpty())
        { /* do your stuffs here */
            alamatDariMap = lokasiPeta;
            lokasiPadaMapTextView.setText(alamatDariMap.toString());

        }

      mAuth = FirebaseAuth.getInstance();
      user = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.reportButton){
                try {
                    String namaJalan = namaJalanEditText.getText().toString();
                    String deskripsiJalan = deskripsiJalanEditText.getText().toString();
                    Log.i("InformasiReportTraffic", namaJalan);
                    Log.i("InformasiDeskripsiJalan", deskripsiJalan);
                    uploadTrafficToDatabase(namaJalan, deskripsiJalan);
                    Toast.makeText(this, "Laporan Jalan Telah terupload",
                            Toast.LENGTH_SHORT).show();


                }
                catch (Exception E){



                }
            }
            if(v.getId() == R.id.mapButton){
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);

            }
    }

    public  void  uploadTrafficToDatabase(String namaJalan, String deskripsiJalan){

        Log.i("Uploading to database", "Uploading Traffic to database");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        //TANGGAL
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tanggalReport = sdf.format(new Date());

        Log.i("InformasiReportTraffic", tanggalReport);


        String idUser = mAuth.getUid().toString();
        String trafficId = myRef.push().getKey();
        Log.i("informasiReportTraffic", idUser);
        Log.i("informasiReportTraffic", trafficId);
        TrafficReports newTrafficReport = new TrafficReports(namaJalan, deskripsiJalan, idUser, trafficId, tanggalReport, lokasiPeta);
      Log.i("jalan lokasi ", newTrafficReport.getJalanLokasi()) ;
        Log.i("lokasi peta", lokasiPeta);
        myRef.child("Traffic Reports").child(trafficId).setValue(newTrafficReport);


    }


}
