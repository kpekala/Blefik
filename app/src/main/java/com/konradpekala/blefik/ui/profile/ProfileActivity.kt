package com.konradpekala.blefik.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.repo.profile.ProfileRepositoryImpl
import com.konradpekala.blefik.data.repo.profile.RemoteProfileRepository
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.MainPresenter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*
import android.R.attr.data
import android.app.Activity
import com.squareup.picasso.Picasso


class ProfileActivity : BaseActivity(),ProfileMvp.View {

    private lateinit var mPresenter: ProfilePresenter<ProfileMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mPresenter = Injector.getProfilePresenter(this,this)

        initButtons()

        mPresenter.onCreate()
    }

    private fun initButtons() {
        buttonChangeNick.setOnClickListener {
            showChangeNameDialog()
        }
        buttonLogOut.setOnClickListener{
            mPresenter.onLogOutClick()
        }
        imageProfile.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                mPresenter.onNewImageChosen(resultUri.path!!)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun showChangeNameDialog(){
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name,layoutProfile,false)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Zmiana nicku")
            .setView(customView).create()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeNickClick(customView.fieldUserName.text.toString())
            dialog.hide()
        }

        dialog.show()

    }

    override fun openLoginActivity() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    override fun changeNick(nick: String) {
        textNickBig.text = nick
        textNickSmall.text = nick
    }

    override fun changeEmail(email: String) {
        textEmail.text = email
    }

    override fun changeProfileImage(path: String) {
        Picasso.get()
            .load("https://firebasestorage.googleapis.com/v0/b/blefik-b4f0a.appspot.com/o/profile_images%2FfiRs2BriLFZpnfEZqxcCNeG3RDt1?alt=media&token=427b4bf1-7538-4857-b831-6413faa20f69")
            .resize(100,100)
            .centerCrop()
            .into(imageProfile)

    }
}
