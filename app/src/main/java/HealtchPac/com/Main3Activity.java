package HealtchPac.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

public class Main3Activity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pending_intent;

    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    private AlarmReceiver alarm;
    private AdminSQLiteOpenHelper admin;
    private Cursor fila;
    private SQLiteDatabase bd;
    private ContentValues registro;

   ImageView ajustes;


    Main3Activity inst;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        this.context = this;


        ajustes= (ImageView) findViewById(R.id.mecha);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(Main3Activity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        //alarm = new AlarmReceiver();
        alarmTextView = (TextView) findViewById(R.id.alarmText);


        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // set the alarm to the time that you picked

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        final Calendar calendar = Calendar.getInstance();
        //ini();


                calendar.add(Calendar.SECOND, 2);
                //setAlarmText("You clicked a button");

                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);

                Log.e("MyActivity", "In the receiver with " + hour + " and " + minute);
                setAlarmText("You clicked a " + hour + " and " + minute);


                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                myIntent.putExtra("extra", "yes");
                pending_intent = PendingIntent.getBroadcast(Main3Activity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

               alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);


                // now you should change the set Alarm text so it says something nice


                setAlarmText("Alarma  " + hour + ":" + (minute));
                //Toast.makeText(getApplicationContext(), "You set the alarm", Toast.LENGTH_SHORT).show();



        Button stop_alarm= (Button) findViewById(R.id.stop_alarm);
        stop_alarm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                int min = 1;
                int max = 9;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                myIntent.putExtra("extra", "no");
                sendBroadcast(myIntent);

                alarmManager.cancel(pending_intent);
                setAlarmText("");
                //setAlarmText("You clicked a " + " canceled");

               admin = new AdminSQLiteOpenHelper(context, "bd1", null, 2);
                bd = admin.getWritableDatabase();
                Calendar calendario = Calendar.getInstance();
                int hora2, min2,dia,mes,ano, intervalo;
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH)+1;
                ano = calendario.get(Calendar.YEAR);
                hora2 = calendario.get(Calendar.HOUR_OF_DAY);
                min2 = calendario.get(Calendar.MINUTE);

                if(bd!=null) {

                    fila = bd.rawQuery("SELECT * FROM alarma where hora="+hora2+" and minuto="+min2+" ",  null);

                    if(fila.moveToFirst()){
                        // alarma=fila.getString(0);
                        // titulo=fila.getString(1);
                        //descripcion =fila.getString(2);
                        //triggerNotification(context,titulo+"\n"+descripcion);


                        Log.e("selec0", String.valueOf(fila.getString(0)));//id
                        Log.e("selec1", String.valueOf(fila.getString(1)));//medicine
                        Log.e("selec2", String.valueOf(fila.getString(2)));//descrip
                        Log.e("selec3", String.valueOf(fila.getString(3)));//paciente
                        Log.e("selec4", String.valueOf(fila.getString(4)));//hr
                        Log.e("selec5", String.valueOf(fila.getString(5)));//minu
                        Log.e("selec6", String.valueOf(fila.getString(6)));//inter
                        Log.e("selec7", String.valueOf(fila.getString(7)));//nmedi

                        Calendar c2 = Calendar.getInstance();

                        c2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fila.getString(4)));
                        c2.set(Calendar.MINUTE, Integer.parseInt(fila.getString(5))+Integer.parseInt(fila.getString(6)));
                        c2.set(Calendar.SECOND, 3);


                        Log.e("ho", String.valueOf(c2.getTime()));
                        //Establecemos los campos-valores a actualizar
                        registro = new ContentValues();
                        registro.put("nombrepastilla", fila.getString(1));
                        registro.put("paciente", fila.getString(3));
                        registro.put("descripcion", fila.getString(2));//nombre del campo
                        registro.put("hora", c2.get(Calendar.HOUR_OF_DAY));
                        registro.put("minuto",c2.get(Calendar.MINUTE));
                        registro.put("intervalo", fila.getString(6));
                        registro.put("npastilla", Integer.parseInt(fila.getString(7))-1);
                        //Actualizamos el registro en la base de datos
                        String id = "idal="+Integer.parseInt(fila.getString(0));

                        bd.update("alarma", registro,id , null);
                        if ((Integer.parseInt(fila.getString(7))-1)==0){
                            String id2 = "idal="+Integer.parseInt(fila.getString(0));
                            bd.delete("alarma", id2, null);
                        }else {


                            startAlarm(c2,1);
                        }



                        //triggerNotification(context,intent,fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(6));
                        //alarm(context);
                    }else {

                        Log.e("vcio","no alarma");
                    }


                }else {
                    Log.e("vcio","vcio");
                }
                bd.close();

            }
        });

    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
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
}
