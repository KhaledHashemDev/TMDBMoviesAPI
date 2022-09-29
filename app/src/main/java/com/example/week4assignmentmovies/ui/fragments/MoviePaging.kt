package com.example.week4assignmentmovies.ui.fragments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.week4assignmentmovies.network.ApiService
import com.example.week4assignmentmovies.model.movie.Result
import java.lang.Exception


/**
 * Paging still needs ot be implemented
 */
class MoviePaging(private val s:String, private val apiService: ApiService): PagingSource<Int,Result>() {


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)


        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
       val page= params.key?:1

        return try {

val data = apiService.getPopularMovies(s,page)

            LoadResult.Page(
                data =    data.body()?.results!!,
                prevKey = if(page==1)  null else page-1,
                nextKey = if(data.body()?.results?.isEmpty()!!) null else page+1
            )

        }catch (e:Exception){

            LoadResult.Error(e)

        }




    }


}