package com.example.ballapp.Account.Avatar

import android.app.appsearch.SearchResult.MatchInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.ballapp.Account.Team.MainActivityTeam
import com.example.ballapp.R
import com.example.ballapp.Utils.Animation
import com.example.ballapp.databinding.ActivityMainAvatarBinding
import com.example.ballapp.databinding.LayoutBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.jar.Manifest

class MainActivityAvatar : AppCompatActivity() {
    private lateinit var mainActivityAvatar : ActivityMainAvatarBinding
    private lateinit var layoutBottomSheetDialogBinding: LayoutBottomSheetDialogBinding
    private lateinit var imgUri :Uri
    private val userUID = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityAvatar = ActivityMainAvatarBinding.inflate(layoutInflater)
        setContentView(mainActivityAvatar.root)
        initEvents()
        initAvatarOb()
    }

    private fun initAvatarOb() {
    0
    }

    private fun initEvents() {
        back()
//        next()
        selectAvatar()
    }

    private fun selectAvatar() {
        mainActivityAvatar.progressPicture.setOnClickListener{
            showBottomSheetDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialog)
        layoutBottomSheetDialogBinding = LayoutBottomSheetDialogBinding.inflate(layoutInflater)
        dialog.setContentView(layoutBottomSheetDialogBinding.root)

        layoutBottomSheetDialogBinding.takePhoto.setOnClickListener{
            if(ActivityCompat.checkSelfPermission(applicationContext,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),100)
            }else{
                selectAvatarFromCamera()
                dialog.dismiss()
                Animation.animateSlideLeft(this)
            }
        }
        layoutBottomSheetDialogBinding.gallery.setOnClickListener{
            if(ActivityCompat.checkSelfPermission(applicationContext,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)

            }else{
                selectAvatarFromgallery()
                dialog.dismiss()
                Animation.animateSlideLeft(this)
            }
        }
        dialog.dismiss()
    }

    private fun selectAvatarFromgallery() {
        val intent = Intent()
        intent.type ="*image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,0)
    }

    private fun selectAvatarFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent,1)
        }else{
            Toast.makeText(this,"Camera not working",Toast.LENGTH_SHORT).show()
        }
    }


//    private fun next() {
//        mainActivityAvatar.next.setOnClickListener{
//            if(this::imgUri.isAbstract){
//                if(userUID != null){
//                    avatarmodel.saveAvatar(imgUri,userUID)
//                }
//                startActivity(Intent(this,MainActivityTeam::class.java))
//                Animation.animateSlideLeft(this)
//            }
//            else{
//                imgUri = Uri.parse("android.resource://$packageName/drawable/empty_avatar")
//                if(userUID != null){
//                    avatarmodel.saveAvatar(imgUri,userUID)
//                }
//                startActivity(Intent(this,MainActivityTeam::class.java))
//                Animation.animateSlideLeft(this)
//            }
//        }
//    }

    private fun back() {
        mainActivityAvatar.back.setOnClickListener{
            finish()
            Animation.animateSlideRight(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animation.animateSlideRight(this)
    }
}