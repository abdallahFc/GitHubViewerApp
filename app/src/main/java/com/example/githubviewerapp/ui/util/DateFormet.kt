import java.text.SimpleDateFormat
import java.util.Locale

fun String.convertDateFormat(): String {
    if (isEmpty()) {
        return ""
    }
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
    return try {
        val date = inputFormatter.parse(this)
        outputFormatter.format(date)
    } catch (e: Exception) {
        ""
    }
}
