package com.hack.blooddonor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProceedAdapter extends RecyclerView.Adapter<ProceedAdapter.Pro> {
    List<CartData> list;
    Context c;
    ProceedAdapter(Context c,List<CartData>list)
    {
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public ProceedAdapter.Pro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showcart, parent, false);
        return new Pro(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProceedAdapter.Pro holder, @SuppressLint("RecyclerView") int i) {
        SessionManager sh = new SessionManager(c, SessionManager.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        String email = hm.get(SessionManager.EMAIL);
        String email1="";
        for(int u=0;u<email.length();u++)
        {
            if(email.charAt(u)=='@')
                break;
            email1+=email.charAt(u);
        }
        Picasso.with(c).load(list.get(i).getUrl()).fit().centerCrop().into(holder.mpic);
        holder.mname.setText(list.get(i).getMname());
        holder.count.setText(list.get(i).getCount());
        long p=Integer.parseInt(list.get(i).getPrice())*Integer.parseInt(list.get(i).getCount());
        holder.total.setText(list.get(i).getPrice()+"* "+list.get(i).getCount()+" = "+list.get(i).getTotal()+"Tk.");
        String finalEmail = email1;
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c.getApplicationContext(), finalEmail,Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("CartCount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()&&list.size()!=0) {
                            String uy = snapshot.child("count").getValue().toString();
                            long r = Integer.parseInt(uy) - Integer.parseInt(list.get(i).getCount());
                            HashMap ty = new HashMap();
                            ty.put("count", r + "");
                            Toast.makeText(c.getApplicationContext(), r+""+uy,Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("CartCount").updateChildren(ty);
                            FirebaseDatabase.getInstance().getReference("Users").child(finalEmail).child("Cart").child(list.get(i).getRan()).removeValue();
                            list.remove(i);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Pro extends RecyclerView.ViewHolder {
        CircleImageView mpic;
        TextView mname,count,total;
        ImageView del;
        public Pro(@NonNull View v) {
            super(v);
            mpic=v.findViewById(R.id.mpic);
            mname=v.findViewById(R.id.mname);
            del=v.findViewById(R.id.del);
            count=v.findViewById(R.id.count);
            total=v.findViewById(R.id.total);
        }
    }
}
