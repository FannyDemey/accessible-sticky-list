package com.techethic.a11ysticky.model

sealed class MovieListItem(val rate : Int) : Comparable<MovieListItem>{
    data class Movie(val title : String, val movieRate : Int): MovieListItem(movieRate) {
        override fun compareTo(other: MovieListItem): Int {
            return when{
                other.rate < this.rate -> -1
                other.rate > this.rate -> 1
                else -> {
                    when(other){
                        is Movie -> 1
                        is StickyHeader -> -1
                    }
                }
            }
        }
    }

    data class StickyHeader(val stickyRate : Int) : MovieListItem(stickyRate) {
        override fun compareTo(other: MovieListItem): Int {
            return when{
                other.rate < this.rate -> -1
                other.rate > this.rate -> 1
                else -> {
                    when(other){
                        is Movie -> -1
                        is StickyHeader -> 1
                    }
                }
            }

        }
    }

}