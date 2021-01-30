package com.example.desafiofirebase.screen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.desafiofirebase.R
import com.example.desafiofirebase.screen.activity.AuthActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    private lateinit var mActivity: AuthActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as AuthActivity
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            delay(2000)

            if (mActivity.appAuth.currentUser() != null) {
                mActivity.navigateUpToHome()
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }
    }
}