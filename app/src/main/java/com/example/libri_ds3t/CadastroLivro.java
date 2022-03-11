package com.example.libri_ds3t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.libri_ds3t.database.SQLHelper;
import com.example.libri_ds3t.helpers.DateFormat;

public class CadastroLivro extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtFoto;

    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtFoto = findViewById(R.id.txtFoto);

        btnCadastrar = findViewById(R.id.btnCadastrarLivro);

        btnCadastrar.setOnClickListener(view->{

            if(!validate()){

                Toast.makeText(this,"TODOS OS CAMPOS DEVEM SER PREENCHIDOS!",Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titulo_cadastro_livro))
                    .setMessage(getString(R.string.mensagem_cadastro_livro))
                    .setPositiveButton(R.string.cadUsuario, (dialog1, wich)->{

                        /** GRAVAÇÃO DOS DADOS NO SQLITE **/
                        String sTitulo = txtTitulo.getText().toString();
                        String sDescricao = txtDescricao.getText().toString();
                        String sFoto = txtFoto.getText().toString();

                        /** DATA DA GRAVAÇÃO **/
                        DateFormat df = new DateFormat();
                        String sData = df.getDate();

                        boolean cadastroLivro = SQLHelper.getInstance(this)
                                .addBook(1, sTitulo, sDescricao, sFoto, sData);

                        if(cadastroLivro){
                            Toast.makeText(this,
                                    R.string.cadastro_ok,
                                    Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(this,
                                    R.string.cadastro_erro,
                                    Toast.LENGTH_LONG).show();
                        }

                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, wich)->{})
                    .create();

            dialog.show();

        });//FIM DO SETONCLICKLISTENER

    }//FIM DO ONCREATE

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }//FIM DO MÉTODO ONCREATEOPTIONSMENU

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*
        O Log.d PERMITE GERAR SAÍDAS DE DEBUG NO LOGCAT
        PARAMETROS:
        1 - TAG DE IDENTIFICAÇÃO;
        2 - MENSAGEM DE SAÍDA;
        BASTA ACESSAR O LOGCAT E CONFIGURAR AS MENSAGENS
        PARA DEBUG E BUSCAR PELA TAG DE IDENTIFICAÇÃO.
        */
        Log.d("IDITEM", String.valueOf(item.getItemId()));

        switch (item.getItemId()){

            case R.id.menu_cadastrar_livro:
                startActivity(
                        new Intent(
                                this,
                                CadastroLivro.class
                        )
                );
                break;

            case R.id.menu_feed_livro:
                startActivity(
                        new Intent(
                                this,
                                FeedLivro.class
                        )
                );
                break;

            case R.id.menu_sair:
                startActivity(
                        new Intent(
                                this,
                                MainActivity.class
                        )
                );
                break;

        }

        return super.onOptionsItemSelected(item);

    }


    /** FUNÇÃO DE VALIDAÇÃO **/
    public boolean validate(){

        return (
                !txtTitulo.getText().toString().isEmpty() &&
                        !txtDescricao.getText().toString().isEmpty() &&
                        !txtFoto.getText().toString().isEmpty()
        );

    }
}