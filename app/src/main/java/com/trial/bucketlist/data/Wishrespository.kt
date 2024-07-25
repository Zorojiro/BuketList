package com.trial.bucketlist.data

import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow


class Wishrespository(private val NwishDao: NWishDao) {



    suspend fun addWish(wish: NWish){
        NwishDao.addWishN(wish)
    }

    fun getWishes(): Flow<List<NWish>> {
       return NwishDao.getAllWishesN()
    }

    fun getWishById(id: Long): Flow<NWish> {
        return NwishDao.getWishByIdN(id)
    }

    suspend fun updateWish(wish: NWish){
        NwishDao.updateWishN(wish)
    }

    suspend fun deleteWish(wish: NWish){
        NwishDao.deleteWishN(wish)
    }
}