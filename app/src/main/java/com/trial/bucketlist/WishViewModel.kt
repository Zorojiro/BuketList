package com.trial.bucketlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.trial.bucketlist.data.Wish
import com.trial.bucketlist.data.Wishrespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class WishViewModel(private val wishRepository: Wishrespository = Graph.wishRepository ): ViewModel()
{
    var wishTitleState by  mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
        checkAuthState()
    }

    fun onWishTileChange(newString: String){
        wishTitleState = newString
    }

    fun onWishDescriptionChange(newString: String){
        wishDescriptionState = newString
    }

    lateinit var getAllWishes: Flow<List<Wish>>



    fun addWish(wish:Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addWish(wish)
        }
    }

    fun updateWish(wish:Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish)
        }
    }

    fun getWishById(id: Long): Flow<Wish>{
        return wishRepository.getWishById(id)
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){
            wishRepository.deleteWish(wish)
        }
    }

    fun checkAuthState(){
        if(auth.currentUser == null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something Went Wrong")
                }
            }
    }

    fun signup(email:String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something Went Wrong")
                }
            }
    }

    fun signout(){
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState{
    object Unauthenticated: AuthState()
    object Authenticated: AuthState()
    object Loading : AuthState()
    data class Error(val message: String): AuthState()
}