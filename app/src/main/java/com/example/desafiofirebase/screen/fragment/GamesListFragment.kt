package com.example.desafiofirebase.screen.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.desafiofirebase.R
import com.example.desafiofirebase.adapter.GamesListAdapter
import com.example.desafiofirebase.databinding.FragmentGamesListBinding
import com.example.desafiofirebase.helper.OnGameClickListener
import com.example.desafiofirebase.other.Constants.SELECTED_GAME_PUT_EXTRA
import com.example.desafiofirebase.provider.IAuthProvider
import com.example.desafiofirebase.screen.activity.AuthActivity
import com.example.desafiofirebase.screen.activity.HomeActivity
import com.example.desafiofirebase.screen.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GamesListFragment : Fragment(), OnGameClickListener {
    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var appAuth: IAuthProvider

    private lateinit var mActivity: HomeActivity
    private lateinit var mBinding: FragmentGamesListBinding
    private lateinit var mAdapter: GamesListAdapter

    private val mViewModel: GameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = GamesListAdapter(glide!!, this)
        mViewModel.loadGames()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as HomeActivity
        mBinding = FragmentGamesListBinding.bind(view)

        mBinding.apply {
            rcGames.adapter = mAdapter
            contentSearch.btnCloseApp.setOnClickListener {
                signOut()
            }
        }

        mViewModel.games.observe(viewLifecycleOwner) {
            mAdapter.listGames = it
        }
    }

    private fun signOut() {
        appAuth.signOut()
        startActivity(Intent(activity, AuthActivity::class.java))
        activity?.finish()
    }

    override fun onGameClick(uuid: String) {
        val bundle = Bundle()
        bundle.putString(SELECTED_GAME_PUT_EXTRA, uuid)

        findNavController().navigate(R.id.action_gamesListFragment_to_gameDetailsFragment, bundle)
    }

    override fun onStart() {
        super.onStart()
        mViewModel.clearSelectedGame()
        mActivity.showAddFab()
    }

    override fun onPause() {
        super.onPause()
        mActivity.hideAddFab()
    }
}