package com.akashgarg.sample.model.post

import java.io.Serializable

data class PostResponseModel(
    val id: Int? = 0,
    val title: String? = "",
    val body: String? = "",
    val userId: Int? = 0
) : Serializable
