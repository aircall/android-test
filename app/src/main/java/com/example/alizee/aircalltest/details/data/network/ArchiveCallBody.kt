package com.example.alizee.aircalltest.details.data.network

import com.google.gson.annotations.SerializedName

data class ArchiveCallBody(@SerializedName("is_archived") val isArchived: Boolean = true)