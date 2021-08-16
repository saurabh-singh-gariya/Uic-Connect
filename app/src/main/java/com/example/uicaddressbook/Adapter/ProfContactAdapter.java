package com.example.uicaddressbook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uicaddressbook.Model.Contact;
import com.example.uicnotifier.R;

import java.util.ArrayList;
import java.util.List;

public class ProfContactAdapter extends RecyclerView.Adapter<ProfContactAdapter.ViewHolder> implements Filterable {

    LayoutInflater inflater;
    List<Contact> contactList;
    List<Contact> backup;
    Context mContext;
    public static  List<Contact> favorite=new ArrayList<>();
    public static int favourite_counter=0;


    public ProfContactAdapter(Context context, List<Contact> contacts){
        this.inflater = LayoutInflater.from(context);
        this.contactList = contacts;
        this.mContext = context;
        backup=new ArrayList<>(contacts);
    }

    @NonNull
    @Override
    public ProfContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.allcontact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfContactAdapter.ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        //holder.specialName.setVisibility(View.GONE);
        holder.contactName.setText(contact.getName());
        holder.contactPhone.setText(contact.getPhone());
        holder.contactEmail.setText(contact.getEmail());
        holder.specialName.setText(contact.getSpecialName());

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contact.getPhone().trim()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{contact.getEmail()});
                try {
                    mContext.startActivity(Intent.createChooser(intent,"Choose a App"));
                }catch (Exception e)
                {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        holder.favorite_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                favourite_counter++;
//                if(favourite_counter%2==0)
//                {
//                    holder.favorite_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
//                    Toast.makeText(mContext, "Removed from favourites", Toast.LENGTH_SHORT).show();
//                    favorite.remove(contactList.get(position));
//                }
//                else {
//                    // favorite.clear();
//                    holder.favorite_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//                    favorite.add(contactList.get(position));
//                    Toast.makeText(mContext, "Added to favorites", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Contact> filterData=new ArrayList<>();
            if(keyword.toString().isEmpty())
            {
                filterData.addAll(backup);
            }
            else
            {
                for(Contact obj:backup)
                {
                    if(obj.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                    {
                        filterData.add(obj);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((ArrayList<Contact>)results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView contactName, contactPhone, contactEmail, specialName;
        Button callBtn, emailBtn,favorite_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            callBtn = itemView.findViewById(R.id.all_contact_btn_call);
            emailBtn = itemView.findViewById(R.id.all_contact_btn_email);
            contactName = itemView.findViewById(R.id.all_contact_name);
            specialName = itemView.findViewById(R.id.all_contact_special_name);
            contactPhone = itemView.findViewById(R.id.all_contact_phone);
            contactEmail = itemView.findViewById(R.id.all_contact_email);
            //favorite_btn=itemView.findViewById(R.id.favt_btn);
        }

    }
}
