package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowResults extends AppCompatActivity {
String re1;
TextView re;
RecyclerView rere;
MedAdapter ad;
List<Med> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        re=(TextView) findViewById(R.id.re);
        rere=(RecyclerView) findViewById(R.id.rere);
        re1=getIntent().getStringExtra("Re");
        rere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ad=new MedAdapter(getApplicationContext(),list);
        rere.setAdapter(ad);
        re.setText("Showing results for "+re1);
        FirebaseDatabase.getInstance().getReference("AllMedicines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren())
                {
                    Med de=s.getValue(Med.class);
                    re1=re1.toLowerCase();
                    String n=de.getMname().toLowerCase();
                    Toast.makeText(getApplicationContext(),n+" "+re1,Toast.LENGTH_LONG).show();
                    if(n.contains(re1)||n.equals(re1)||re1.contains(n))
                    list.add(de);
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}