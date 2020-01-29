package HealtchPac.com;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.AlarmClock;
import android.util.Log;
import android.content.pm.PackageManager;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static android.app.PendingIntent.getActivity;

public class AlertReceiver extends BroadcastReceiver {
    MediaPlayer mMediaPlayer;
    private AdminSQLiteOpenHelper admin;
    private Cursor fila;
    private SQLiteDatabase bd;
    Context c;
    private ContentValues registro;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendario = Calendar.getInstance();

        int hora, min,dia,mes,ano, intervalo;

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        String hora_sistema = hora + ":" + min;

     /*   PackageManager pm = context.getPackageManager();
        Intent intent1 = new Intent(AlarmClock.ACTION_SET_ALARM)

                .putExtra(AlarmClock.EXTRA_MESSAGE,"prueb")
                .putExtra(AlarmClock.EXTRA_HOUR,hora)
                .putExtra(AlarmClock.EXTRA_MINUTES,min);
        //PackageManager pm = getActivity().getPackageManager();
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

      if (intent1.resolveActivity(pm)!= null){

          context.startActivity(intent1);

      }*/


       Log.e("reci","entro");

          c=context;
        admin = new AdminSQLiteOpenHelper(context, "bd1", null, 2);
        bd = admin.getWritableDatabase();
        if(bd!=null) {
            Log.e("recisiste",hora_sistema);

            fila = bd.rawQuery("SELECT * FROM alarma where hora="+hora+" and minuto="+min+" ",  null);
           // while (fila.moveToNext()) { // Extract data.

               // Log.e("selec3", String.valueOf(fila.moveToFirst()));
               // Log.e("selec4", String.valueOf(fila.getString(0)));
              //  Log.e("selec4", String.valueOf(fila.getString(1)));
               // Log.e("selec4", String.valueOf(fila.getString(2)));
               // Log.e("selec4", String.valueOf(fila.getString(3)));
               // Log.e("selec4", String.valueOf(fila.getString(4)));
               // Log.e("selec4", String.valueOf(fila.getString(5)));
                //Log.e("selec4", String.valueOf(fila.getString(6)));

                // }
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

                triggerNotification(context);


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

    private void triggerNotification(Context context) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());


        //Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        //context.startService(serviceIntent);

    }


}
