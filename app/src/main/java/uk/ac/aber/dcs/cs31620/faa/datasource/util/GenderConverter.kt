/**
 * A Room converter object for converting Gnder enums to Strings
 * and vice versa. Room will
 * use this automatically when a conversion is needed.
 * @author Chris Loftus
 * @version 1
 */
package uk.ac.aber.dcs.cs31620.faa.datasource.util

import androidx.room.TypeConverter
import uk.ac.aber.dcs.cs31620.faa.model.Gender

object GenderConverter {

    @TypeConverter
    @JvmStatic
    fun toString(gender: Gender) = gender.toString()

    @TypeConverter
    @JvmStatic
    fun toGender(gender: String) = Gender.valueOf(gender)
}
