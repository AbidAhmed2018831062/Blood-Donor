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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_Section extends AppCompatActivity {
    RecyclerView rco,rwo,rde,rho,rnu,rvi,rhe,rca,rdi,rpn,rme,rot,tip;
    TextView co,wo,de,ho,nu,vi,he,ca,dia,pn,me,ot;
    EditText se;
    ImageView search;
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
    int img[]={R.drawable.covid19,R.drawable.women,R.drawable.device,R.drawable.homeo,R.drawable.nut,R.drawable.vita,R.drawable.he
    ,R.drawable.can,R.drawable.dia,R.drawable.pne,R.drawable.ment,R.drawable.ment,R.drawable.oth};

    TipAdapter ta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_section);
        co=(TextView) findViewById(R.id.co);
        se=(EditText) findViewById(R.id.se);
        search=(ImageView) findViewById(R.id.search);
        add=(Button) findViewById(R.id.add);
        String na[]=getResources().getStringArray(R.array.What);
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
        tip=(RecyclerView) findViewById(R.id.tip);
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
        ta=new TipAdapter(getApplicationContext(),img,na);
        tip.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        tip.setAdapter(ta);

        FirebaseDatabase.getInstance().getReference("Medicines").child(co.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(wo.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(de.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(ho.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(nu.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(vi.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(he.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(ca.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(dia.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(pn.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(me.getText().toString()).addValueEventListener(new ValueEventListener() {
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
        FirebaseDatabase.getInstance().getReference("Medicines").child(ot.getText().toString()).addValueEventListener(new ValueEventListener() {
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
search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),ShowResults.class).putExtra("Re",se.getText().toString()));
    }
});

    }
}