package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OwnerProfile extends AppCompatActivity {
RecyclerView rco,rwo,rde,rho,rnu,rvi,rhe,rca,rdi,rpn,rme,rot;
TextView co,wo,de,ho,nu,vi,he,ca,dia,pn,me,ot;
Button add;
List<Med> list=new ArrayList<>();
List<Med> list1=new ArrayList<>();
List<Med> list2=new ArrayList<>();
List<Med> list3=new ArrayList<>();
List<Med> list4=new ArrayList<>();
List<Med> list5=new ArrayList<>();
List<Med> list6=new ArrayList<>();
List<Med> list7=new ArrayList<>();
List<Med> list8=new ArrayList<>();
List<Med> list9=new ArrayList<>();
List<Med> list10=new ArrayList<>();
List<Med> list11=new ArrayList<>();
List<Med> list12=new ArrayList<>();
List<Med> list13=new ArrayList<>();
List<Med> list14=new ArrayList<>();
List<Med> list15=new ArrayList<>();
MedAdapter m;
String email,email1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        co=(TextView) findViewById(R.id.co);
        add=(Button) findViewById(R.id.add);
        wo=(TextView) findViewById(R.id.wo);
        de=(TextView) findViewById(R.id.de);
        ho=(TextView) findViewById(R.id.ho);
        nu=(TextView) findViewById(R.id.nu);
        ot=(TextView) findViewById(R.id.ot);
        pn=(TextView) findViewById(R.id.pn);
        he=(TextView) findViewById(R.id.he);
        ca=(TextView) findViewById(R.id.ca);
        vi=(TextView) findViewById(R.id.vi);
        me=(TextView) findViewById(R.id.me);
        dia=(TextView) findViewById(R.id.dia);
        rco=(RecyclerView) findViewById(R.id.rco);
        rhe=(RecyclerView) findViewById(R.id.rhe);
        rwo=(RecyclerView) findViewById(R.id.rwo);
        rde=(RecyclerView) findViewById(R.id.rde);
        rme=(RecyclerView) findViewById(R.id.rme);
        rho=(RecyclerView) findViewById(R.id.rho);
        rpn=(RecyclerView) findViewById(R.id.rpn);
        rnu=(RecyclerView) findViewById(R.id.rnu);
        rot=(RecyclerView) findViewById(R.id.rot);
        rca=(RecyclerView) findViewById(R.id.rca);
        rvi=(RecyclerView) findViewById(R.id.rvi);
        rdi=(RecyclerView) findViewById(R.id.rdi);
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),AddMedicine.class).putExtra("Work","Profile"));
    }
});
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        email = hm.get(SessionManager.EMAIL);

        for(int i=0;i<email.length();i++)
        {
            if(email.charAt(i)=='@')
                break;
            email1+=email.charAt(i);

        }
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(co.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list.add(me);
                    }
                    rco.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list);
                    rco.setAdapter(m);
                }
                else
                {
                    co.setVisibility(View.GONE);
                    rco.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(wo.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list1.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list1.add(me);
                    }
                    rwo.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list1);
                    rwo.setAdapter(m);
                }
                else
                {
                    rwo.setVisibility(View.GONE);
                    wo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(de.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list2.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list2.add(me);
                    }
                    rde.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list2);
                    rde.setAdapter(m);
                }
                else
                {
                    rde.setVisibility(View.GONE);
                    de.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(ho.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list3.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list3.add(me);
                    }
                    rho.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list2);
                    rho.setAdapter(m);
                }
                else
                {
                    ho.setVisibility(View.GONE);
                    rho.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(nu.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list4.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list4.add(me);
                    }
                    rnu.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list4);
                    rnu.setAdapter(m);
                }
                else
                {
                    nu.setVisibility(View.GONE);
                    rnu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(vi.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list5.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list5.add(me);
                    }
                    rvi.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list5);
                    rvi.setAdapter(m);
                }
                else
                {
                    vi.setVisibility(View.GONE);
                    rvi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(he.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list6.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list6.add(me);
                    }
                    rhe.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list6);
                    rhe.setAdapter(m);
                }
                else
                {
                    he.setVisibility(View.GONE);
                    rhe.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(ca.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list7.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list7.add(me);
                    }
                    rca.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list7);
                    rca.setAdapter(m);
                }
                else
                {
                    ca.setVisibility(View.GONE);
                    rca.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(dia.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list8.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list8.add(me);
                    }
                    rdi.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list8);
                    rdi.setAdapter(m);
                }
                else
                {
                    dia.setVisibility(View.GONE);
                    rdi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(pn.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list9.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list9.add(me);
                    }
                    rpn.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list9);
                    rpn.setAdapter(m);
                }
                else
                {
                    pn.setVisibility(View.GONE);
                    rpn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(me.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list10.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list10.add(me);
                    }
                    rme.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list10);
                    rme.setAdapter(m);
                }
                else
                {
                    me.setVisibility(View.GONE);
                    rme.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("MedicineOwners").child(email1).child("Medicines").child(ot.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    list11.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Med me = ds.getValue(Med.class);
                        list11.add(me);
                    }
                    rot.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    m=new MedAdapter(getApplicationContext(),list11);
                    rot.setAdapter(m);
                }
                else
                {
                    ot.setVisibility(View.GONE);
                    rot.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}