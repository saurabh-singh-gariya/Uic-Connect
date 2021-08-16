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

public class CuOtherAdapter extends RecyclerView.Adapter<CuOtherAdapter.ViewHolder> implements Filterable {

    LayoutInflater inflater;
    List<Contact> contactList;
    List<Contact> backup;
    Context mContext;

    public CuOtherAdapter(Context context, List<Contact> contactList) {
        this.inflater = LayoutInflater.from(context);
        this.contactList = contactList;
        this.mContext = context;
        backup=new ArrayList<>(contactList);
    }

    @NonNull
    @Override
    public CuOtherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.allcontact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuOtherAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactName .setText(contact.getName());
        holder.contactSpecialName.setText(contact.getSpecialName());
        holder.contactPhone.setText(contact.getPhone());
        holder.contactEmail.setText(contact.getEmail());

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
        TextView contactName, contactPhone, contactEmail, contactSpecialName;
        Button callBtn, emailBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.all_contact_name);
            contactSpecialName = itemView.findViewById(R.id.all_contact_special_name);
            contactEmail = itemView.findViewById(R.id.all_contact_email);
            contactPhone = itemView.findViewById(R.id.all_contact_phone);
            callBtn = itemView.findViewById(R.id.all_contact_btn_call);
            emailBtn = itemView.findViewById(R.id.all_contact_btn_email);
        }
    }
}
