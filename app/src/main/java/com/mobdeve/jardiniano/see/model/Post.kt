package com.mobdeve.jardiniano.see.model

import java.io.Serializable

data class Post(
    var id: String? = null,
    var uid: String? = null,
    var title: String? = null,
    var description: String? = null
) : Serializable
