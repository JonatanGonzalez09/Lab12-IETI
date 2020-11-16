package edu.eci.myapplicationlab12_ieti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sancarbar.myapplication.R;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.myapplicationlab12_ieti.config.Token;
import edu.eci.myapplicationlab12_ieti.model.LoginWrapper;
import edu.eci.myapplicationlab12_ieti.service.AuthService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity  extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private AuthService authService;
    private Button mybtnLogin;
    private EditText myeditTextTextEmailAddress;
    private EditText myPassword;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http:/10.0.2.2:8080") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService authService = retrofit.create(AuthService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void onLoginClicked(View view){
        mybtnLogin = (Button) findViewById(R.id.btnLogin);
        myeditTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        myPassword = (EditText) findViewById(R.id.editTextTextPassword);

        mybtnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String login = myeditTextTextEmailAddress.getText().toString();
            if (login.isEmpty()) {
                myeditTextTextEmailAddress.setError("Invalid email");
            } else {
                Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
            }
        }
        });
        String email = myeditTextTextEmailAddress.getText().toString();
        String password = myPassword.getText().toString();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Token> response =
                            authService.loginUser(new LoginWrapper("test@mail.com", "password")).execute();
                    Token token = response.body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
