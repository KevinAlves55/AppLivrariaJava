package com.example.libri_ds3t.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.libri_ds3t.Item;
import com.example.libri_ds3t.Livro;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {

    /** ATRIBUTOS DE CONEXÃO E CRIAÇÃO DO BANCO DE DADOS **/
    private static final String DB_NAME = "libri";
    private static final int DB_VERSION = 2;
    private static SQLHelper INSTANCE;

    /** MÉTODO DE RECUPERAÇÃO DA CONEXÃO COM O SQLITE **/
    public static SQLHelper getInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = new SQLHelper(context);
        }

        return INSTANCE;

    }

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_usuario" +
                "(cod_usuario INTEGER PRIMARY KEY," +
                "nome TEXT," +
                "sobrenome TEXT," +
                "email TEXT," +
                "login TEXT," +
                "senha TEXT," +
                "created_date DATETIME);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_livro(" +
                "cod_livro INTEGER PRIMARY KEY," +
                "cod_usuario INTEGER," +
                "titulo TEXT," +
                "descricao TEXT," +
                "foto TEXT," +
                "created_date DATETIME," +
                "FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario))");

    }//FIM DO MÉTODO onUpgrade

    /** CRIAÇÃO DO MÉTODO DE INSERÇÃO DE DADOS DE USUÁRIO**/
    public boolean addUser(String nome, String sobrenome, String email,
                           String login, String senha, String created_data){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try{

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("email", email);
            values.put("login", login);
            values.put("senha", senha);
            values.put("created_date", created_data);

            sqLiteDatabase.insertOrThrow("tbl_usuario",
                                         null,
                                         values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;


        }catch (Exception e){

            Log.d("SQLERRO", e.getMessage());
            return false;

        }finally {

            if(sqLiteDatabase.isOpen()){
                sqLiteDatabase.endTransaction();
            }

        }

    }//FIM DO MÉTODO ADDUSER

    /** CRIAÇÃO DO MÉTODO DE INSERÇÃO DE LIVROS**/
    public boolean addBook(int cod_usuario, String titulo, String descricao, String foto,
                           String created_date){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        try{

            sqLiteDatabase.beginTransaction();

            ContentValues values = new ContentValues();

            values.put("cod_usuario", cod_usuario);
            values.put("titulo", titulo);
            values.put("descricao", descricao);
            values.put("foto", foto);
            values.put("created_date", created_date);

            sqLiteDatabase.insertOrThrow("tbl_livro",
                    null,
                    values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

        }catch (Exception e){

            Log.d("SQLERRO", e.getMessage());
            return false;

        }finally {

            if(sqLiteDatabase.isOpen()){
                sqLiteDatabase.endTransaction();
            }

        }

    }//FIM DO MÉTODO addBook

    /** CRIAÇÃO DO MÉTODO DE LOGIN**/
    public int login(String login, String senha){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase
                        .rawQuery("SELECT * FROM tbl_usuario" +
                                      " WHERE login = ? AND senha = ?",
                                       new String[]{login, senha});

        try{

            if(cursor.moveToFirst()){

                int cod_usuario = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                return cod_usuario;

            }

        }catch(Exception e){

            Log.d("SQLiteError", e.getMessage());
            return 0;

        }finally {

            if(cursor != null && !cursor.isClosed()){

                cursor.close();

            }

        }

        return 0;

    }

    /** LISTAGEM DE LIVROS POR USUÁRIO **/
    public List<Item> listBooks(){

        List<Item> itens = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tbl_livro " +
                                        "WHERE cod_usuario = ?",
                                    new String[]{"1"});

        try{

            if(cursor.moveToFirst()){

                do{

                    Livro livro = new Livro(
                            cursor.getString(cursor.getColumnIndex("titulo")),
                            cursor.getString(cursor.getColumnIndex("descricao"))
                    );

                    itens.add(new Item(0, livro));

                }while(cursor.moveToNext());

            }

        }catch(Exception e){

            Log.d("SQLITERRO:", e.getMessage());

        }finally{

            if(cursor != null  && !cursor.isClosed()){

                cursor.close();

            }

        }

        return itens;

    }

}//FIM DA CLASSE SQLHelper












