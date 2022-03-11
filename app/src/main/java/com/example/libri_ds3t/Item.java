package com.example.libri_ds3t;

public class Item {

    /*
    TIPOS:
    0 - Livro
    1 - HQ
    2 - Manga
    * */
    private int type;

    /*
    GUARDA OBJETOS DAS CLASSES DE ACORDO COM
    O TIPO
    * */
    private Object object;

    public Item(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
