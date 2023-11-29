package com.example.mobdev21_night_at_the_museum.domain.model

import com.google.firebase.Timestamp
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class Exhibition(

    var name: String? = null,

    var museumName: String? = null,

    var place: String? = null,

    var thumbnail: String? = null,

    var museumThumb: String? = null,

    var startTime: Timestamp? = null,

    var endTime: Timestamp? = null,

    var ticketLink: String? = null

) : MuseumModel() {
    fun getMuseumInfo(): String {
        return "$museumName\n$place"
    }

    fun getExhibitionDate(): String {
        if (startTime == null || endTime == null) return ""
        return "Từ ${timeStampToString(startTime!!)} đến ${timeStampToString(endTime!!)}"
    }

    fun getExhibitionTitle(): String {
        return if (startTime!! > Timestamp.now()) {
            getAppString(R.string.exhibition_opening_soon)
        } else {
            getAppString(R.string.current_exhibition)
        }
    }

    private fun timeStampToString(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}
