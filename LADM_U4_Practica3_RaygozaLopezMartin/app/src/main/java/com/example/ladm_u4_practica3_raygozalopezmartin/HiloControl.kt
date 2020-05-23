package com.example.ladm_u4_practica3_raygozalopezmartin

class HiloControl (p:MainActivity) : Thread(){
    private var iniciado = false
    private var puntero = p
    private var pausa = false

    override fun run() {
        super.run()
        iniciado = true
        while(iniciado){
            sleep(1000)
            if(pausa == false){
                puntero.runOnUiThread {
                    puntero.verificarSMS()
                }
            }
        }
    }
}