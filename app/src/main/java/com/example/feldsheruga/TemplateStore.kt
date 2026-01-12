package com.example.feldsheruga

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class TemplateStore(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun loadTemplates(): MutableList<TemplateCard> {
        val raw = prefs.getString(KEY_TEMPLATES, null) ?: return mutableListOf()
        val list = mutableListOf<TemplateCard>()
        val array = JSONArray(raw)
        for (index in 0 until array.length()) {
            val item = array.getJSONObject(index)
            list.add(
                TemplateCard(
                    id = item.getLong("id"),
                    title = item.getString("title"),
                    body = item.getString("body")
                )
            )
        }
        return list
    }

    fun saveTemplates(templates: List<TemplateCard>) {
        val array = JSONArray()
        templates.forEach { template ->
            val obj = JSONObject()
            obj.put("id", template.id)
            obj.put("title", template.title)
            obj.put("body", template.body)
            array.put(obj)
        }
        prefs.edit().putString(KEY_TEMPLATES, array.toString()).apply()
    }

    companion object {
        private const val PREFS_NAME = "template_prefs"
        private const val KEY_TEMPLATES = "templates"
    }
}

data class TemplateCard(
    val id: Long,
    var title: String,
    var body: String
)
