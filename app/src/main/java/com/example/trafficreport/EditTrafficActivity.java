package com.example.trafficreport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTrafficActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namaJalanEditText;
    private EditText deskripsiJalanEditText;
    private TextView lokasiDiMapTextViewEdit;
    public  String idTrafficReport;
    public  String namaJalan;
    public  String deskripsiJalan;
    private  String lokasiPeta;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    DatabaseReference myRefeee;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_traffic);

        Intent intent2 = getIntent();
        lokasiPeta = intent2.getStringExtra("alamat");



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    namaJalanEditText = (EditText) findViewById(R.id.namaJalanEditTextEdit);
    deskripsiJalanEditText  = (EditText) findViewById(R.id.deskripsiEditTextEdit);
    lokasiDiMapTextViewEdit = (TextView) findViewById(R.id.lokasiDiMapTextViewEdit);
        if(lokasiPeta != null && !lokasiPeta.isEmpty())
        { /* do your stuffs here */
            lokasiDiMapTextViewEdit.setText(lokasiPeta);

        }

        Intent intent = getIntent();
        idTrafficReport = intent.getStringExtra("trafficReportId");
        setTitle("Edit " + idTrafficReport);
//        Log.i("Traffic Report ID", idTrafficReport);
        //download traffic data
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Class FirebaseDatabase merujuk ke database dari projek
      myRefeee = database.getReference();


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.updateButtonEdit){
            try {
                String namaJalan = namaJalanEditText.getText().toString() + "(Updated)";
                String deskripsiJalan = deskripsiJalanEditText.getText().toString() + "(Updated)";
                Log.i("InformasiReportTraffic", namaJalan);
                Log.i("InformasiDeskripsiJalan", deskripsiJalan);
                updateTrafficToDatabase(namaJalan, deskripsiJalan);
                Toast.makeText(this, "Laporan Jalan Telah diupdate",
                        Toast.LENGTH_SHORT).show();


            }
            catch (Exception E){



            }

        }
        if(v.getId() == R.id.deleteButtonEdit){

            deleteTraffic();

        }
        if(v.getId() == R.id.mapButtonEdit){
            Intent intent = new Intent(EditTrafficActivity.this, MapsActivity.class);
            intent.putExtra("dari", "Edit Traffic");
            intent.putExtra("trafficReportId", idTrafficReport);
            startActivity(intent);
        }
    }

    public void updateTrafficToDatabase(String namaJalan, String deskripsiJalan){
        Log.i("Updating to database", "Updating Traffic to database");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        //TANGGAL
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tanggalReport = sdf.format(new Date());

        Log.i("InformasiReportTraffic", tanggalReport);


        String idUser = mAuth.getUid().toString();
        String trafficId = idTrafficReport;
        Log.i("informasiReportTraffic", idUser);
        Log.i("informasiReportTraffic", trafficId);
        TrafficReports newTrafficReport = new TrafficReports(namaJalan, deskripsiJalan, idUser, trafficId, tanggalReport, lokasiPeta);
        myRef.child("Traffic Reports").child(trafficId).setValue(newTrafficReport);

        Intent intent = new Intent(EditTrafficActivity.this, SeeTrafficAcitivity.class);
        startActivity(intent);



    }

    public void deleteTraffic(){
        try{

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("Traffic Reports").child(idTrafficReport);
            myRef.removeValue();
            Intent intent = new Intent(EditTrafficActivity.this, SeeTrafficAcitivity.class);
            startActivity(intent);
            Toast.makeText(this, "Laporan Jalan Telah didelete",
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception E){

        }




    }
}
