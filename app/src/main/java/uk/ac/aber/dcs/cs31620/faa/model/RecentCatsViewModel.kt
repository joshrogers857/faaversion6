package uk.ac.aber.dcs.cs31620.faa.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.faa.datasource.FaaRepository
import java.time.LocalDateTime

class RecentCatsViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FaaRepository = FaaRepository(application)

    var recentCats: LiveData<List<Cat>> = loadRecentCats()
    private set

    private fun loadRecentCats(): LiveData<List<Cat>> {
        val endDate = LocalDateTime.now().plusDays(365)
        val pastDate = LocalDateTime.now().minusDays(30)

        return repository.getRecentCats(pastDate, endDate)
    }
}