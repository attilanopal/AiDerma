package com.example.aiderma.api.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("insertId")
	val insertId: Int? = null
)
