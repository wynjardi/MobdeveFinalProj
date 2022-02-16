package com.mobdeve.jardiniano.see.model

import java.io.Serializable

data class Comment(
    var id: String? = null,
    var uid: String? = null,
    var postId: String? = null,
    var content: String? = null
): Serializable
