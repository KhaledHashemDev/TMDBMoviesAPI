package com.example.week4assignmentmovies.utils

class Constants {
    companion object{

        fun getGenre(id: Int): String {
            val genreMap = HashMap<Int,String>()
            genreMap[28] = "Action"
            genreMap[12] = "Adventure"
            genreMap[16] = "Animation"
            genreMap[35] = "Comedy"
            genreMap[80] = "Crime"
            genreMap[99] = "Documentary"
            genreMap[18] = "Drama"
            genreMap[10751] = "Family"
            genreMap[14] = "Fantasy"
            genreMap[36] = "History"
            genreMap[27] = "Horror"
            genreMap[10402] = "Music"
            genreMap[9648] = "Mystery"
            genreMap[10749] = "Romance"
            genreMap[878] = "Science Fiction"
            genreMap[10770] = "TV Movie"
            genreMap[53] = "Thriller"
            genreMap[10752] = "War"
            genreMap[37] = "Western"

            return genreMap[id]!!
        }
    }
}