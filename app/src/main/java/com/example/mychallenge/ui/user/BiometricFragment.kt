package com.example.mychallenge.ui.user

import android.content.Intent
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import com.example.mychallenge.R
import com.example.mychallenge.ui.home.HomeActivity
import java.util.concurrent.Executor

open class BiometricFragment: Fragment() {

    open fun getBiometricPrompt(executor: Executor): BiometricPrompt{
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(context, errString, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(context, getText(R.string.label_generic_error), Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }
        }

        return BiometricPrompt(this, executor, callback)

    }

    open fun showDialogBiometric(): BiometricPrompt.PromptInfo{
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Iniciar sesion")
            .setSubtitle("Inicia sesion con tu huella dactilar")
            .setDescription("Coloca tu dedo en el sensor para poder acceder a la aplicacion")
            .setNegativeButtonText("Cancelar")
            .build()

        return promptInfo

    }

}