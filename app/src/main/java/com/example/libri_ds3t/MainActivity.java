package com.example.libri_ds3t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.libri_ds3t.database.SQLHelper;

public class MainActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastar;
    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = findViewById(R.id.txtUsuario);
        txtSenha = findViewById(R.id.txtSenhaLogin);
        btnCadastar = findViewById(R.id.btnCadastrarLogin);
        btnLogar = findViewById(R.id.btnEntrarLogin);

        btnCadastar.setOnClickListener(view->{

            Intent intent = new Intent(
                    MainActivity.this,
                    CadastroUsuario.class
            );

            startActivity(intent);

        });//FIM DO BOTÃO DE CADASTRAR

        btnLogar.setOnClickListener(view->{

            String login = txtLogin.getText().toString();
            String senha = txtSenha.getText().toString();

            int cod_ususario = SQLHelper.getInstance(this)
                                        .login(login, senha);

            if(cod_ususario > 0){

                //AÇÃO LOGADO
                startActivity(
                        new Intent(MainActivity.this, FeedLivro.class)
                );

            }else{

                //AÇÃO ERRO LOGIN
                Toast.makeText(MainActivity.this,
                        "DADOS DE LOGIN INCORRRETOS",
                        Toast.LENGTH_LONG).show();

            }

        });


    }
}