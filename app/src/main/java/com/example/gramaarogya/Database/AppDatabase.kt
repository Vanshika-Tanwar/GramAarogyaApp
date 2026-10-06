package com.example.gramaarogya.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//import com.example.koffi.Database.Cart.CartDao
//import com.example.koffi.Database.Cart.CartEntity
//import com.example.koffi.Database.Drink.DrinkDao
//import com.example.koffi.Database.Drink.DrinkData
//import com.example.koffi.Database.Drink.DrinkEntity
import com.example.gramaarogya.Database.User.UserDao
import com.example.gramaarogya.Database.User.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        //CartEntity::class,
        //DrinkEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    //abstract fun cartDao(): CartDao
    //abstract fun drinkDao(): DrinkDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gram_aarogya_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                seedDrinks(instance)

                instance
            }
        }

        private fun seedDrinks(database: AppDatabase) {

            CoroutineScope(Dispatchers.IO).launch {

                //val drinkDao = database.drinkDao()

//                if (drinkDao.getDrinkCount() == 0) {
//                    drinkDao.insertAllDrinks(DrinkData.drinks)
//                }
            }
        }
    }
}