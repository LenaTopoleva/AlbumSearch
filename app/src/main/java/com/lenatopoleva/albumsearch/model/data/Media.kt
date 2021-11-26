package com.lenatopoleva.albumsearch.model.data

data class Media
    (val wrapperType : String,
     val artistId : Int,
     val collectionId : Int,
     val artistName : String,
     val collectionName : String,
     val artistViewUrl : String,
     val artworkUrl60 : String,
     val artworkUrl100 : String,
     val collectionPrice : Double,
     val trackCount : Int,
     val country : String,
     val currency : String,
     val releaseDate : String,
     val primaryGenreName : String,
     val copyright : String?,

     val trackName: String?,
     val trackNumber: Int?,
     val trackTimeMillis: Long?,
     val trackPrice: Double?
     )