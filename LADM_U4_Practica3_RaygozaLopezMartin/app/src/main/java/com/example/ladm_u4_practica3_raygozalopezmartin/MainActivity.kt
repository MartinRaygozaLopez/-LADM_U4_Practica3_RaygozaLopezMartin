package com.example.ladm_u4_practica3_raygozalopezmartin

import android.content.pm.PackageManager
import android.database.sqlite.SQLiteException
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var baseDatosNombre = "listacanciones"

    var siPermisoReceiver = 10
    var siPermisoLectura = 11
    var siPermiso = 12

    var hiloControl : HiloControl?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECEIVE_SMS), siPermisoReceiver)
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS), siPermisoLectura)
        }

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), siPermiso)
        }

        hiloControl = HiloControl(this)
        hiloControl!!.start()
    }

    fun verificarSMS() {
        try {
            val cursor = BaseDatos(this, baseDatosNombre, null, 1)
                .readableDatabase
                .rawQuery("SELECT * FROM ENTRANTES", null)

            if(cursor.moveToFirst()) {
                do {
                    var numero = cursor.getString(1)
                    var mensaje = cursor.getString(2)
                    var array = mensaje.split(" ")

                    if(array.isNotEmpty()) {
                        // Para ignorar todos los mesajes al menos que empiecen con la palabra MUSICA
                        if(array[0] != "MUSICA") {
                            borrarID(cursor.getString(0))
                            return
                        }

                        // Si empieza con MUSICA pero la sintaxis esta mal
                        if(array[0] == "MUSICA" && array.size != 3) {
                            enviarSMS(numero, "ERROR, LA SINTAXIS ES 'MUSICA ARTISTA AÑO'")
                            borrarID(cursor.getString(0))
                            return
                        }

                        var data = recuperarCanciones(array[1], array[2])
                        var dataCompleta = ""
                        if(data != "NO EXISTE NINGUNA COINCIDENCIA") {
                            dataCompleta = "Las canciones del artista ${array[1]} durante el año ${array[2]} son:\n" + data
                            enviarSMS(numero, dataCompleta)
                        } else {
                            enviarSMS(numero, data)
                        }
                        borrarID(cursor.getString(0))
                    }
                } while (cursor.moveToNext())
            }
        } catch (err : SQLiteException){
            Toast.makeText(this, err.message.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun borrarID(id : String) {
        try {
            var baseDatos = BaseDatos(this, baseDatosNombre, null, 1)
            var insertar = baseDatos.writableDatabase
            var SQL = "DELETE FROM ENTRANTES WHERE ID = ?"

            var parametros = arrayOf(id)
            insertar.execSQL(SQL,parametros)
            insertar.close()
            baseDatos.close()
        } catch (error : SQLiteException) {  }
    }

    fun recuperarCanciones(artista : String, a : String) : String {
        var data = ""

        try {
            var baseDatos = BaseDatos(this, baseDatosNombre,null,1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM MUSICA WHERE ARTISTA = ? AND FECHA = ?"
            var parametros = arrayOf(artista, a)
            var cursor = select.rawQuery(SQL,parametros)

            if (cursor.moveToFirst()) {
                do {
                    data = data + "-> " + cursor.getString(2) + "\n"
                } while (cursor.moveToNext())
            } else {
                data = "NO EXISTE NINGUNA COINCIDENCIA"
            }
            cursor.close()
        } catch (err : Exception){
            Toast.makeText(this, err.message.toString(), Toast.LENGTH_LONG)
                .show()
        }
        return data
    }

    fun enviarSMS(numero: String, mensaje : String) {
        SmsManager.getDefault().sendTextMessage(numero, null, mensaje, null, null)
    }
}
