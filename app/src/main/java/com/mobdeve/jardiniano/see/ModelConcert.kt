package com.mobdeve.jardiniano.see

class ModelConcert {

    var uid:String = ""
    var id:String = ""
    var concertArtist:String = ""
    var concertName:String = ""
    var categoryId:String = ""
    var imageUrl:String = ""
    var timestamp:Long = 0

    //empty constuctor
    constructor()

    constructor(
        uid: String,
        id: String,
        concertArtist: String,
        concertName: String,
        categoryId: String,
        imageUrl: String,
        timestamp: Long
    ) {
        this.uid = uid
        this.id = id
        this.concertArtist = concertArtist
        this.concertName = concertName
        this.categoryId = categoryId
        this.imageUrl = imageUrl
        this.timestamp = timestamp
    }

}