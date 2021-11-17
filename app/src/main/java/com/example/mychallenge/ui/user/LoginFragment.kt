package com.example.mychallenge.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mychallenge.R
import com.example.mychallenge.common.Extensions.getShowError
import com.example.mychallenge.common.StatusUser
import com.example.mychallenge.databinding.LoginBindingFragment
import com.example.mychallenge.ui.home.HomeActivity
import com.example.mychallenge.ui.user.viewmodel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.concurrent.Executor
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: BiometricFragment(){
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loginBinder: LoginBindingFragment
    private val loginView get() = loginBinder
    private lateinit var mail: String
    private lateinit var password: String
    private lateinit var mBiometricPrompt: BiometricPrompt


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loginBinder = DataBindingUtil.inflate(
            inflater, R.layout.login_fragment, container,false)

        return loginView.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startLoginByUser()

        initObserveVM()
        loginView.buttonLogin.setOnClickListener(onPressLoginButton)
        loginBinder.labelRegister.setOnClickListener(onPressRegisterClickListener)
        loginBinder.labelPassword.setOnClickListener(onPressLabelClickListener)
        loginBinder.imageFinger.setOnClickListener(onPressFingerClickListener)
        loginBinder.labelChangeUser.setOnClickListener(onPressChangeUserClickListener)
    }

    private fun startLoginByUser(){
        loginViewModel.userSharePreferences.observe(viewLifecycleOwner){ user ->
            if(user != null)
                loginBinder.textNameUser.text = user.name
        }

        loginViewModel.biometricEnable.observe(viewLifecycleOwner){ enable ->
            checkTypeLogin(enable)
        }

        loginViewModel.executor.observe(viewLifecycleOwner){
            mBiometricPrompt = getBiometricPrompt(it)
        }
    }

    private fun initObserveVM(){

        loginViewModel.loading.observe(viewLifecycleOwner){ visible ->
            loginBinder.progressCircular.isVisible = visible
        }

        loginViewModel.validInfoMsg.observe(viewLifecycleOwner, { statusUser ->
            when(statusUser){
                StatusUser.MAIL_ERROR -> loginBinder.layoutUser.getShowError(getString(R.string.label_mail_error))
                StatusUser.PASSWORD_ERROR -> loginBinder.layoutPassword.getShowError(getString(R.string.label_password_error))
                StatusUser.PASSWORD_NOT_THE_SAME_BY_USER_SAVE ->
                    showDialogError(getString(R.string.label_title_error_login), getString(R.string.label_error_by_password))
                StatusUser.INFO_SUCCESS_BY_USER_SAVE -> goToHome()
                else -> loginViewModel.getLoginUser(mail, password)
            }
        })

        loginViewModel.notUserInDevice.observe(viewLifecycleOwner){ notHasUser ->
            checkVisibleComponents(notHasUser)
        }

        loginViewModel.user.observe(viewLifecycleOwner){ user ->
            if(user != null)
                goToHome()

            else showDialogError(getString(R.string.label_title_error_login), getString(R.string.label_error_login))
        }
    }

    private val onPressLoginButton = View.OnClickListener {
        getCleanError()
        mail = loginBinder.editMail.text.toString()
        password = loginBinder.editPassword.text.toString()

        loginViewModel.validInfoLoginUser(mail, password)
    }

    private val onPressRegisterClickListener =  View.OnClickListener{
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, RegisterFragment())
            .commit()
    }

    private val onPressChangeUserClickListener =  View.OnClickListener{
        loginViewModel.cleanSharePreferences()
    }

    private val onPressLabelClickListener =  View.OnClickListener{
        loginViewModel.getChangeMethodLogin().observe(viewLifecycleOwner){ visible ->
            checkTypeLogin(visible)
            loginBinder.editPassword.text?.clear()
            getCleanError()
        }
    }

    private val onPressFingerClickListener =  View.OnClickListener{
        mBiometricPrompt.authenticate(showDialogBiometric())
    }

    private fun checkTypeLogin(enable: Boolean){
        loginBinder.containerFingerprint.isVisible = enable
        loginBinder.containerPassword.isVisible = !enable
        loginBinder.labelPassword.text = if(enable) getText(R.string.text_login_password)
        else getText(R.string.text_login_by_fingerprint)
    }

    private fun checkVisibleComponents(visible: Boolean){
        loginBinder.textNameUser.isVisible = !visible
        loginBinder.labelChangeUser.isVisible = !visible
        loginBinder.layoutUser.isVisible = visible
        loginBinder.labelRegister.isVisible = visible
        loginBinder.labelPassword.isVisible = !visible
        loginBinder.containerFingerprint.isVisible = !visible
        loginBinder.containerPassword.isVisible = visible
        loginBinder.labelInfo.isVisible = visible
    }


    private fun goToHome(){
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun getCleanError(){
        loginBinder.layoutUser.error = null
        loginBinder.layoutPassword.error = null
    }

    private fun showDialogError(title: String, msgError: String){
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(title)
                .setMessage(msgError)
                .setPositiveButton(getString(R.string.label_button_accept)) { dialog, listener ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}