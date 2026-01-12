package com.example.feldsheruga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feldsheruga.databinding.ItemTemplateBinding

class TemplateAdapter(
    private val templates: MutableList<TemplateCard>,
    private val onEdit: (TemplateCard) -> Unit,
    private val onCopy: (TemplateCard) -> Unit
) : RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        val binding = ItemTemplateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TemplateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        holder.bind(templates[position])
    }

    override fun getItemCount(): Int = templates.size

    fun updateAll(newList: List<TemplateCard>) {
        templates.clear()
        templates.addAll(newList)
        notifyDataSetChanged()
    }

    inner class TemplateViewHolder(private val binding: ItemTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: TemplateCard) {
            binding.templateTitle.text = card.title
            binding.templateBody.text = card.body
            binding.editButton.setOnClickListener { onEdit(card) }
            binding.copyButton.setOnClickListener { onCopy(card) }
        }
    }
}
