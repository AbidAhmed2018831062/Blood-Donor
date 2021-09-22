package com.hack.blooddonor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsandWatch extends AppCompatActivity {
TextView po;
RecyclerView posts;
CircleImageView profile_image;
String url;
List<PostingData> list=new ArrayList<>();
PostsAdapter post;
    String email,dis,div;
    Button yes, no;
    LinearLayout ask;
    BottomNavigationView bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_postsand_watch);
        po=(TextView)findViewById(R.id.po);
        posts=(RecyclerView)findViewById(R.id.posts);
        yes=(Button)findViewById(R.id.yes);
        no=(Button)findViewById(R.id.no);
        ask=(LinearLayout)findViewById(R.id.ask);
        final SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        url=hm.get(SessionManager.URL);
        String donor=hm.get(SessionManager.DONOR);
       //Toast.makeText(getApplicationContext(), donor+"Abid", Toast.LENGTH_LONG).show();
         dis=hm.get(SessionManager.DISTRICT);
         div=hm.get(SessionManager.DIVISION);
        bm = (BottomNavigationView) findViewById(R.id.bottomnav);
        bm.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.s) {
                    startActivity(new Intent(PostsandWatch.this, PostsandWatch.class));
                }  else if (item.getItemId() == R.id.donors) {
                    startActivity(new Intent(PostsandWatch.this, ShowDonors.class));

                }
                else if(item.getItemId()==R.id.noti)
                {
                    startActivity(new Intent(PostsandWatch.this, Notifications.class));
                }
                else if(item.getItemId()==R.id.profile)
                {
                  //  startActivity(new Intent(UserPrfofilew.this, WatchLater.class));

                }
            }
        });
        if(donor==null||donor.equals("No"))
            ask.setVisibility(View.VISIBLE);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ask.setVisibility(View.GONE);
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DonorReg.class));
            }
        });
        profile_image=(CircleImageView) findViewById(R.id.profile_image);

       po.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                         startActivity(new Intent(getApplicationContext(),Posting.class).putExtra("Work","No"));
                         finish();
           }
       });
        Picasso.with(PostsandWatch.this).load(url).fit().centerCrop().into(profile_image);
        posts.setLayoutManager(new LinearLayoutManager(this));
       post = new PostsAdapter(this, list);
        posts.setAdapter(post);
         email = hm.get(SessionManager.EMAIL);
        String email1 = "";
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@')
                break;
            email1 += email.charAt(i);
        }
        Calendar cal = Calendar.getInstance();
        int cday = cal.get(Calendar.DAY_OF_MONTH);
        int cmonth = cal.get(Calendar.MONTH);
        int cy = cal.get(Calendar.YEAR);
        String month = "";
        if (cmonth == 1 - 1)
            month = "January";
        else if (cmonth == 2 - 1)
            month = "February";
        else if (cmonth == 3 - 1)
            month = "March";
        else if (cmonth == 4 - 1)
            month = "April";
        else if (cmonth == 5 - 1)
            month = "May";
        else if (cmonth == 6 - 1)
            month = "June";
        else if (cmonth == 7 - 1)
            month = "July";
        else if (cmonth == 8 - 1)
            month = "August";
        else if (cmonth == 9 - 1)
            month = "September";
        else if (cmonth == 10 - 1)
            month = "October";
        else if (cmonth == 11 - 1)
            month = "November";
        else month = "December";
        String finalMonth = month;
        FirebaseDatabase.getInstance().getReference("Users").child(email1).child("Posts").child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              ///  list.clear();
                for(DataSnapshot s:snapshot.getChildren())
                {
                   // Toast.makeText(getApplicationContext(), finalMonth,Toast.LENGTH_LONG).show();
                    PostingData pd=s.getValue(PostingData.class);
                    list.add(pd);
                }

              post.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("PostsDi").child(dis).child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren())
                {
              //      Toast.makeText(getApplicationContext(), "Abid",Toast.LENGTH_LONG).show();
                    PostingData pd=s.getValue(PostingData.class);
                    if(pd.getEmail().equals(email))
                        continue;
                    list.add(pd);
                }
                post.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference("PostsDi").child(div).child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren())
                {
                    PostingData pd=s.getValue(PostingData.class);
              //      Toast.makeText(getApplicationContext(), dis,Toast.LENGTH_LONG).show();


                    if(pd.getEmail().equals(email)||pd.getDistrict().equals(dis))
                        continue;
                    list.add(pd);
                }
                post.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}