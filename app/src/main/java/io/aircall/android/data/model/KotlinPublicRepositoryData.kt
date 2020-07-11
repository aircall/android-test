package io.aircall.android.data.model

data class KotlinPublicRepositoryData(val name: String,
                                      val starsCount: Int,
                                      val watchersCount: Int,
                                      val forkCount: Int,
                                      val prCount: Int,
                                      val issues: List<IssueData>
                                )