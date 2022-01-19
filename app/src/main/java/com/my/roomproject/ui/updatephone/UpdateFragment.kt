package com.my.roomproject.ui.updatephone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.my.roomproject.R
import com.my.roomproject.data.model.Phone
import com.my.roomproject.databinding.FragmentUpdateBinding
import com.my.roomproject.viewmodel.PhonesViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var chosenPhone: Phone
    private lateinit var phonesViewModel: PhonesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        phonesViewModel =
            ViewModelProvider(this).get(PhonesViewModel::class.java)
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chosenPhone = args.phone

        binding.editTextName.setText(chosenPhone.name)
        binding.editTextNumber.setText(chosenPhone.number)

        binding.saveButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val number = binding.editTextNumber.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val phone = Phone(chosenPhone.id,name,number)
                phonesViewModel.updatePhone(phone)
                binding.editTextName.text.clear()
                binding.editTextNumber.text.clear()

                view.findNavController().navigate(R.id.action_updateFragment_to_navigation_home)
            } else {
                val toastMessage = "Заполните все поля"
                Toast.makeText(activity,toastMessage, Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val number = binding.editTextNumber.text.toString().trim()
            val phone = Phone(chosenPhone.id,name,number)
            phonesViewModel.deletePhone(phone)
            view.findNavController().navigate(R.id.action_updateFragment_to_navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}