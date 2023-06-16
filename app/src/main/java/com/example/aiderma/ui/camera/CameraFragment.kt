package com.example.aiderma.ui.camera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.aiderma.LoginActivity
import com.example.aiderma.databinding.FragmentCameraBinding
import com.example.aiderma.helper.SessionPref
import com.example.aiderma.helper.reduceFileImage
import com.example.aiderma.helper.uriToFile
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CameraFragment : Fragment() {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CameraViewModel
    private lateinit var imgProfile: ImageView
    private lateinit var mProfileUri: Uri
    private var imgFile: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgProfile = binding.ivPreview
        viewModel = ViewModelProvider(requireActivity())[CameraViewModel::class.java]

        val pref = SessionPref(requireContext())
        val token = "${pref.getToken()}"

        binding.btnUpload.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.btnScan.setOnClickListener {
            if(imgFile != null) {
                val file = reduceFileImage(imgFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())

                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                viewModel.sendImage(token,imageMultipart)
            } else {
                Toast.makeText(requireContext(), "Harap masukkan gambar", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.uploadImg.observe(viewLifecycleOwner){
            if (it.insertId != null){
                Toast.makeText(requireContext(), "Gambar berhasil diupload", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            pref.clearSession()
            val intent = Intent(requireContext(),LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = fileUri
                imgProfile.setImageURI(fileUri)
                imgFile = uriToFile(mProfileUri, requireActivity())
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

}