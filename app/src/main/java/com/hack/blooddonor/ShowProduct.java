package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ShowProduct extends AppCompatActivity {
String Mname,Url,Name,Qua,Des,Ran,Dis,Price;
GridView gr;
String email,email1="",url,name;
GridAdapter agr;
TextView bname,des,mname,pr,price,c,count;
    ImageView mpic;
    Button dec,inc,buy;
    RelativeLayout scart;
List<Med> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_product);
        Mname=getIntent().getStringExtra("Mname");
        Qua=getIntent().getStringExtra("Qua");
        Des=getIntent().getStringExtra("Des");
        Ran=getIntent().getStringExtra("Ran");
        Dis=getIntent().getStringExtra("Dis");
        Price=getIntent().getStringExtra("Price");
       gr=(GridView)findViewById(R.id.grid);
       mpic=(ImageView) findViewById(R.id.mpic);
        Picasso.with(getApplicationContext()).load(Url).fit().centerCrop().into(mpic);
       mname=(TextView) findViewById(R.id.mname);
       c=(TextView) findViewById(R.id.c);
       dec=(Button) findViewById(R.id.dec);
       inc=(Button) findViewById(R.id.inc);
       buy=(Button) findViewById(R.id.buy);
       dec.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               int yu=Integer.parseInt(pr.getText().toString());
               yu--;
               pr.setText(yu+"");
           }
       });
        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yu=Integer.parseInt(pr.getText().toString());
                yu++;
                pr.setText(yu+"");
            }
        });
        scart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),TakeAddress.class));
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sh = new SessionManager(getApplicationContext(), SessionManager.USERSESSION);
                HashMap<String, String> hm = sh.returnData();
                email = hm.get(SessionManager.EMAIL);
                name = hm.get(SessionManager.FULLNAME);
                url = hm.get(SessionManager.URL);
                long p=Integer.parseInt(count.getText().toString())+Integer.parseInt(pr.getText().toString());
                email1 = "";
                HashMap mp=new HashMap();
                mp.put("count",p);
                FirebaseDatabase.getInstance().getReference("Users").child(email1).child("CartCount").updateChildren(mp);
                for (int i = 0; i < email.length(); i++) {
                    if (email.charAt(i) == '@')
                        break;
                    email1 += email.charAt(i);
                }

                count.setText(p+"");
                Random rn=new Random();
                long op=rn.nextInt(10000000);


                FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Cart").child(Ran).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            String ui=snapshot.child("count").getValue().toString();
                            long p1=Integer.parseInt(ui);
                            p1+=Integer.parseInt(pr.getText().toString());
                            long total= p1*Integer.parseInt(Price);
                            CartData cd=new CartData(Mname,Price,p1+"",Name,Ran,total+"",Url);

                            FirebaseDatabase.getInstance().getReference("Users").child(email1).child(email1).child(Ran).setValue(cd);
                        }
                        else
                        {
                            long total=Integer.parseInt(pr.getText().toString())*Integer.parseInt(Price);
                            CartData cd=new CartData(Mname,Price,pr.getText().toString(),Name,Ran,total+"",Url);

                            FirebaseDatabase.getInstance().getReference("Users").child(email1).child(email1).child(Ran).setValue(cd);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
       count=(TextView) findViewById(R.id.count);
       scart=(RelativeLayout) findViewById(R.id.scart);
       mname.setText(Mname);
       bname=(TextView) findViewById(R.id.bname);
       bname.setText(Name);
       pr=(TextView) findViewById(R.id.pr);
       price=(TextView) findViewById(R.id.price);
       des=(TextView) findViewById(R.id.des);
       des.setText(Des);
       price.setText("The price of "+Qua+" is: "+Price);
        agr=new GridAdapter(getApplicationContext(),list);
        gr.setAdapter(agr);
        FirebaseDatabase.getInstance().getReference("Medicines").child(Dis).addValueEventListener(new ValueEventListener() {
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