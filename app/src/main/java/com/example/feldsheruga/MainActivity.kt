package com.example.feldsheruga

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feldsheruga.databinding.ActivityMainBinding
import com.example.feldsheruga.databinding.DialogTemplateBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var store: TemplateStore
    private lateinit var adapter: TemplateAdapter
    private val templates = mutableListOf<TemplateCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        store = TemplateStore(this)
        templates.addAll(store.loadTemplates())

        adapter = TemplateAdapter(
            templates,
            onEdit = { template -> openEditor(template) },
            onCopy = { template -> copyTemplate(template) }
        )

        binding.templateList.layoutManager = LinearLayoutManager(this)
        binding.templateList.adapter = adapter

        binding.addTemplateButton.setOnClickListener {
            openEditor(null)
        }

        refreshEmptyState()
    }

    private fun openEditor(existing: TemplateCard?) {
        val dialogBinding = DialogTemplateBinding.inflate(LayoutInflater.from(this))
        dialogBinding.templateTitleInput.setText(existing?.title.orEmpty())
        dialogBinding.templateBodyInput.setText(existing?.body.orEmpty())

        AlertDialog.Builder(this)
            .setTitle(if (existing == null) getString(R.string.add_template) else getString(R.string.edit))
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.save) { _, _ ->
                val title = dialogBinding.templateTitleInput.text.toString().trim()
                val body = dialogBinding.templateBodyInput.text.toString().trim()
                if (existing == null) {
                    val newCard = TemplateCard(
                        id = System.currentTimeMillis(),
                        title = if (title.isEmpty()) getString(R.string.untitled) else title,
                        body = body
                    )
                    templates.add(0, newCard)
                } else {
                    existing.title = if (title.isEmpty()) getString(R.string.untitled) else title
                    existing.body = body
                }
                store.saveTemplates(templates)
                adapter.updateAll(templates)
                refreshEmptyState()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun copyTemplate(template: TemplateCard) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(template.title, template.body)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, getString(R.string.copied_toast), Toast.LENGTH_SHORT).show()
    }

    private fun refreshEmptyState() {
        binding.emptyState.visibility = if (templates.isEmpty()) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
        }
    }
}
