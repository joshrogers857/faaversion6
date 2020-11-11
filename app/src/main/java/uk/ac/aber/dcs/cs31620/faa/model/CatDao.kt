/**
 * A DAO interface to allow access to the underlying Room
 * database
 * @author Chris Loftus
 * @version 1
 */
package uk.ac.aber.dcs.cs31620.faa.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSingleCat(cat: Cat)

    @Insert
    fun insertMultipleCats(catsList: List<Cat>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCat(cat: Cat)

    @Delete
    fun deleteCat(cat: Cat)

    @Query("DELETE FROM cats")
    fun deleteAll()

    @Query("SELECT * FROM cats WHERE id = :id")
    fun fetchCatByCatId(id: Int): Cat

    @Query("SELECT * FROM cats")
    fun getAllCats(): LiveData<List<Cat>>

    @Query(
        """SELECT * FROM cats WHERE breed = :breed AND 
              gender = :gender AND dob BETWEEN :startDate AND :endDate"""
    )
    fun getCats(
        breed: String, gender: Gender, startDate: LocalDateTime,
        endDate: LocalDateTime
    ): LiveData<List<Cat>>

    @Query(
        """SELECT * FROM cats WHERE admission_date 
           BETWEEN :startDate AND :endDate"""
    )
    fun getCatsAdmittedBetweenDates(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): LiveData<List<Cat>>

    @Query(
        """SELECT * FROM cats WHERE admission_date 
           BETWEEN :startDate AND :endDate"""
    )
    fun getCatsAdmittedBetweenDatesSync(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): List<Cat>

}