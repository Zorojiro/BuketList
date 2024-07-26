package com.trial.bucketlist.data

import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow


class Wishrespository(private val NwishDao: NWishDao) {



     fun addWish(wish: NWish){
        NwishDao.addWishN(wish)
    }

    fun getWishes(): Flow<List<NWish>> {
       return NwishDao.getWishesN()
    }

    fun getWishById(id: String): Flow<NWish?> {
        return NwishDao.getWishById(id)
    }

    suspend fun updateWish(wish: NWish){
        NwishDao.updateWishByIdN(wish)
    }

     fun deleteWish(wish: NWish){
        NwishDao.deleteWishN(wish)
    }
}