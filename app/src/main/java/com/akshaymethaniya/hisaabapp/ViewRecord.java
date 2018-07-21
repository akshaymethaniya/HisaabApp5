package com.akshaymethaniya.hisaabapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRecord extends AppCompatActivity {
    ListView listView;
    ArrayList<Hissab_entry> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=(ListView)findViewById(R.id.listView);
        arrayList=new ArrayList<Hissab_entry>();

        ItemListAdapter adapter=new ItemListAdapter(this,R.layout.adapter_view_layout,arrayList);
        listView.setAdapter(adapter);
        /*adapter=new ArrayAdapter<String>(ViewRecord.this,R.layout.adapter_view_layout,arrayList);

        listView.setAdapter(adapter);

        */
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listview,View v,int position,long id)
            {
                openActivity(position,v);
            }
        };
        listView.setOnItemClickListener(itemClickListener);
        viewEntries();
        adapter.notifyDataSetChanged();
    }
    public void addEntryInList(Hissab_entry he)
    {
        arrayList.add(he);
    }
    public void viewEntries()
    {
        HisaabDatabaseHandler handler=null;
        SQLiteDatabase db=null;
        Cursor cursor=null;

        try {
            handler = new HisaabDatabaseHandler(this);
            db= handler.getReadableDatabase();
            String[] str = new String[6];
            str[0] = "NAME";
            str[1] = "DESCRIPTION";
            str[2] = "TYPE";
            str[3] = "VALUE";
            str[4] = "DATE";
            str[5] ="STATUS";
            cursor = db.query("HISAAB_RECORD", str, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    String desc = cursor.getString(1);
                    int type = cursor.getInt(2);
                    int value = cursor.getInt(3);
                    String date = cursor.getString(4);
                    int status=cursor.getInt(5);

                    Log.d("Message:", name + " : " + " : " + value + " : " + type + " : " + date);
                    String s = name + " " + " " + value + " " + type;
                    addEntryInList(new Hissab_entry(desc, name, type, value, date,status));

                } while (cursor.moveToNext());
            }

        }
        catch(SQLiteException e)
        {
            Toast toast=Toast.makeText(ViewRecord.this,"Database Unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        finally {
            if(cursor!=null)
                cursor.close();
            if(db!=null)
                db.close();
            if(handler!=null)
                handler.close();
        }

    }
    public void openActivity(int position,View view)
    {
        Hissab_entry he=arrayList.get(position);

        String name=he.getName();
        String desc=he.getDesc();
        int type=he.getType();
        int amount=he.getAmount();
        String date=he.getDate();
        int status=he.getStatus();

        /*TextView nameTextView=(TextView) view.findViewById(R.id.textViewName);
        String name=nameTextView.getText().toString();*/

        Intent intent=new Intent(this,UpdateActivity.class);

        intent.putExtra("name",name);
        intent.putExtra("desc",desc);
        intent.putExtra("type",type);
        intent.putExtra("amount",amount);
        intent.putExtra("date",date);
        intent.putExtra("status",status);
        startActivity(intent);
        finish();
    }
}
