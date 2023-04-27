package com.test.anywhere.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.test.anywhere.R
import com.test.anywhere.TestApplication
import com.test.anywhere.databinding.ActivityDetailViewBinding
import com.test.anywhere.model.RelatedTopic

class DetailViewActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var binding: ActivityDetailViewBinding

    private var relatedTopic: RelatedTopic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_view)
        context = this@DetailViewActivity

        relatedTopic = TestApplication.currentTopic
        setupUI()
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvURL.setOnClickListener {
            openURL()
        }

        updateData()
    }

    private fun updateData() {
        if (relatedTopic == null) return

        val iconURL = "https://duckduckgo.com/" + (relatedTopic?.Icon?.URL ?: "")
        if (!relatedTopic?.Icon?.URL.isNullOrEmpty())
            Glide.with(binding.root)
                .load(iconURL)
                .thumbnail(Glide.with(binding.root).load(R.mipmap.placeholder))
                .fitCenter()
                .into(binding.ivRelatedTopic)

        binding.tvURL.text = relatedTopic?.FirstURL
        binding.tvTEXT.text = relatedTopic?.Text
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openURL() {
        val url = relatedTopic?.FirstURL
        if (url.isNullOrEmpty()) {
            print("URL is empty.")
        } else {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}