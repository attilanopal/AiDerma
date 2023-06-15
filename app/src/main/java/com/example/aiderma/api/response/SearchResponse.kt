package com.example.aiderma.api.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("diseases")
	val diseases: List<DiseasesItem?>? = null
)

