package com.example.siet.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.siet.data.network.ApiService
import com.example.siet.domain.models.UserOfList

class UserPagingSource(
    private val apiService: ApiService,
    private val perPage: Int
) : PagingSource<Int, UserOfList>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserOfList> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getUsers(page, perPage)
            val users = response.body()?.data ?: emptyList()

            val nextPage = if (page < response.body()?.totalPages ?: 0) page + 1 else null

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserOfList>): Int? {
        return state.anchorPosition
    }
}

