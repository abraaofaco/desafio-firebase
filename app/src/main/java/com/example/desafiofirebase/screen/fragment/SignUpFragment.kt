package com.example.desafiofirebase.screen.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.FragmentSignUpBinding
import com.example.desafiofirebase.screen.activity.AuthActivity
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var mBinding: FragmentSignUpBinding
    private lateinit var mActivity: AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentSignUpBinding.bind(view)
        mActivity = activity as AuthActivity

        mBinding.apply {
            btnSignUp.setOnClickListener {
                createAccount()
            }
        }
    }

    private fun createAccount() {
        val email = mBinding.txtEmail.text.toString()
        val password = mBinding.txtPassword.text.toString()
        val passwordConfirmation = mBinding.txtPasswordConfirmation.text.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            val activity = this@SignUpFragment.activity as AuthActivity

            try {
                activity.appAuth.signUp(email, password)
                activity.navigateUpToHome()
            } catch (e: Exception) {
                Log.i("SignInFragment@signIn", e.message, e)
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}