package com.example.ladm_u4_practica3_raygozalopezmartin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ENTRANTES(ID INTEGER PRIMARY KEY AUTOINCREMENT, CELULAR VARCHAR(200), MENSAJE VARCHAR(2000), STATUS BOOLEAN)")
        db.execSQL("CREATE TABLE MUSICA(ARTISTA VARCHAR(100), FECHA VARCHAR(50), CANCION VARCHAR(100))")

        //JOSE JOSE
        db.execSQL("INSERT INTO MUSICA VALUES('JOSE_JOSE','1970','El triste')")
        db.execSQL("INSERT INTO MUSICA VALUES('JOSE_JOSE','1970','La nave del olvido')")
        db.execSQL("INSERT INTO MUSICA VALUES('JOSE_JOSE','1976','El principe')")
        db.execSQL("INSERT INTO MUSICA VALUES('JOSE_JOSE','1977','Gavilan o paloma')")
        db.execSQL("INSERT INTO MUSICA VALUES('JOSE_JOSE','1977','Amar y querer')")

        //JUAN GABRIEL
        db.execSQL("INSERT INTO MUSICA VALUES('JUAN_GABRIEL','1971','No tengo dinero')")
        db.execSQL("INSERT INTO MUSICA VALUES('JUAN_GABRIEL','1985','Amor eterno')")
        db.execSQL("INSERT INTO MUSICA VALUES('JUAN_GABRIEL','1986','Hasta que te conoci')")
        db.execSQL("INSERT INTO MUSICA VALUES('JUAN_GABRIEL','1979','El Noa-Noa')")
        db.execSQL("INSERT INTO MUSICA VALUES('JUAN_GABRIEL','1971','Me he quedado solo')")

        //BELINDA
        db.execSQL("INSERT INTO MUSICA VALUES('BELINDA','2003','Lo siento')")
        db.execSQL("INSERT INTO MUSICA VALUES('BELINDA','2003','Boba niña nice')")
        db.execSQL("INSERT INTO MUSICA VALUES('BELINDA','2004','Angel')")
        db.execSQL("INSERT INTO MUSICA VALUES('BELINDA','2004','Vivir')")
        db.execSQL("INSERT INTO MUSICA VALUES('BELINDA','2005','Be Free')")

        //THE_BEATLES
        db.execSQL("INSERT INTO MUSICA VALUES('THE_BEATLES','1969','Come Together')")
        db.execSQL("INSERT INTO MUSICA VALUES('THE_BEATLES','1970','Let it Be')")
        db.execSQL("INSERT INTO MUSICA VALUES('THE_BEATLES','1969','Something')")
        db.execSQL("INSERT INTO MUSICA VALUES('THE_BEATLES','1965','Yesterday')")
        db.execSQL("INSERT INTO MUSICA VALUES('THE_BEATLES','1965','In my Life')")

        //QUEEN
        db.execSQL("INSERT INTO MUSICA VALUES('QUEEN','1977','We will rock you')")
        db.execSQL("INSERT INTO MUSICA VALUES('QUEEN','1978','Bicycle Race')")
        db.execSQL("INSERT INTO MUSICA VALUES('QUEEN','1975','Now Im Here')")
        db.execSQL("INSERT INTO MUSICA VALUES('QUEEN','1978','Dont Stop Me Now')")
        db.execSQL("INSERT INTO MUSICA VALUES('QUEEN','1975','Bohemian Rhapsody')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}