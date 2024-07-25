package com.trial.bucketlist

import android.content.Context
import androidx.room.Room
import com.trial.bucketlist.data.WishDatabase
import com.trial.bucketlist.data.Wishrespository

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        Wishrespository(database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(
            context,
            WishDatabase::class.java,
            "wishlist.db"
        ).build()
    }
}