package HealtchPac.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Main6Activity extends AppCompatActivity {
    private EditText editText1,editText2;
    private Cursor fila;
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase bd;
    private ContentValues registro;
    private Button button;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        list = (ListView)findViewById(R.id.listview);


        bus();
        editText1=(EditText) findViewById(R.id.editText1);
        editText2=(EditText) findViewById(R.id.editText2);
        button=(Button) findViewById(R.id.guardar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gu();

            }
        });

    }

   public void  gu(){


       try {
           AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bd1", null, 2);
           SQLiteDatabase bd = admin.getReadableDatabase();
           bd = admin.getWritableDatabase();

           registro = new ContentValues();
           registro.put("nombre", editText1.getText().toString());
           registro.put("tipo",editText2.getText().toString() );
           Log.e("dtos", registro.toString());
           bd.insert("medi", null, registro);//nombre de la tabla
           bd.close();
           bus();
       }catch ( Exception e){
           Log.e("error_lite",e.getMessage());

       }


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
                   items.add("id:"+fila.getString(0)+", \nNombre:" + fila.getString(1) + "\ntipo:" + fila.getString(2));
               }
              ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
              list.setAdapter(adaptador);
           }else {
               Log.e("vcio","vcio");
           }
           bd.close();

       }catch ( Exception e){
           Log.e("error_lite",e.getMessage());

       }

   }

}
