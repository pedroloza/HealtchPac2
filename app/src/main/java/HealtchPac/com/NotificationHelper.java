package HealtchPac.com;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private AdminSQLiteOpenHelper admin;
    private Cursor fila;
    private SQLiteDatabase bd;
    private ContentValues registro;

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Calendar calendario = Calendar.getInstance();

        int hora, min,dia,mes,ano, intervalo;

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        String hora_sistema = hora + ":" + min;

        Intent intent1 = new Intent(this.getApplicationContext(), Main3Activity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Intent intent3 = new Intent(this.getApplicationContext(),Main3Activity.class);
        intent3.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent3);
        String n1 = null,n2 = null,n3 = null,n4 = null,n5;

        admin = new AdminSQLiteOpenHelper(this.getApplicationContext(), "bd1", null, 2);
        bd = admin.getWritableDatabase();

        if(bd!=null) {


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
                n1=fila.getString(3);
                n2=fila.getString(1);
                n3=fila.getString(2);
                n4=fila.getString(7);
                Log.e("noti", "notifiv");

                Log.e("selec1", String.valueOf(fila.getString(0)));
                Log.e("selec2", String.valueOf(fila.getString(1)));//medicine
                Log.e("selec3", String.valueOf(fila.getString(2)));//descrip
                Log.e("selec4", String.valueOf(fila.getString(3)));//paciente
                Log.e("selec5", String.valueOf(fila.getString(4)));
                Log.e("selec6", String.valueOf(fila.getString(5)));
                Log.e("selec7", String.valueOf(fila.getString(6)));//nmedcin
                //triggerNotification(context,intent,fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(6));
                //alarm(context);

            }else {

                Log.e("vcio","no alarma");
            }


        }else {
            Log.e("vcio","vcio");
        }
        bd.close();

        String f = "Medicina:"+n2+"\nDescripcion:"+n3 +"\nNumero de Pastilla:"+n4;

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(n1)
                .setContentText(f)
                .setSmallIcon(R.drawable.kk)
                .setColor(Color.BLACK)
               // .setContentIntent(pIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(f))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setLights(Color.MAGENTA, 1000, 1000)
        .setVibrate(new long[]{1000,1000,1000,1000,1000})
        .setDefaults(Notification.DEFAULT_SOUND);


    }
}
