package com.example.siet.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.siet.domain.models.UserOfList
import com.example.siet.data.network.ApiService

class UserPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, UserOfList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserOfList> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getUsers(page = page, perPage = params.loadSize)
            val users = response.body()?.data ?: emptyList()

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (users.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserOfList>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

