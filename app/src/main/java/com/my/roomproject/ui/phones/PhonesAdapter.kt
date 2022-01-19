package com.my.roomproject.ui.phones

import com.my.roomproject.data.model.Phone
import com.my.roomproject.databinding.PhonesListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter (private val dataset: List<Phone>):
    RecyclerView.Adapter<PhonesAdapter.PhonesViewHolder>() {

    class PhonesViewHolder(
        var binding: PhonesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(phone: Phone) {
            binding.textViewName.text = phone.name
            binding.textViewNumber.text = phone.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhonesViewHolder(
            PhonesListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhonesViewHolder, position: Int) {
        val phone = dataset[position]
        holder.bind(phone)

        holder.itemView.setOnClickListener { view ->

            val direction = PhonesFragmentDirections.actionNavigationHomeToUpdateFragment(phone)
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}