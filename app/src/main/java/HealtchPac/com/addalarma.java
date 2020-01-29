package HealtchPac.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class addalarma extends AppCompatActivity  implements TimePickerDialog.OnTimeSetListener{
    private TextView text_timepicker;
    private EditText paciente;
    private EditText descripcion;
    private EditText medicamento;
    private EditText intervalo;
    private EditText npastilla;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private Cursor fila;
    private ContentValues registro;
    Calendar c = Calendar.getInstance();
    private Spinner spinner;
    int h,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalarma);
        text_timepicker = findViewById(R.id.text_timepicker);
        paciente = findViewById(R.id.editText1);
        descripcion = findViewById(R.id.editText2);
        medicamento = findViewById(R.id.editText3);
        intervalo = findViewById(R.id.editText4);
        npastilla = findViewById(R.id.editText5);
        spinner= findViewById(R.id.spn);

        bus();

        admin = new AdminSQLiteOpenHelper(this, "bd1", null, 2);
        bd = admin.getWritableDatabase();

        Button buttonTimePicker = findViewById(R.id.button_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker ");
            }
        });
        ///////////
        final Button guardar = findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                guar();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void guar() {

       String  tipo1 = spinner.getSelectedItem().toString();
             Log.e("tipo",tipo1);

       try {
           AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bd1", null, 2);
           SQLiteDatabase bd = admin.getReadableDatabase();
           bd = admin.getWritableDatabase();
           String timeText = "";

           int g=c.get(Calendar.MINUTE);
           int ho=  c.get(Calendar.HOUR_OF_DAY);


                registro = new ContentValues();
                registro.put("nombrepastilla", tipo1);
                registro.put("paciente", paciente.getText().toString());
                registro.put("descripcion", descripcion.getText().toString());//nombre del campo
                registro.put("hora", ho);
                registro.put("minuto",g );
                registro.put("npastilla", Integer.parseInt(npastilla.getText().toString()));
                registro.put("intervalo", intervalo.getText().toString());

                Log.e("dtos", registro.toString());

                bd.insert("alarma", null, registro);//nombre de la tabla

           bd.close();

           startAlarm(c, Integer.parseInt(intervalo.getText().toString()));
           Toast.makeText(this, "alarma registrada", Toast.LENGTH_LONG).show();

         }catch ( Exception e){
           Log.e("error_lite",e.getMessage());

       }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        h=hourOfDay;
        m=minute;
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 3);

        updateTimeText(c);
        // startAlarm(c);


    }

    private void updateTimeText(Calendar c) {
        String timeText = "";

        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        text_timepicker.setText(timeText);

        Log.e("hor",timeText);

    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c, int s) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        int p=30;
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        Log.e("strt","kkk");
        Log.e("strtc", String.valueOf(c.getTimeInMillis()));
        Log.e("strtc", String.valueOf(c.getTime()));
        Log.e("strts", String.valueOf(s));
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),(p*1000), pendingIntent);

    }
    public  void bus(){

      ArrayList<String> items = new ArrayList<>();
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bd1", null, 2);
            SQLiteDatabase bd = admin.getReadableDatabase();
            bd = admin.getWritableDatabase();

            if(bd!=null) {
                Log.e("recisiste","bus");
                fila = bd.rawQuery("SELECT * FROM medi ",  null);
                while (fila.moveToNext()) { // Extract data.
                    Log.e("selec0", String.valueOf(fila.getString(0)));
                    Log.e("selec1", String.valueOf(fila.getString(1)));//medicine
                    Log.e("selec2", String.valueOf(fila.getString(2)));//descrip

                   items.add("Nombre:" + fila.getString(1) + "\ntipo:" + fila.getString(2));
                }
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                spinner.setAdapter(adaptador);
            }else {
                Log.e("vcio","vcio");
            }
            bd.close();

        }catch ( Exception e){
            Log.e("error_lite",e.getMessage());

        }

    }
}
