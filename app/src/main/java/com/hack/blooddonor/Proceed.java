package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Proceed extends AppCompatActivity {
TextView total,order,deli;
RecyclerView scart;
List<CartData> list=new ArrayList<>();
ProceedAdapter pa;
CircleImageView profile_image;
TextView profile_name,location;
Button pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_proceed);
        total=(TextView) findViewById(R.id.total);
        location=(TextView) findViewById(R.id.location);
        pay=(Button) findViewById(R.id.pay);
        profile_name=(TextView) findViewById(R.id.profile_name);
        profile_image=(CircleImageView) findViewById(R.id.profile_image);
        SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String url = hm.get(SessionManager.URL);
        String nam = hm.get(SessionManager.FULLNAME);
        Picasso.with(getApplicationContext()).load(url).fit().centerCrop().into(profile_image);
        profile_name.setText(nam);
        location.setText(getIntent().getStringExtra("Location"));
        String email1="";
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        deli=(TextView) findViewById(R.id.deli);
        order=(TextView) findViewById(R.id.order);
        scart=(RecyclerView) findViewById(R.id.scart);
        pa=new ProceedAdapter(Proceed.this,list);
        scart.setLayoutManager(new LinearLayoutManager(this));
        scart.setAdapter(pa);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Users").child(email1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              for(DataSnapshot ds: snapshot.getChildren())
              {
                  CartData cs=ds.getValue(CartData.class);
                  list.add(cs);
              }
              pa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}