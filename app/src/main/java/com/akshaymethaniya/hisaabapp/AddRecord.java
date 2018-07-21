package com.akshaymethaniya.hisaabapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;


public class AddRecord extends AppCompatActivity {
    private Boolean dateSet=false;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button mDisplayDate;
    private int y,d,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayDate=(Button) findViewById(R.id.datePicker);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddRecord.this, android.R.style.Theme_DeviceDefault_Dialog, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y=year;
                m=monthOfYear;
                d=dayOfMonth;
                mDisplayDate.setText(d+" / "+m+" / "+y);
                dateSet=true;
                Log.d("ADD ACTIVITY", "onDAteSet : " + year + " : " + monthOfYear + " : " + dayOfMonth);
            }
        };
    }

    public void onClickAddRec(View view){
        if(dateSet) {
            EditText nameEditText = (EditText) findViewById(R.id.name);
            EditText descEditText = (EditText) findViewById(R.id.desc);
            EditText amountEditText = (EditText) findViewById(R.id.amount);
            RadioGroup typeRadio = (RadioGroup) findViewById(R.id.type_radio);
            Button datePicker=(Button)findViewById(R.id.datePicker);
            String dateText= d+" / "+m+" / "+y;

            int id = typeRadio.getCheckedRadioButtonId();
            int type = 0;
            int status=0;//Not Paid
            switch (id) {
                case R.id.toPay:
                    type = 0;
                    break;
                case R.id.toTake:
                    type = 1;
                    break;
            }
            HisaabDatabaseHandler handler = new HisaabDatabaseHandler(this);
            handler.insertRecord(handler.getWritableDatabase(), nameEditText.getText().toString(), descEditText.getText().toString(), Integer.parseInt(amountEditText.getText().toString()),dateText, type,status);

            Toast toast = Toast.makeText(AddRecord.this, "Record Added Successfully", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
        else
        {
            Toast toast = Toast.makeText(AddRecord.this, "Please Choose Date", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
