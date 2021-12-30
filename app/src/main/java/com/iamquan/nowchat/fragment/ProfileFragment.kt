package com.iamquan.nowchat.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.activity.LoginActivity
import com.iamquan.nowchat.databinding.FragmentProfileBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils
import android.R
import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.iamquan.nowchat.databinding.DialogChangePasswordBinding
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mAuth: FirebaseAuth? = null
    private var passwordUCurrent: String = ""
    private var idCurrent: String = ""
    private var mUploadTask: StorageTask<*>? = null
    private lateinit var mAvtUri: Uri
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        getProfileUser()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.imgImageProfile.setOnClickListener {
//            BottomSheetFragment().show(childFragmentManager, "ABC")
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(intent, 123)

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 123)
        }
        binding.tvEditProfile.setOnClickListener {
            enableEdit(true)
        }
        binding.btnCanelEdit.setOnClickListener {
            enableEdit(false)
        }
        binding.tvChangePassword.setOnClickListener {
            val dialog = Dialog(requireContext())
            var bd = DialogChangePasswordBinding.inflate(layoutInflater)
            dialog.setContentView(bd.root)
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            bd.btnSavePasswordDialog.setOnClickListener {
                changePassword(
                    bd.edtOldPassword.text.toString(),
                    bd.edtNewPasswordDialog.text.toString(),
                    bd.edtConfirmPasswordDialog.text.toString()
                )
                dialog.cancel()
            }
            bd.btnCanelDialog.setOnClickListener {
                dialog.cancel()
            }
        }

        binding.tvLogOut.setOnClickListener {
            mAuth?.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.btnSaveEdit.setOnClickListener {
            saveEdit()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            mAvtUri = data.data!!
            Glide.with(requireContext()).load(mAvtUri).into(binding.imgAvtProfile)
        }
    }

    fun getProfileUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        idCurrent = currentUser?.uid.toString()
        if (currentUser != null) {
            val reference =
                FirebaseDatabase.getInstance().getReference(Utils.USERS).child(idCurrent)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    binding.edtEmailProfile.setText(user?.email)
                    binding.edtNameProfile.setText(user?.username)
                    binding.edtBirthDayProfile.setText(user?.birthday)
                    binding.edtSexProfile.setText(user?.sex)
                    binding.edtPhoneProfile.setText(user?.phone)
                    passwordUCurrent = user?.password.toString()
                    if (user?.status == 1) {
                        binding.swStatus.isChecked = true
                    } else if (user?.status == 0) {
                        binding.swStatus.isChecked = false
                    }
                    if (!user?.avatar.equals(Utils.DEFAULT)) {
                        Glide.with(requireContext()).load(user?.avatar).into(binding.imgAvtProfile)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun enableEdit(boolean: Boolean) {
        binding.edtNameProfile.isEnabled = boolean
        binding.edtBirthDayProfile.isEnabled = boolean
        binding.edtPhoneProfile.isEnabled = boolean
        binding.edtSexProfile.isEnabled = boolean
        binding.imgImageProfile.isVisible = boolean
        binding.btnCanelEdit.isVisible = boolean
        binding.btnSaveEdit.isVisible = boolean
    }

    fun changePassword(
        edtOldPassword: String,
        edtPasswordDialog: String,
        edtConfirmPasswordDialog: String
    ) {
        if (!edtOldPassword.equals(passwordUCurrent)) {
            Toast.makeText(requireContext(), "Old password is Wrong", Toast.LENGTH_LONG)
                .show()
        } else if (edtPasswordDialog.length < 6) {
            Toast.makeText(
                requireContext(),
                "Password must be than 6 characters",
                Toast.LENGTH_LONG
            ).show()
        } else if (edtConfirmPasswordDialog.isEmpty() || edtConfirmPasswordDialog != edtPasswordDialog
        ) {
            Toast.makeText(requireContext(), "Password not matching", Toast.LENGTH_LONG).show()
        } else {
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap.put(Utils.PASSWORD, edtPasswordDialog)
            FirebaseDatabase.getInstance().getReference(Utils.USERS).child(idCurrent)
                .updateChildren(hashMap).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Change password successed",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Change password failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = requireContext().getContentResolver()
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun updateAvatar() {
        if (mAvtUri != null) {
            var fileName = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")
            ref.putFile(mAvtUri).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    val hashMap: HashMap<String, Any> = HashMap()
                    hashMap.put(Utils.AVATAR, it.toString())
                    FirebaseDatabase.getInstance().getReference(Utils.USERS).child(idCurrent)
                        .updateChildren(hashMap)
                }
            }
        }
    }

    fun saveEdit() {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put(Utils.USER_NAME, binding.edtNameProfile.text.toString())
        hashMap.put(Utils.SEX, binding.edtSexProfile.text.toString())
        hashMap.put(Utils.BIRTHDAY, binding.edtBirthDayProfile.text.toString())
        hashMap.put(Utils.PHONE, binding.edtPhoneProfile.text.toString())
        updateAvatar()
        FirebaseDatabase.getInstance().getReference(Utils.USERS).child(idCurrent)
            .updateChildren(hashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Change successed",
                        Toast.LENGTH_SHORT
                    ).show()
                    enableEdit(false)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Change failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}