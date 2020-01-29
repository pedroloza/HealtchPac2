package HealtchPac.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ajustes extends AppCompatActivity {
ImageView ajustes;
    Button btnlogout;
    FirebaseAuth mFirenbaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        ajustes= (ImageView) findViewById(R.id.mecha);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =new Intent(ajustes.this, MainActivity.class);
                startActivity(intent1);
            }
        });




        btnlogout = findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent (ajustes.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });

            }


    }

