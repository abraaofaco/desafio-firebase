package com.example.desafiofirebase.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.FragmentGameDetailsBinding
import com.example.desafiofirebase.other.Constants.SELECTED_GAME_PUT_EXTRA
import com.example.desafiofirebase.screen.activity.HomeActivity
import com.example.desafiofirebase.screen.viewmodel.GameViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private lateinit var mBinding: FragmentGameDetailsBinding
    private lateinit var gameUUID: String
    private val mViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            gameUUID = it.getString(SELECTED_GAME_PUT_EXTRA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentGameDetailsBinding.bind(view)

        mBinding.apply {
            fab.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SELECTED_GAME_PUT_EXTRA, gameUUID)

                findNavController().navigate(
                    R.id.action_gameDetailsFragment_to_gameDataFragment,
                    bundle
                )
            }

            (activity as HomeActivity?)!!.setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        mViewModel.selectedGame.observe(viewLifecycleOwner) { game ->
            mBinding.apply {
                container.txtNameGame.text = game.name
                container.txtYearGame.text = game.createdYear.toString()
                container.txtDescriptionGame.text = game.description
                glide.load(game.imageUrl).into(imgGame)
                toolbar.title = game.name
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.loadGame(gameUUID)
    }
}