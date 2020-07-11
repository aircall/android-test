package io.aircall.android.domain

import io.aircall.android.data.model.KotlinPublicRepositoryData
import io.aircall.android.data.model.UserData
import io.aircall.android.domain.date.DateHelper
import io.aircall.android.domain.date.DateHelper.toDateString
import io.aircall.android.domain.model.IssuesByWeek
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.model.User
import java.util.*

fun UserData.toDomainObject() = User(name)

fun List<KotlinPublicRepositoryData>.toDomainObject() =
    map { data ->
        val calendar = DateHelper.getCalendar()
        calendar.add(Calendar.YEAR, -1)
        val minDate = calendar.time
        val issuesByWeek = data.issues
            .filter { issue ->
                issue.dateTime.after(minDate)
            }
            .sortedByDescending { it.dateTime }
            .groupBy {
                val issueCalendar = DateHelper.getCalendar()
                issueCalendar.time = it.dateTime
                issueCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                val startDay = issueCalendar.time.toDateString()
                issueCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                val endDay = issueCalendar.time.toDateString()
                Pair(startDay, endDay)
            }
            .map {
                IssuesByWeek(it.key.first, it.key.second, it.value.size.toString())
            }

        KotlinPublicRepository(
            data.name,
            data.starsCount.toString(),
            data.watchersCount.toString(),
            data.forkCount.toString(),
            data.prCount.toString(),
            issuesByWeek
        )
    }