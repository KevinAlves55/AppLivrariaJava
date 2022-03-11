package com.example.libri_ds3t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.libri_ds3t.Livro;
import com.example.libri_ds3t.database.SQLHelper;

import java.util.List;

public class FeedLivro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> livros = SQLHelper.getInstance(this).listBooks();

        recyclerView.setAdapter(new LivroAdapater(livros));

    }//FIM DO MÉTODO ONCREATE

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

    }//FIM DO MENU

    /***** INICIO DO RECYCLERVIEW *****/

    /** CLASSE DE ADAPTER **/
    private class LivroAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        //ATRIBUTO DE ITENS
        List<Item> item;

        //MÉTODO CONSTRUTOR


        public LivroAdapater(List<Item> item) {
            this.item = item;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if(viewType == 0){

                return new LivroAdapater.LivroViewHolder(
                        LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_container_livro,
                                parent,
                                false
                        )
                );

            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if(getItemViewType(position) == 0){
                Livro livro = (Livro) item.get(position).getObject();
                ( (LivroAdapater.LivroViewHolder) holder ).setLivroData(livro);
            }

        }

        /*MÉTODO AUXILIAR DO MÉTODO onBindViewHolder
        *RECUPERA E RETORNA O TIPO DE ITEM 0 - LIVRO, 1 - HQ E 2 - MANGÁ
        * */
        public int getItemViewType(int position){

            return  item.get(position).getType();

        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        /** CLASSE DE VIEWHOLDER (SECUNDÁRIO DE ADAPTER) **/
        private class LivroViewHolder extends RecyclerView.ViewHolder {

            private TextView textLivroTitulo, textLivroDescricao;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                textLivroTitulo = itemView.findViewById(R.id.textLivroTitulo);
                textLivroDescricao = itemView.findViewById(R.id.textLivroDescricao);

            }//FIM DO CONSTRUTOR

            public void setLivroData(Livro livro){

                textLivroTitulo.setText(livro.getTitulo());
                textLivroDescricao.setText(livro.getDescricao());

            }

        }//FIM DA CLASSE VIEWHOLDER


    }

    /***** FIM DO RECYCLERVIEW *****/

}//FIM DA CLASSE








