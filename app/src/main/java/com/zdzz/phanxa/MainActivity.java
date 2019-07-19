package com.zdzz.phanxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zdzz.phanxa.service.time.TimeService;
import com.zdzz.phanxa.service.time.impl.TimeServiceImpl;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Context
    private TextView textView;

    //Service
    private TimeService timeService;

    //Model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
        initView();

        doGetMilliSecondsTime();
    }

    private void doGetMilliSecondsTime() {
        textView.setOnClickListener(this);
    }

    private void initService() {
        timeService = new TimeServiceImpl();
    }

    private void initView() {
        textView = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.textView){
            try {
                if (Long.getLong(textView.getText().toString()) instanceof  Long){
                    textView.setText(new Date(Long.parseLong(textView.getText().toString())).toString());
                }
                long millisecondTime = timeService.getMillisecondTime();
                textView.setText(String.valueOf(millisecondTime));
            }catch (Exception e){

            }
        }

    }
}
