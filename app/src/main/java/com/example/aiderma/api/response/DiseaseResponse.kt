package com.example.aiderma.api.response

import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("diseases")
	val diseases: List<DiseasesItem>
)

data class DiseasesItem(

	@field:SerializedName("namaPenyakit")
	val namaPenyakit: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("gambar")
	val gambar: Gambar? = null
)

data class Gambar(

	@field:SerializedName("data")
	val data: List<Int>,

	@field:SerializedName("type")
	val type: String? = null
)
