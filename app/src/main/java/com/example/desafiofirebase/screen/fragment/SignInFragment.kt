package com.example.desafiofirebase.screen.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.FragmentSignInBinding
import com.example.desafiofirebase.screen.activity.AuthActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var mBinding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentSignInBinding.bind(view)

        mBinding.apply {
            btnSignIn.setOnClickListener {
                signIn()
            }

            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

    private fun signIn() {
        val email = mBinding.txtEmail.text.toString()
        val password = mBinding.txtPassword.text.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            val activity = this@SignInFragment.activity as AuthActivity

            try {
                activity.appAuth.signIn(email, password)
                activity.navigateUpToHome()
            } catch (e: Exception) {
                Log.i("SignInFragment@signIn", e.message, e)
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}