package HealtchPac.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    private AdminSQLiteOpenHelper admin;
    private Cursor fila;
    private SQLiteDatabase bd;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        admin = new AdminSQLiteOpenHelper(this, "bd1", null, 2);
        bd = admin.getWritableDatabase();
        list = (ListView)findViewById(R.id.listview);

        ArrayList<String> items = new ArrayList<>();

       boolean cp=true;
        if(bd!=null) {

            Log.e("recisiste","bus");

            fila = bd.rawQuery("SELECT * FROM alarma ",  null);
             while (fila.moveToNext()) { // Extract data.


            Log.e("selec0", String.valueOf(fila.getString(0)));
            Log.e("selec1", String.valueOf(fila.getString(1)));//medicine
            Log.e("selec2", String.valueOf(fila.getString(2)));//descrip
            Log.e("selec3", String.valueOf(fila.getString(3)));//paciente
            Log.e("selec4", String.valueOf(fila.getString(4)));
            Log.e("selec5", String.valueOf(fila.getString(5)));
            Log.e("selec6", String.valueOf(fila.getString(6)));//nmedcin
            Log.e("selec7", String.valueOf(fila.getString(7)));//nmedcin


                 items.add("id:"+fila.getString(0)+", \nPaciente:" + fila.getString(3) + "\nMedicina:" + fila.getString(1));


             }
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
            list.setAdapter(adaptador);
        }else {
            Log.e("vcio","vcio");
        }
        bd.close();

    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("prueb", String.valueOf(list.getItemAtPosition(position)));
            String[] parts = String.valueOf(list.getItemAtPosition(position)).split(",");
            String part1 = parts[0]; //obtiene: 19
            String[] part2 = parts[0].split(":");
            String part3 = part2[1]; //obtiene: 19
            Log.e(" din",part3);


        }
    });
    }
}
