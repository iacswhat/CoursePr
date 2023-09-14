package com.example.acchelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {
    // Объявление имен полей для ввода
    EditText Edit_min;
    EditText Edit_sec;
    EditText Edit_litres;
    EditText Edit_RHr;
    EditText Edit_RMin;
    EditText Edit_Result_Safely;
    EditText Edit_Result_Risky;
    Button myButton;

    Button btnGoToSecAct;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch SW_Heat_Lap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Связь имен с полями на форме
        Edit_min = (EditText) findViewById(R.id.Edit_min);
        Edit_sec = (EditText) findViewById(R.id.Edit_sec);
        Edit_litres = (EditText) findViewById(R.id.Edit_litres);
        Edit_RHr = (EditText) findViewById(R.id.edit_RHr);
        Edit_RMin = (EditText) findViewById(R.id.edit_RMin);
        Edit_Result_Safely = (EditText) findViewById(R.id.Edit_Result_Safely);
        Edit_Result_Risky = (EditText) findViewById(R.id.Edit_Result_Risky);
        myButton = (Button) findViewById(R.id.Calculate_button);
        SW_Heat_Lap = (Switch) findViewById(R.id.Switch_Heat_Lap);
        btnGoToSecAct = (Button) findViewById(R.id.btnGoToSecAct);

        View.OnClickListener oclBtnGoToSecAct = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecAct.class);
                startActivity(intent);
            }
        };
        btnGoToSecAct.setOnClickListener(oclBtnGoToSecAct);


        View.OnClickListener oclCalcBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Edit_min.getText().toString().trim().equals("") & !Edit_sec.getText().toString().trim().equals("") &
                        !Edit_litres.getText().toString().trim().equals("")
                        & !Edit_RHr.getText().toString().trim().equals("") & !Edit_RMin.getText().toString().trim().equals("")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Edit_Result_Safely.setTextColor(getColor(R.color.white));
                        Edit_Result_Risky.setTextColor(getColor(R.color.white));
                    }
                    Edit_Result_Safely.setText("");
                    Edit_Result_Risky.setText("");

                    String min = Edit_min.getText().toString();
                    String sec = Edit_sec.getText().toString();
                    float seconds = Float.parseFloat(sec) / 60;
                    int Minutes = Integer.parseInt(min);
                    float TotalLapTime = Minutes + seconds;

                    String RHoursStr = Edit_RHr.getText().toString();
                    String RMinutesStr = Edit_RMin.getText().toString();

                    int RHours = Integer.parseInt(RHoursStr) * 60;

                    int RMinutes = Integer.parseInt(RMinutesStr);
                    int RaceTime = RHours + RMinutes;

                    float LapCount = RaceTime / TotalLapTime;

                    String LitresPerLap = Edit_litres.getText().toString();
                    float Litres = Float.parseFloat(LitresPerLap);

                    float TotalLitres = 0;
                    if (!SW_Heat_Lap.isChecked()) TotalLitres = Litres * LapCount;
                    else if (SW_Heat_Lap.isChecked()) TotalLitres = Litres * LapCount + Litres;

                    Edit_Result_Safely.setText(String.valueOf(TotalLitres));
                    Edit_Result_Risky.setText(String.valueOf(TotalLitres + 3));
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Edit_Result_Safely.setTextColor(getColor(R.color.red));
                        Edit_Result_Risky.setTextColor(getColor(R.color.red));
                    }
                    Edit_Result_Safely.setText(R.string.Error);
                    Edit_Result_Risky.setText(R.string.Error);
                }
            }
        };
        myButton.setOnClickListener(oclCalcBtn);

    }
}