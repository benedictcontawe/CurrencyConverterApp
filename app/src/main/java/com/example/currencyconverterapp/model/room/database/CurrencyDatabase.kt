package com.example.currencyconverterapp.model.room.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.currencyconverterapp.model.room.dao.CurrencyDAO
import com.example.currencyconverterapp.model.room.table.CurrencyTable

@Database(entities = [CurrencyTable::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDAO(): CurrencyDAO

    companion object {
        @Volatile private var instance : CurrencyDatabase? = null

        fun getInstance(context: Context) : CurrencyDatabase? {
            if (instance == null) {
                synchronized(CurrencyDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyDatabase::class.java, "custom_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback : RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                //Initialize Database if no database attached to the App
                super.onCreate(db)
                //PopulateDbAsyncTask(instance).execute()
                AsyncTask.execute {
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 0")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 1")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 2")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 3")))
                    //instance?.customDao()?.insert(ConvertList.toEntity(CustomModel(R.drawable.ic_launcher_foreground,"name 4")))
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                //Re-open Database if it has database attached to the App
                super.onOpen(db)
            }
        }

        class PopulateDbAsyncTask(db : CurrencyDatabase?) : AsyncTask<Unit,Unit,Unit>() {
            //private val customDAO = db?.customDao()

            override fun doInBackground(vararg param : Unit) {
                //customDAO?.insert(CustomEntity("name 0"))
                //customDAO?.insert(CustomEntity("name 1"))
                //customDAO?.insert(CustomEntity("name 2"))
                //customDAO?.insert(CustomEntity("name 3"))
                //customDAO?.insert(CustomEntity("name 4"))
            }
        }
    }
}