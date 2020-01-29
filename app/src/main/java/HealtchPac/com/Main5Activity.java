package HealtchPac.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Main5Activity extends AppCompatActivity {
    private AdminSQLiteOpenHelper admin;
    private Cursor fila;
    private SQLiteDatabase bd;
    private ContentValues registro;
    Context context;
    private EditText editText1,editText2,editText3,editText4,editText5;
    private TextView textView;
    private Button button1,button2;
    private  String valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        this.context = this;

        editText1 =(EditText)findViewById(R.id.editText1) ;
        editText2 =(EditText)findViewById(R.id.editText2) ;
        editText3 =(EditText)findViewById(R.id.editText3) ;
        editText4 =(EditText)findViewById(R.id.editText4) ;
        editText5 =(EditText)findViewById(R.id.editText5) ;
        textView = (TextView)findViewById(R.id.text_timepicker);
        button1  = (Button) findViewById(R.id.guardar);
        button2  = (Button) findViewById(R.id.eliminar);

        admin = new AdminSQLiteOpenHelper(context, "bd1", null, 2);
        bd = admin.getWritableDatabase();

        valor = getIntent().getStringExtra("id");
        Log.e("id",valor);

        if(bd!=null) {

            fila = bd.rawQuery("SELECT * FROM alarma where idal="+valor+" ",  null);

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

                editText1.setText(fila.getString(3));
                editText2.setText(fila.getString(2));
                editText3.setText(fila.getString(1));
                editText4.setText(fila.getString(6));
                editText5.setText(fila.getString(7));
                textView.setText(fila.getString(4)+":"+fila.getString(5));


                //triggerNotification(context,intent,fila.getString(1),fila.getString(2),fila.getString(3),fila.getString(6));
                //alarm(context);
            }else {

                Log.e("vcio","no alarma");
            }


        }else {
            Log.e("vcio","vcio");
        }
        bd.close();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin = new AdminSQLiteOpenHelper(context, "bd1", null, 2);
                bd = admin.getWritableDatabase();

                //Establecemos los campos-valores a actualizar
                 registro = new ContentValues();
                 registro.put("nombrepastilla",editText3.getText().toString());
                 registro.put("paciente", editText1.getText().toString());
                 registro.put("descripcion", editText2.getText().toString());//nombre del campo
                 registro.put("npastilla", editText5.getText().toString());
                 //Actualizamos el registro en la base de datos
                 String id = "idal="+valor;
                 bd.update("alarma", registro , id , null);
              Log.e("modi", String.valueOf(registro));
                Intent i=new Intent(Main5Activity.this, MainActivity.class);

                startActivity(i);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin = new AdminSQLiteOpenHelper(context, "bd1", null, 2);
                bd = admin.getWritableDatabase();
                Log.e("id",valor);
                String id2 = "idal="+valor;
                Log.e("id",id2);
                bd.delete("alarma", id2, null);

                Intent i=new Intent(Main5Activity.this, MainActivity.class);

                startActivity(i);
            }
        });
    }
}
