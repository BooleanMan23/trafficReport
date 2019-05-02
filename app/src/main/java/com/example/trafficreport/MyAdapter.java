package com.example.trafficreport;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<TrafficReports> listTraffics;
    private Context context;

    public MyAdapter(List<TrafficReports> listTraffics, Context context) {
        this.listTraffics = listTraffics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from( viewGroup.getContext())
                .inflate(R.layout.list_traffic, viewGroup, false);

        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TrafficReports listTraffic = listTraffics.get(i);
        viewHolder.headerTextView.setText(listTraffic.getNamaJalan());
        viewHolder.descriptionTextView.setText(listTraffic.getTanggalReport());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String activityapa = v.getContext().toString();
                Log.i("activity", activityapa);
                if(activityapa.contains("Edit")){
                    Intent intent = new Intent (v.getContext(), EditTrafficActivity.class);
                    intent.putExtra("trafficReportId", listTraffic.getIdReport());
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent (v.getContext(), TrafficDetailActivity.class);
                    intent.putExtra("trafficReportId", listTraffic.getIdReport());
                    context.startActivity(intent);
                }


        }
        });

    }

    @Override
    public int getItemCount() {
        return listTraffics.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView headerTextView;
        public  TextView descriptionTextView;
        public LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            headerTextView = (TextView) itemView.findViewById(R.id.jalanTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tanggalTextView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
