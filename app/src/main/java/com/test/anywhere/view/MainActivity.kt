package com.test.anywhere.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.anywhere.R
import com.test.anywhere.TestApplication
import com.test.anywhere.databinding.ActivityMainBinding
import com.test.anywhere.model.RelatedTopic
import com.test.anywhere.view.adapter.ItemClickListener
import com.test.anywhere.view.adapter.RelatedTopicAdapter
import com.test.anywhere.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Context
    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: RelatedTopicAdapter
    private lateinit var searchResultList: ArrayList<RelatedTopic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        context = this@MainActivity

        this.searchResultList = ArrayList()

        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.progressBar.visibility = View.VISIBLE
        mainViewModel.getResult()?.observe(this) {
            binding.progressBar.visibility = View.GONE

            this.searchResultList.addAll(it.RelatedTopics)
            this.filter("")
        }
    }

    private fun setupUI() {
        this.adapter = RelatedTopicAdapter(this.searchResultList, object : ItemClickListener {
            override fun onItemClick(position: Int) {
                showDetail(position)
            }
        })

        binding.searchListView.layoutManager = LinearLayoutManager(this)
        binding.searchListView.adapter = this.adapter

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(text: String) {
        this.adapter.filter.filter(text)
    }

    private fun showDetail(position: Int) {
        TestApplication.currentTopic = if (this.adapter.filteredList.isEmpty()) {
            this.searchResultList[position]
        } else {
            this.adapter.filteredList[position]
        }

        val intent = Intent(this, DetailViewActivity::class.java)
        startActivity(intent)
    }
}