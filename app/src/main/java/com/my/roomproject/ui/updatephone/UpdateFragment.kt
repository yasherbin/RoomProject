package com.my.roomproject.ui.updatephone

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.my.roomproject.R
import com.my.roomproject.data.ImageStorage.Companion.deleteImage
import com.my.roomproject.data.ImageStorage.Companion.loadImageFromStorage
import com.my.roomproject.data.ImageStorage.Companion.saveToInternalStorage
import com.my.roomproject.data.model.Phone
import com.my.roomproject.databinding.FragmentUpdateBinding
import com.my.roomproject.viewmodel.PhonesViewModel
import java.io.IOException

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var chosenPhone: Phone
    private lateinit var phonesViewModel: PhonesViewModel
    private var isImageChanged = false
    private var bitmap: Bitmap?=null

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { binding.imageView.setImageURI(uri)
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                binding.imageView.setImageBitmap(bitmap)
                isImageChanged=true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        phonesViewModel =
            ViewModelProvider(requireActivity())[PhonesViewModel::class.java]
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenPhone = args.phone
        binding.editTextName.setText(chosenPhone.name)
        binding.editTextNumber.setText(chosenPhone.number)
        if (chosenPhone.imageUri!=null)
        binding.imageView.setImageBitmap(loadImageFromStorage(chosenPhone.imageUri!!, chosenPhone.name))

        binding.saveButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val number = binding.editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                if (isImageChanged) {
                    val s = "$name.jpg"
                    val imageURI = saveToInternalStorage(activity?.applicationContext, bitmap!!, s)
                    val phone = Phone(chosenPhone.id, name, number, imageURI)
                    phonesViewModel.updatePhone(phone)
                } else {
                    val phone = Phone(chosenPhone.id, name, number, chosenPhone.imageUri)
                    phonesViewModel.updatePhone(phone)
                }
                binding.editTextName.text.clear()
                binding.editTextNumber.text.clear()

                view.findNavController().navigate(R.id.action_updateFragment_to_navigation_home)
            } else {
                val toastMessage = "Заполните все поля"
                Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnClickListener {
            delete()
            view.findNavController().navigate(R.id.action_updateFragment_to_navigation_home)
        }

        binding.addPhotoButton.setOnClickListener { selectImageFromGallery() }
    }

    private fun delete() {
        val name = binding.editTextName.text.toString().trim()
        val number = binding.editTextNumber.text.toString().trim()
        val phone = Phone(chosenPhone.id, name, number, chosenPhone.imageUri)
        phonesViewModel.deletePhone(phone)
        if(!chosenPhone.imageUri.equals(null))
        deleteImage(chosenPhone.imageUri!!, name)
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}