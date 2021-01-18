package co.edu.unipiloto.stopwatch;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Stopwatch extends Activity {
    //Segundos a mostrar en el cronometro
    private int seconds = 0;
    //comprobar que este corriendo
    private boolean running;
    private boolean wasRunning;
    private int vuelta;
    private int num_vuelta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //arrancar el cronometro
    public void onClickStart(View view) {
        running = true;
    }

    //Detener El Cronometro
    public void onClickStop(View view) {
        running = false;
    }

    //Resetear el cronometro
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        vuelta = 0;
        num_vuelta = 0;
        TextView numero_vuelta = (TextView) findViewById(R.id.numero_vuelta); numero_vuelta.setText("0");
        TextView tiempo_vuelta = (TextView) findViewById(R.id.tiempo_vuelta); tiempo_vuelta.setText("0");
    }

    //Registrar Vuelta
    public void onClickRegister(View view) {
        if (num_vuelta <=9) {
            running = true;
            vuelta = seconds;
            num_vuelta = num_vuelta + 1;
        }

        TextView numero_vuelta = (TextView) findViewById(R.id.numero_vuelta); numero_vuelta.setText(String.valueOf(num_vuelta));
        TextView tiempo_vuelta = (TextView) findViewById(R.id.tiempo_vuelta); tiempo_vuelta.setText(String.valueOf(vuelta));

/*        Log.d("Numero de vuelta", String.valueOf(num_vuelta));
        Log.d("Tiempo", String.valueOf(vuelta));*/


    }



    //Setear el cronometro
    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}