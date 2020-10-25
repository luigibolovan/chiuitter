package ro.upt.ac.chiuitter.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ro.upt.ac.chiuitter.domain.Chiuit


@Dao
interface ChiuitDao {

    @Query("SELECT * FROM chiuits")
    fun getAll(): List<ChiuitEntity>


    @Insert
    fun insert(chiuit: ChiuitEntity)


    @Delete
    fun delete(chiuit: ChiuitEntity)

}