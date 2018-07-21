package com.akshaymethaniya.hisaabapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FOR ORACLE on 04-05-2018.
 */
public class ItemListAdapter extends ArrayAdapter<Hissab_entry> {
    private static final String TAG="ItemListAdapter";
    private Context mContext;
    int mResource;

    /**
     *Default Constructor
     * @param context
     * @param resource
     * @param objects
     */
    public ItemListAdapter(Context context, int resource, ArrayList<Hissab_entry> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name=getItem(position).getName();
        int value=getItem(position).getAmount();
        int type=getItem(position).getType();
        String desc=getItem(position).getDesc();
        String date=getItem(position).getDate();
        int status=getItem(position).getStatus();

        Hissab_entry he=new Hissab_entry(desc,name,value,type,date,status);

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mResource,parent,false);

        TextView textViewName=(TextView)convertView.findViewById(R.id.textViewName);
        TextView textViewToPay=(TextView)convertView.findViewById(R.id.textViewToPay);
        TextView textViewToTake=(TextView)convertView.findViewById(R.id.textViewToTake);
        TextView textViewStatus=(TextView)convertView.findViewById(R.id.status);
        TextView textViewDate=(TextView)convertView.findViewById(R.id.date);
        //TextView textViewStatus=(TextView)convertView.findViewById(R.id.Status);
        //Button b= (Button)convertView.findViewById(R.id.Status) ;
        //b.setBackgroundResource(R.drawable.ic_action_name);
        textViewDate.setText(date.substring(0,date.length()-6));
        textViewName.setText(name);
        if(type==1)
        {
            textViewToTake.setText(value+"");
            textViewToPay.setText("0");
        }
        else
        {
            textViewToPay.setText(value+"");
            textViewToTake.setText("0");
        }
        if(status==1)
        {
            textViewStatus.setText("Clear");
        }

        //textViewStatus.setText(status+"");
        return convertView;
    }
}
