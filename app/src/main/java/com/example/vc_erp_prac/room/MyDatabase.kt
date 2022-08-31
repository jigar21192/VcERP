package com.example.vc_erp_prac.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.vc_erp_prac.model.CityModel
import com.example.vc_erp_prac.model.ContactModel
import com.example.vc_erp_prac.model.DistrictModel
import com.example.vc_erp_prac.model.StateModel

@Database(entities = [DistrictModel::class, CityModel::class, StateModel::class, ContactModel::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {
        var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return instance
        }
    }

}

private val roomCallback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
    }
}