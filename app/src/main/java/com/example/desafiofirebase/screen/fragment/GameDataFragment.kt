package com.example.desafiofirebase.screen.fragment

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.desafiofirebase.R
import com.example.desafiofirebase.databinding.FragmentGameDataBinding
import com.example.desafiofirebase.other.Constants.SELECTED_GAME_PUT_EXTRA
import com.example.desafiofirebase.provider.database.entities.Game
import com.example.desafiofirebase.screen.viewmodel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameDataFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private lateinit var mBinding: FragmentGameDataBinding
    private val mViewModel: GameViewModel by activityViewModels()
    private var mSelectedPhotoUri: Uri? = null
    private var gameUUID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            gameUUID = it.getString(SELECTED_GAME_PUT_EXTRA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSelectedPhotoUri = null
        mBinding = FragmentGameDataBinding.bind(view)

        mBinding.apply {
            btnSelectedPhoto.setOnClickListener {
                selectPhoto()
            }

            btnSave.setOnClickListener {
                saveGame()
            }

            if(gameUUID != null) {
                mViewModel.selectedGame.observe(viewLifecycleOwner) { game ->
                    txtName.setText(game.name)
                    txtCreatedYear.setText(game.createdYear.toString())
                    txtDescription.setText(game.description)

                    mSelectedPhotoUri = Uri.parse(game.imageUrl)
                    glide.load(game.imageUrl).into(imgPhotoGame)
                    btnSelectedPhoto.alpha = 0F
                }
            }
        }
    }

    private fun saveGame() {
        val name = mBinding.txtName.text.toString()
        val createdYear = mBinding.txtCreatedYear.text.toString().toInt()
        val description = mBinding.txtDescription.text.toString()

        if(gameUUID == null)
            mViewModel.createGame(name, createdYear, description, mSelectedPhotoUri)
        else
            mViewModel.updateGame(
                Game(gameUUID!!, name, createdYear, description, mSelectedPhotoUri.toString())
            )

        findNavController().popBackStack()
    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            mSelectedPhotoUri = data?.data
            val contextResolver = activity?.contentResolver

            try {
                mSelectedPhotoUri?.let { photo ->
                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            contextResolver,
                            photo
                        )
                    } else {
                        val source = ImageDecoder.createSource(contextResolver!!, photo)
                        ImageDecoder.decodeBitmap(source)
                    }

                    mBinding.apply {
                        imgPhotoGame.setImageBitmap(bitmap)
                        btnSelectedPhoto.alpha = 0F
                    }
                }
            } catch (e: Exception) {
                Log.i("GameDataFrag@ActResult", e.message, e)
            }
        }
    }
}
