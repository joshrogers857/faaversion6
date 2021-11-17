/**
 * The Repository class acting as a facade to the
 * underlying Room database.
 * @author Chris Loftus
 * @version 1
 */
package uk.ac.aber.dcs.cs31620.faa.datasource

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.faa.model.Cat
import uk.ac.aber.dcs.cs31620.faa.model.Gender
import java.time.LocalDateTime

class FaaRepository(application: Application) {
    private val catDao = FaaRoomDatabase.getDatabase(application)!!.catDao()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun insert(cat: Cat){
        coroutineScope.launch {
            catDao.insertSingleCat(cat)
        }
    }

    fun insertMultipleCats(cats: List<Cat>){
        coroutineScope.launch {
            catDao.insertMultipleCats(cats)
        }
    }

    fun getAllCats() = catDao.getAllCats()

    fun getCats(breed: String, gender: Gender, startDate: LocalDateTime, endDate: LocalDateTime)=
        catDao.getCats(breed, gender, startDate, endDate)

    fun getRecentCats(startDate: LocalDateTime, endDate: LocalDateTime)=
        catDao.getCatsAdmittedBetweenDates(startDate, endDate)

    fun getRecentCatsSync(startDate: LocalDateTime, endDate: LocalDateTime)=
        catDao.getCatsAdmittedBetweenDatesSync(startDate, endDate)
}