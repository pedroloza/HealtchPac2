package HealtchPac.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;


    private ImageView imageView;
    private TextView textView,textView2,textView3;

    Button uwu;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    CardView addalarma,viewalarma,addmedi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uwu =(Button) findViewById(R.id.mecha);

        uwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });

        //  imageView=(ImageView) findViewById(R.id.img);
        //textView=(TextView) findViewById(R.id.nom);
      //  textView2=(TextView) findViewById(R.id.correo);
       // textView3=(TextView) findViewById(R.id.id);

        addalarma= (CardView) findViewById(R.id.cardView1);
        viewalarma= (CardView) findViewById(R.id.cardView2);
        addmedi= (CardView) findViewById(R.id.cardView3);


        addmedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent i = new Intent(getApplication(), Main6Activity.class);
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });

        addalarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent i = new Intent(getApplication(), addalarma.class);
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });

        viewalarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent i = new Intent(getApplication(), Main4Activity.class);
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
                }


                //cancelAlarm();





            }
        });



        GoogleSignInOptions gso = new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    setUserData(user);

                }else {
                    goLogInScrren();
                }

            }
        };

    }

    private void setUserData(FirebaseUser user) {

       // textView.setText(user.getDisplayName());
        //textView2.setText(user.getEmail());
        //textView3.setText(user.getUid());
        //Glide.with(this).load(user.getPhotoUrl()).into(imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
       firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }



    private void goLogInScrren() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void remove(View view) {

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){

                    goLogInScrren();
                }else {

                    Toast.makeText(getApplicationContext(),R.string.not_remove,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void salir(View view) {

        firebaseAuth.signOut();


        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){

                    goLogInScrren();
                }else {

                    Toast.makeText(getApplicationContext(),R.string.not_close_session,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

}
