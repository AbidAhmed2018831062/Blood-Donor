package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowResults extends AppCompatActivity {
String re1;
TextView re;
RecyclerView rere;
MedAdapter ad;
String[] ty=new String [5];
int re3=0;
    GridView gr;
    String email,email1="",url,name;
    GridAdapter agr;
List<Med> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_results);

        re=(TextView) findViewById(R.id.re);
        gr=(GridView) findViewById(R.id.grid);
        rere=(RecyclerView) findViewById(R.id.rere);
        re1=getIntent().getStringExtra("Re");
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);
        name = hm.get(SessionManager.FULLNAME);
        email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }

        rere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ad=new MedAdapter(ShowResults.this,list,"Sh");
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
                    if(n.contains(re1)||n.equals(re1)||re1.contains(n)) {
                        if(re3<5)
                        {
                            ty[re3]=de.getDisease();
                            re3++;
                        }
                        list.add(de);
                    }
                }
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        agr=new GridAdapter(getApplicationContext(),list);
        gr.setAdapter(agr);
        for(int i=0;i<re3;i++)
        {

            FirebaseDatabase.getInstance().getReference("Medicines").child(ty[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot s: snapshot.getChildren())
                    {
                        Med de=s.getValue(Med.class);
                        list.add(de);
                    }
                    agr.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),ShowProduct.class).putExtra("Url",list.get(i).getURL()
                ).putExtra("Name",list.get(i).getName()).putExtra("Mname",list.get(i).getMname()).
                        putExtra("Qua",list.get(i).getQua()).putExtra("Des",list.get(i).getDes()).
                        putExtra("Dis",list.get(i).getDisease()).putExtra("Ran",list.get(i).getRan()).putExtra("Price",list.get(i).getPrice()));
            }
        });

    }
}