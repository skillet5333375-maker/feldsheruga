package com.example.feldsheruga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feldsheruga.databinding.ItemAlgorithmBinding

class AlgorithmAdapter(
    private val items: List<AlgorithmCard>,
    private val onOpen: (AlgorithmCard) -> Unit,
    private val onCopy: (AlgorithmCard) -> Unit
) : RecyclerView.Adapter<AlgorithmAdapter.AlgorithmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgorithmViewHolder {
        val binding = ItemAlgorithmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlgorithmViewHolder(binding, onOpen, onCopy)
    }

    override fun onBindViewHolder(holder: AlgorithmViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class AlgorithmViewHolder(
        private val binding: ItemAlgorithmBinding,
        private val onOpen: (AlgorithmCard) -> Unit,
        private val onCopy: (AlgorithmCard) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlgorithmCard) {
            binding.title.text = item.title
            binding.bodyPreview.text = item.body
            binding.root.setOnClickListener { onOpen(item) }
            binding.copyButton.setOnClickListener { onCopy(item) }
        }
    }
}
