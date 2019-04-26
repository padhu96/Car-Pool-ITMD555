package com.example.mypackage.carpool;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DriverList extends ArrayAdapter<ProfileInformation> {

    private Activity context;
    private List<ProfileInformation> driverList;

    public  DriverList(Activity context, List<ProfileInformation> driverList) {
        super(context, R.layout.list_layout, driverList);
        this.driverList = driverList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewDest = listViewItem.findViewById(R.id.textViewDestination);
        TextView textViewContact = listViewItem.findViewById(R.id.textViewContact);
        TextView textviewTime = listViewItem.findViewById(R.id.textViewTime);
        TextView textviewAddress = listViewItem.findViewById(R.id.textViewAddress);

        ProfileInformation driver = driverList.get(position);

        textViewName.setText(driver.gettheName());
        textViewContact.setText(driver.getthePhone_no());
        textViewDest.setText(driver.gettheDestination());
        textviewTime.setText(driver.gettheTime());
        textviewAddress.setText(driver.gettheAddress());

        return listViewItem;
    }
}
