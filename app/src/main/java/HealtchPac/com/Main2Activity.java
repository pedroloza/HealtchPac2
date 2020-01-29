package HealtchPac.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    private EditText editText,editText2,editText3;
    private  String  email ;
    private  String  pass;
    private  String  nombre ;
    private FirebaseAuth firebaseAuth;
    DatabaseReference  databaseReference;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText=(EditText) findViewById(R.id.editText3);
        editText2=(EditText) findViewById(R.id.editText4);
        editText3=(EditText) findViewById(R.id.editText5);
        button=(Button) findViewById(R.id.button3);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre= editText.getText().toString();
                email= editText2.getText().toString();
                pass= editText3.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty() && !nombre.isEmpty() ){

                    reguser();

                }else {
                    Toast.makeText(getApplicationContext(), "Campos Vacios", Toast.LENGTH_SHORT).show();


                }



            }
        });


    }

    private void reguser() {
        Log.e("e",email+pass);
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String id=firebaseAuth.getCurrentUser().getUid();

                    Map<String,Object> map=new HashMap<>();
                    map.put("name",nombre);
                    map.put("email",email);
                    map.put("pass",pass);

                    databaseReference.child("User").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                startActivity(new Intent(Main2Activity.this,MainActivity.class));
                                finish();
                            }else{

                                Toast.makeText(getApplicationContext(), "Create failed2.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }else{

                    Toast.makeText(getApplicationContext(), "Create failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
