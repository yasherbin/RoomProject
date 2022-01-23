package com.my.roomproject.ui.newphone

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.my.roomproject.data.model.Phone
import com.my.roomproject.databinding.FragmentNewPhoneBinding
import com.my.roomproject.viewmodel.PhonesViewModel
import android.graphics.Bitmap
import java.io.*
import android.provider.MediaStore
import com.my.roomproject.R
import com.my.roomproject.data.ImageStorage.Companion.saveToInternalStorage

class NewPhoneFragment : Fragment() {

    private lateinit var phonesViewModel: PhonesViewModel
    private var _binding: FragmentNewPhoneBinding? = null
    private val binding get() = _binding!!
    private var imageURI: String? = null
    private var bitmap: Bitmap?=null
    private var flag = false

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { binding.imageView.setImageURI(uri)
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.imageView.setImageBitmap(bitmap)
                flag=true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        phonesViewModel =
            ViewModelProvider(this).get(PhonesViewModel::class.java)

        _binding = FragmentNewPhoneBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val number = binding.editTextNumber.text.toString().trim()
            val s = "$name.jpg"
            if (flag)
            imageURI=saveToInternalStorage(activity?.applicationContext,bitmap!!, s)

            if (name.isNotEmpty() && number.isNotEmpty()) {
                if (imageURI.equals(null)) {
                    val phone = Phone(0, name, number, null)
                    phonesViewModel.addPhone(phone)
                } else {
                    val phone = Phone(0, name, number, imageURI)
                    phonesViewModel.addPhone(phone)
                }
                binding.editTextName.text.clear()
                binding.editTextNumber.text.clear()
                binding.imageView.setImageResource(R.drawable.ic_baseline_face_24)
            } else {
                val toastMessage = "Заполните все поля"
                Toast.makeText(activity,toastMessage,Toast.LENGTH_SHORT).show()
            }
        }
        binding.addPhotoButton.setOnClickListener { selectImageFromGallery() }
        return root
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}