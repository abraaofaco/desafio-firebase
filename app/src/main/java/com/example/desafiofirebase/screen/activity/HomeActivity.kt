package com.example.desafiofirebase.screen.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.ActivityHomeBinding
import com.example.desafiofirebase.screen.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding
    private val mViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.apply {
            fab.setOnClickListener {
                findNavController(R.id.app_nav_host_fragment).navigate(R.id.action_gamesListFragment_to_gameDataFragment)
            }
        }
    }

    fun showAddFab() {
        mBinding.fab.show()
    }

    fun hideAddFab() {
        mBinding.fab.hide()
    }
}