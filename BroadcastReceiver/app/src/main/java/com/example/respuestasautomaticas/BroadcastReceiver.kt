package com.example.respuestasautomaticas


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.example.respuestasautomaticas.ui.Telefono


class BroadcastReceiver : BroadcastReceiver() {
    private var mListener: PhoneStateListener? = null
    private var mTelephonyManager: TelephonyManager? = null
    private var mContext: Context? = null

    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        val action = intent.action

        if (action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            mTelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mTelephonyManager?.listen(object : PhoneStateListener() {
                    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                        super.onCallStateChanged(state, incomingNumber)

                        when (state) {
                            TelephonyManager.CALL_STATE_RINGING -> {
                                if (incomingNumber != null) {
                                    Log.d("BroadcastReceiver", "Número entrante: $incomingNumber")
                                    Toast.makeText(mContext, "Número entrante: $incomingNumber", Toast.LENGTH_LONG).show()
                                    EnviarMensaje(incomingNumber)
                                } else {
                                    Log.d("BroadcastReceiver", "No se pudo obtener el número")
                                    Log.d("BroadcastReceiver", "Valio no se consiguio")
                                }
                            }
                        }
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE)
            }
        }
    }

    // Método para enviar un mensaje SMS
    private fun EnviarMensaje(numeroEntrante: String) {
        val smsMensaje = SmsManager.getDefault()
        val mensaje = Telefono.Telefono.mensaje

        try {
            smsMensaje.sendTextMessage(numeroEntrante, null, mensaje, null, null)
            Log.d("Mensaje Enviado", "Número: $numeroEntrante, Mensaje: $mensaje")
            Toast.makeText(mContext, "Mensaje enviado a $numeroEntrante", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("Error al enviar mensaje", e.toString())
            Toast.makeText(mContext, "Error al enviar el mensaje", Toast.LENGTH_LONG).show()
        }
    }
}