package com.gmail.joaomarcos.cronometro2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    Button start,stop,reset;
    private long timeWhenStopped = 0;
    String horaAtual2;
    int qtdPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);
        Chronometer chrono  = findViewById(R.id.chronometer);
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s = (int)(time - h*3600000- m*60000)/1000 ;
                String t = ((m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s));
                chronometer.setText(t);
            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("00:00");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                chrono.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
                chrono.stop();

                // Recupera a data e hora de parada do cronômetro
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                String currentDateandTime = sdf.format(new Date());

                String hist1 = currentDateandTime.substring(0,10);
                String hist2 = currentDateandTime.substring(11,19);

                String horaAtual = currentDateandTime;

                final TextView historico5 = findViewById(R.id.historico5);
                historico5.setText(horaAtual2);

                qtdPause++;

                for(int i=0; qtdPause>i; i++){
                    horaAtual2 = (hist1+" "+hist2);
                    final TextView historico4 = findViewById(R.id.historico4);
                    historico4.setText(horaAtual2);
                }








                // Dicas para as contrações
//                if (timeWhenStopped>=-4000){
//                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                    alertDialog.setTitle("Dica");
//                    alertDialog.setMessage("Vire de lado");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }else if(timeWhenStopped>=-10000){
//                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                    alertDialog.setTitle("Dica");
//                    alertDialog.setMessage("Sente-se");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                chrono.stop();
            }
        });
    }



}