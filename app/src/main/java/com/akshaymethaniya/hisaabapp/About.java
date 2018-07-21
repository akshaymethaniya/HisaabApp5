package com.akshaymethaniya.hisaabapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView update=(TextView)findViewById(R.id.update);
        TextView contact=(TextView)findViewById(R.id.contact);
        String contactText="For Any Queries or Reporting Contact :- akshaymethaniya@gmail.com";
        contact.setText(contactText);
        try
        {
            update.setMovementMethod(LinkMovementMethod.getInstance());
            contact.setMovementMethod(LinkMovementMethod.getInstance());
            String text="<a href='https://drive.google.com/open?id=19AQpJx3IIBgNi8QewtZNgftZBw0DjQnv'>Click Here To Check For Updates</a>";
            update.setText(Html.fromHtml(text));

        }catch (NullPointerException e){
            Toast toast=Toast.makeText(About.this,"Click Again",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
