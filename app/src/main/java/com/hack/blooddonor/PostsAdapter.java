package com.hack.blooddonor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Posts> {
    Context c;
    List<PostingData> list;
    public PostsAdapter(Context c, List<PostingData> pd) {
        this.c=c;
    this.list=pd;
    }

    @NonNull
    @Override
    public PostsAdapter.Posts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posts, parent, false);
        return new Posts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.Posts holder, int i) {
        SessionManager sh = new SessionManager(c, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        Picasso.with(c).load(list.get(i).getUrl()).fit().centerCrop().into(holder.profile_image);
        holder.profile_name.setText(list.get(i).getName());
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c,Comment.class).putExtra("Date",list.get(i).getDate()).
                        putExtra("Gh",list.get(i).getGh()).putExtra("Email",list.get(i).getEmail()).
                        putExtra("Dis",list.get(i).getDistrict()).putExtra("Div",list.get(i).getDivision())
                .putExtra("Blood",list.get(i).getBlood()).putExtra("Patient",list.get(i).getPatientName())
                        .putExtra("Pat",list.get(i).getPhone()).putExtra("Location",list.get(i).getLocation())
                   .putExtra("Url",list.get(i).getUrl())   .putExtra("Disease",list.get(i).getDisease()));
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse("tel:"+list.get(i).getPhone()));
                c.startActivity(in);
            }
        });
        holder.date.setText(list.get(i).getDate());
        holder.blood.setText(list.get(i).getBlood()+" blood is needed at "+list.get(i).getLocation()+", "+list.get(i).getDistrict()+", "+list.get(i).getDivision());
        holder.patient.setText("Patient name is "+list.get(i).getPatientName()+". The blood is needed for "+list.get(i).getDisease());
        holder.contact.setText("Contact Number: "+list.get(i).getPhone());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Posts extends RecyclerView.ViewHolder {
TextView profile_name,date,blood,contact,patient;
CircleImageView profile_image;
Button submit,call;
        public Posts(@NonNull View i) {
            super(i);
            profile_name=i.findViewById(R.id.profile_name);
            date=i.findViewById(R.id.date);
            blood=i.findViewById(R.id.blood);
            contact=i.findViewById(R.id.contact);
            patient=i.findViewById(R.id.patient);
            profile_image=i.findViewById(R.id.profile_image);
            submit=i.findViewById(R.id.chat);
            call=i.findViewById(R.id.Call);

        }
    }
}
