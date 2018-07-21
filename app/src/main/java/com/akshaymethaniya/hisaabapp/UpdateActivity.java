package com.akshaymethaniya.hisaabapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.ViewGroup;

public class UpdateActivity extends AppCompatActivity {
    String name,desc,type,amount,date,status;
    EditText nameTextView,amountTextView, descTExtView;
    Button datePicker,hisaabClear;
    RadioButton toPayRadioButton,toTakeRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView=(EditText)findViewById(R.id.newName);
        amountTextView=(EditText) findViewById(R.id.newAmount);
        descTExtView=(EditText)findViewById(R.id.newDesc);
        toPayRadioButton=(RadioButton)findViewById(R.id.newToPay);
        toTakeRadioButton=(RadioButton)findViewById(R.id.newToTake);
        datePicker=(Button)findViewById(R.id.dateButton);
        hisaabClear=(Button)findViewById(R.id.hisaabClear);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.new_type_radio);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioGroup typeRadio=(RadioGroup)findViewById(R.id.new_type_radio);
                int id=typeRadio.getCheckedRadioButtonId();
                int type=0;
                switch (id)
                {
                    case R.id.newToPay:hisaabClear.setText("SET AS PAID");break;
                    case R.id.newToTake:hisaabClear.setText("SET AS TAKEN");break;
                }
            }
        });

        Intent intent=getIntent();

        name=intent.getExtras().get("name").toString();
        desc=intent.getExtras().get("desc").toString();
        type=intent.getExtras().get("type").toString();
       amount=intent.getExtras().get("amount").toString();
        date=intent.getExtras().get("date").toString();
        status=intent.getExtras().get("status").toString();

        date="DATE : "+date;
        nameTextView.setText(name);
        amountTextView.setText(amount);
        descTExtView.setText(desc);
        datePicker.setText(date);


        if(type.equals("0"))
        {
            toPayRadioButton.setChecked(true);
            hisaabClear.setText("SET AS PAID");
            toTakeRadioButton.setChecked(false);
        }
        else
        {
            hisaabClear.setText("SET AS TAKEN");
            toPayRadioButton.setChecked(false);
            toTakeRadioButton.setChecked(true);
        }

        ImageView imageView=(ImageView)findViewById(R.id.paidOrNot);
        if(status.equals("1"))
        {
            imageView.setBackgroundResource(R.drawable.ic_action_name);
            ViewGroup layout = (ViewGroup) hisaabClear.getParent();
             //for safety only  as you are doing onClick
            layout.removeView(hisaabClear);
            Button button=(Button)findViewById(R.id.button1);
            layout.removeView(button);
        }
        else{
            imageView.setBackgroundResource(R.drawable.ic_action_name_cross);

        }

    }
    public void onClickUpdateRec(View v)
    {
        String rowNo="-1";
        HisaabDatabaseHandler handler = new HisaabDatabaseHandler(this);
        SQLiteDatabase db = handler.getReadableDatabase();

        Cursor cursor=db.query("HISAAB_RECORD",new String[]{"_id"},"NAME = ? AND DESCRIPTION = ? AND VALUE = ? AND TYPE = ?",new String[]{name,desc,amount,type},null,null,null);
        if(cursor.moveToFirst())
        {
            rowNo=cursor.getString(0);
        }
        ContentValues cv=new ContentValues();
        cv.put("NAME",nameTextView.getText().toString());
        cv.put("DESCRIPTION",descTExtView.getText().toString());
        cv.put("VALUE",amountTextView.getText().toString());
        RadioGroup typeRadio=(RadioGroup)findViewById(R.id.new_type_radio);
        int id=typeRadio.getCheckedRadioButtonId();
        int type=0;
        switch (id)
        {
            case R.id.newToPay:type=0;break;
            case R.id.newToTake:type=1;break;
        }
        cv.put("TYPE",type);
        db.update("HISAAB_RECORD",cv,"_ID = ?",new String[]{rowNo});

        cursor.close();
        db.close();
        handler.close();

        Intent intent=new Intent(this,ViewRecord.class);
        startActivity(intent);
        finish();

        Toast toast=Toast.makeText(UpdateActivity.this,"Record Updated",Toast.LENGTH_SHORT);
        toast.show();

    }

    public void onClickRemoveRec(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure ? This Will Delete The Record ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String rowNo="-1";
                HisaabDatabaseHandler handler = new HisaabDatabaseHandler(UpdateActivity.this);
                SQLiteDatabase db = handler.getReadableDatabase();

                Cursor cursor=db.query("HISAAB_RECORD",new String[]{"_id"},"NAME = ? AND DESCRIPTION = ? AND VALUE = ? AND TYPE = ?",new String[]{name,desc,amount,type},null,null,null);
                if(cursor.moveToFirst())
                {
                    rowNo=cursor.getString(0);
                }
                db.delete("HISAAB_RECORD","_ID = ?",new String[]{rowNo});
                cursor.close();
                db.close();
                handler.close();
                openViewRecordAactivity();


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
    public void openViewRecordAactivity()
    {
        Toast toast=Toast.makeText(UpdateActivity.this,"Record Removed",Toast.LENGTH_SHORT);
        toast.show();
        Intent intent=new Intent(this,ViewRecord.class);
        startActivity(intent);
        finish();
    }
    public void onClickHisaabClear(View view){
        String rowNo="-1";
        HisaabDatabaseHandler handler = new HisaabDatabaseHandler(this);
        SQLiteDatabase db = handler.getReadableDatabase();

        Cursor cursor=db.query("HISAAB_RECORD",new String[]{"_id"},"NAME = ? AND DESCRIPTION = ? AND VALUE = ? AND TYPE = ?",new String[]{name,desc,amount,type},null,null,null);
        if(cursor.moveToFirst())
        {
            rowNo=cursor.getString(0);
        }
        ContentValues cv=new ContentValues();
        cv.put("NAME",nameTextView.getText().toString());
        cv.put("DESCRIPTION",descTExtView.getText().toString());
        cv.put("VALUE",amountTextView.getText().toString());
        cv.put("STATUS","1");
        RadioGroup typeRadio=(RadioGroup)findViewById(R.id.new_type_radio);
        int id=typeRadio.getCheckedRadioButtonId();
        int type=0;
        switch (id)
        {
            case R.id.newToPay:type=0;break;
            case R.id.newToTake:type=1;break;
        }
        cv.put("TYPE",type);
        db.update("HISAAB_RECORD",cv,"_ID = ?",new String[]{rowNo});

        cursor.close();
        db.close();
        handler.close();

        Intent intent=new Intent(this,ViewRecord.class);
        startActivity(intent);
        finish();

        Toast toast=Toast.makeText(UpdateActivity.this,"Record Updated",Toast.LENGTH_SHORT);
        toast.show();

    }
}
