package com.example.trafficreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;




public class SeeEditTrafficActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<TrafficReports> listTraffics;
    private FirebaseAuth mAuth;
    private  String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_edit_traffic);
        setTitle("Edit Traffic");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listTraffics = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Traffic Reports");
        idUser = currentUser.getUid();
        Log.i("IDUSER", idUser);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot trafficReportSnapshot : dataSnapshot.getChildren()){
                    String deskripsi = trafficReportSnapshot.child("deskripsi").getValue().toString();
                    String idPembuat = trafficReportSnapshot.child("idPembuat").getValue().toString();
                    String idReport = trafficReportSnapshot.child("idReport").getValue().toString();
                    String namaJalan = trafficReportSnapshot.child("namaJalan").getValue().toString();
                    String tanggalReport = trafficReportSnapshot.child("tanggalReport").getValue().toString();

                    Log.i("MengambilDataTrafficD", deskripsi);
                    Log.i("MengambilDataTrafficIDP", idPembuat);
                    Log.i("MengambilDataTrafficIDR", idReport);
                    Log.i("MengambilDataTrafficNJ", namaJalan);
                    Log.i("MengambilDataTrafficTR", tanggalReport);


                    if(idPembuat.equals(idUser)){
                        TrafficReports listTraffic = new TrafficReports(namaJalan, deskripsi, idPembuat, idReport, tanggalReport, "3");
                        listTraffics.add(listTraffic);
                    }







                }
                //        //SET ADAPTER TO RECYCLER VIEW
                adapter =  new MyAdapter(listTraffics, SeeEditTrafficActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Mengambil dari database", "Failed to read value.", error.toException());
            }
        });

//        for(int i = 0;i<=10 ; i++){
//
//            TrafficReports listItem = new TrafficReports("1","1","1","1","1");
//            listTraffics.add(listItem);
//        }
//
//        //SET ADAPTER TO RECYCLER VIEW
//        adapter =  new MyAdapter(listTraffics, this);
//        recyclerView.setAdapter(adapter);

    }
}
