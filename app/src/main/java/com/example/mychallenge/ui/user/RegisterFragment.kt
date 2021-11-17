package com.example.mychallenge.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mychallenge.R
import com.example.mychallenge.common.StatusUser
import com.example.mychallenge.databinding.RegisterBindingFragment
import com.example.mychallenge.ui.home.HomeActivity
import com.example.mychallenge.ui.user.viewmodel.RegisterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var registerBinder: RegisterBindingFragment
    private val registerView get() = registerBinder
    private var name: String? = null
    private var mail: String? = null
    private var password: String? = null
    private var passwordAgain: String? = null
    private var mBiometricPrompt: BiometricPrompt ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerBinder = DataBindingUtil.inflate(
            inflater, R.layout.register_fragment, container,false)

        return registerView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        registerBinder.buttonRegister.setOnClickListener(onPressRegisterListener)
        registerBinder.labelLogin.setOnClickListener(onPressLoginListener)
    }

    private fun initObserver(){
        registerViewModel.loading.observe(viewLifecycleOwner){ visible ->
            registerBinder.progressCircular.isVisible = visible
        }

        registerViewModel.validInfoMsg.observe(viewLifecycleOwner, { statusUser ->
            Log.d("validcion usuario", "initObserver: " + statusUser)
            when (statusUser) {
                StatusUser.NAME_ERROR -> registerBinder.layoutUserName.error =
                    getString(R.string.label_name_error)
                StatusUser.MAIL_ERROR -> registerBinder.layoutUserMail.error =
                    getString(R.string.label_mail_error)
                StatusUser.PASSWORD_ERROR -> registerBinder.layoutPassword.error =
                    getString(R.string.label_password)
                StatusUser.PASSWORD_NOT_THE_SAME -> {
                    registerBinder.layoutPassword.error =
                        getString(R.string.label_passwords_not_equals)
                    registerBinder.layoutPasswordAgain.error =
                        getString(R.string.label_passwords_not_equals)
                }

                else -> validaUserExist()
            }

        })

        registerViewModel.oldUser.observe(viewLifecycleOwner, { user ->
            Log.d("validcion usuario", "validaUserExist: " + user?.toString())
            if (user == null)
                registerViewModel.getRegisterUser(name!!, mail!!, password!!)
            else
                showDialogError(
                    getString(R.string.label_title_error_register),
                    getString(R.string.label_error_mail_registrated))

        })

        registerViewModel.userRegisterSuccess.observe(viewLifecycleOwner, { user ->
            if (user != null){
                goToHome()

            } else
                showDialogError(getString(R.string.label_title_error_register), getString(R.string.label_error_register))
        })
    }

    private val onPressRegisterListener = View.OnClickListener {
        getCleanError()
        name = registerBinder.editName.text.toString()
        mail = registerBinder.editEmail.text.toString()
        password = registerBinder.editPassword.text.toString()
        passwordAgain = registerBinder.editPasswordAgain.text.toString()

        registerViewModel.validInfoRegisterUser(name!!, mail!!, password!!, passwordAgain!!)
    }

    private fun validaUserExist(){
        registerViewModel.getUserExist(mail!!)
    }

    private val onPressLoginListener = View.OnClickListener {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment())
            .commit()
    }


    fun goToHome(){
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun getCleanError(){
        registerBinder.layoutUserName.error = null
        registerBinder.layoutUserMail.error = null
        registerBinder.layoutPassword.error = null
        registerBinder.layoutPasswordAgain.error = null
    }

    private fun showDialogError(title: String, msgError: String){
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(msgError)
                .setPositiveButton(getString(R.string.label_button_accept)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}