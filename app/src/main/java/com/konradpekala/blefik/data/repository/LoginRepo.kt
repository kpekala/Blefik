package com.konradpekala.blefik.data.repository

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(private val mAuth: Auth,
                                         private val mDb: FirebaseDatabase,
                                         private val mPrefs: SharedPrefs) {

    fun signUp(email: String, password: String): Single<String> {
        return mAuth.signUp(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun signIn(email: String, password: String): Single<String> {
        return mAuth.signIn(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun createUser(email: String,password: String,id: String,nick: String): Completable{
        return mDb.createUser(email, password, id,nick)
            .doOnComplete { mPrefs.setUserNick(nick)}
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun saveUserLocally(nick: String, email: String){
        mPrefs.setUserNick(nick)
        mPrefs.setUserEmail(email)
        mPrefs.setIsUserLoggedIn(true)
    }


    fun getUserNick(id: String): Single<String>{
        return mDb.getUserNick(id)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

}