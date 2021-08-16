package com.example.uicaddressbook.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.uicaddressbook.AddEdit.EditFaculty;
import com.example.uicaddressbook.ContactDetailsAdmin;
import com.example.uicaddressbook.ContactsDetails;
import com.example.uicaddressbook.Helper.URLs;
import com.example.uicaddressbook.Helper.VolleySingleton;
import com.example.uicaddressbook.Model.Contact;
import com.example.uicnotifier.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminProfContacts extends RecyclerView.Adapter<AdminProfContacts.ViewHolder> implements Filterable {
    LayoutInflater inflater;
    List<Contact> contactList;
    List<Contact> backup;
    Context mContext;
    ContactsDetails contactsDetails;
    ProgressDialog progressDialog;

    public AdminProfContacts(Context context, List<Contact> contacts){
        this.inflater = LayoutInflater.from(context);
        this.contactList = contacts;
        this.mContext = context;
        backup=new ArrayList<>(contacts);
    }

    @NonNull
    @Override
    public AdminProfContacts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.allcontact_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProfContacts.ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        holder.specialName.setVisibility(View.GONE);
        holder.contactName.setText(contact.getName());
        holder.contactPhone.setText(contact.getPhone());
        holder.contactEmail.setText(contact.getEmail());

        holder.emailBtn.setText(R.string.edit);
        holder.callBtn.setText(R.string.delete);

        holder.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditFaculty.class);
                intent.putExtra("currentContact", contact);
                mContext.startActivity(intent);
            }
        });

        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("Deleting....");
                progressDialog.show();
                deleteProfessor(contact);
            }
        });
    }

    private void deleteProfessor(Contact contact) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DEL_PROF,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("message").equalsIgnoreCase("Contact Deleted!!")) {
                            progressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, ContactDetailsAdmin.class);
                            intent.putExtra("contactType", 1);
                            mContext.startActivity(intent);
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(mContext, "Something Wrong!!!", Toast.LENGTH_SHORT).show())
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(contact.getId()));
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
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
        Button callBtn, emailBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            callBtn = itemView.findViewById(R.id.all_contact_btn_call);
            emailBtn = itemView.findViewById(R.id.all_contact_btn_email);
            contactName = itemView.findViewById(R.id.all_contact_name);
            specialName = itemView.findViewById(R.id.all_contact_special_name);
            contactPhone = itemView.findViewById(R.id.all_contact_phone);
            contactEmail = itemView.findViewById(R.id.all_contact_email);
        }

    }
}
