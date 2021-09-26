package com.hack.blooddonor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MedAdapter  extends RecyclerView.Adapter<MedAdapter.MED> {
    List<Med> list;
    Context c;
    MedAdapter(Context c,List<Med>list)
    {
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public MedAdapter.MED onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_med_adapter, parent, false);
        return new MED(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedAdapter.MED holder, int i) {
        Toast.makeText(c,list.size()+"",Toast.LENGTH_LONG).show();
        Picasso.with(c).load(list.get(i).getURL()).fit().centerCrop().into(holder.ph);
        holder.name.setText(list.get(i).getMname());
        holder.price.setText(list.get(i).getPrice()+"Tk.");

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MED extends RecyclerView.ViewHolder {
        CircleImageView ph;
        TextView name,price;
        public MED(@NonNull View v) {
            super(v);
            ph=v.findViewById(R.id.ph);
            name=v.findViewById(R.id.name);
            price=v.findViewById(R.id.price);
        }
    }
}
