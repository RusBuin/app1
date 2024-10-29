package com.example.siet.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.siet.R
import com.example.siet.domain.models.UserOfList
import com.example.siet.presentation.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowsListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: AdapterList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list)

        adapter = AdapterList(
            context = this,
            onFollowClick = { user ->
                viewModel.followUser(user)
            },
            onUnfollowClick = { user ->
                viewModel.unfollowUser(user)
            }
        )

        val recyclerViewUsers: RecyclerView = findViewById(R.id.recyclerViewUsers)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = adapter

        lifecycleScope.launch {
            viewModel.users.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        viewModel.loadUsers()
    }
}
