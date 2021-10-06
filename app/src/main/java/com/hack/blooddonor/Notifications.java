package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Notifications extends AppCompatActivity {
RecyclerView noti;
NotiAdapter adapter;
String email;
ImageView back;
List<NotiData> list=new ArrayList<>();
List<NotiData> list1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        noti=(RecyclerView)findViewById(R.id.noti);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
noti.setLayoutManager(new LinearLayoutManager(this));
adapter=new NotiAdapter(this,list1);
noti.setAdapter(adapter);
        final SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
         email=hm.get(SessionManager.EMAIL);
        String email1="";
        for(int i=0;i<email.length();i++)
        {
            if(email.charAt(i)=='@')
                break;
            email1+=email.charAt(i);
        }
        String finalEmail = email1;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                 for(DataSnapshot s:snapshot.getChildren())
                 {
                     NotiData pd=s.getValue(NotiData.class);
                     list.add(pd);
                 }
                for(int i=list.size()-1;i>=0;i--)
                    list1.add(list.get(i));

                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}