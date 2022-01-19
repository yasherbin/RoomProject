package com.my.roomproject.ui.newphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.my.roomproject.data.model.Phone
import com.my.roomproject.databinding.FragmentNewPhoneBinding
import com.my.roomproject.viewmodel.PhonesViewModel

class NewPhoneFragment : Fragment() {

    private lateinit var phonesViewModel: PhonesViewModel
    private var _binding: FragmentNewPhoneBinding? = null
    private val binding get() = _binding!!

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

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val phone = Phone(0,name,number)
                phonesViewModel.addPhone(phone)
                binding.editTextName.text.clear()
                binding.editTextNumber.text.clear()
            } else {
                val toastMessage = "Заполните все поля"
                Toast.makeText(activity,toastMessage,Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}