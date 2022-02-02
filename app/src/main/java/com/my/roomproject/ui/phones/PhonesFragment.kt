package com.my.roomproject.ui.phones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.roomproject.databinding.FragmentPhonesBinding
import com.my.roomproject.viewmodel.PhonesViewModel

class PhonesFragment : Fragment() {

    private lateinit var phonesViewModel: PhonesViewModel
    private var _binding: FragmentPhonesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhonesBinding.inflate(inflater, container, false)
        phonesViewModel =
            ViewModelProvider(requireActivity())[PhonesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        phonesViewModel.allPhones.observe(viewLifecycleOwner, { allPhones->
            binding.recyclerView.adapter=PhonesAdapter(allPhones)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}