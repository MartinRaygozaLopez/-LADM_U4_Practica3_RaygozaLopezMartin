package com.example.ladm_u4_practica3_raygozalopezmartin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReceiver : BroadcastReceiver() {

    var baseDatosNombre = "listacanciones"

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras

        if(extras != null) {
            var sms = extras.get("pdus") as Array<Any>

            for(indice in sms.indices) {
                var formato = extras.getString("format")

                var smsMensaje = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    SmsMessage.createFromPdu(sms[indice] as ByteArray, formato)
                } else {
                    SmsMessage.createFromPdu(sms[indice] as ByteArray)
                }

                var celularOrigen = smsMensaje.originatingAddress
                var contenidoSMS = smsMensaje.messageBody.toString()

                // GUARDAR SOBRE TABLA SQLITE
                try {
                    var baseDatos = BaseDatos(context, baseDatosNombre, null, 1)
                    var insertar = baseDatos.writableDatabase
                    var SQL = "INSERT INTO ENTRANTES VALUES (null, '${celularOrigen}','${contenidoSMS}', '${false}')"
                    insertar.execSQL(SQL)
                    baseDatos.close()

                } catch (err : SQLiteException) {
                    Toast.makeText(context, err.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
