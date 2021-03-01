package com.nick_sib.refactoringdevelop.utils.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface INetworkStatus{
//    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
    fun waitInternet(): Observable<Boolean>
}