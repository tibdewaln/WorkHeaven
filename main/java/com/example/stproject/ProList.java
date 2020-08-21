package com.example.stproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ProList extends ArrayAdapter<ProInfo> {

    private Activity context;
    private List<ProInfo> prolist;

    public ProList(Activity context, List<ProInfo> prolist)
    {
        super(context, R.layout.list_layout, prolist);
        this.context = context;
        this.prolist = prolist;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listitem = inflater.inflate(R.layout.list_layout, null, true);
        TextView name = listitem.findViewById(R.id.name);
        TextView address = listitem.findViewById(R.id.address);
        TextView email = listitem.findViewById(R.id.email);
        TextView service = listitem.findViewById(R.id.services);
        TextView des = listitem.findViewById(R.id.descript);
        ProInfo info = prolist.get(position);
        name.setText("Name: " + info.getName());
        address.setText("Address: " + info.getAddress());
        email.setText("Contact Information:"+info.getEmail() +", "+info.getContact());
        service.setText("Profession: "+info.getService());
        des.setText("Description : " + info.getDes());
        return listitem;
    }
}
