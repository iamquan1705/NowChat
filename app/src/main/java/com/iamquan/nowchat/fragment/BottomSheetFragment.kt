package com.iamquan.nowchat.fragment

import android.R.attr
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.LayoutBottomsheetBinding
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.net.Uri
import android.R.attr.data
import java.io.InputStream
import android.R.attr.data
import com.iamquan.nowchat.databinding.FragmentProfileBinding


class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : LayoutBottomsheetBinding
    private var Resquet_code_camera = 123
    private var Resquet_code_library = 456
    lateinit var bmAvatar : Bitmap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutBottomsheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, Resquet_code_camera)
        }
        binding.btnLibrary.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, Resquet_code_library)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Resquet_code_camera && resultCode == RESULT_OK && data != null) {
            bmAvatar = data.extras?.get("data") as Bitmap
            dismiss()
        }
//        if (requestCode == Resquet_code_library && resultCode == RESULT_OK && data!=null) {
//            val uri = data.data
//            try {
//                val inputStream: InputStream = con.openInputStream(uri)
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//            }
//        }

    }

}


