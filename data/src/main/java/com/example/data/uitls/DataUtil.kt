package com.example.data.uitls

import android.content.Context
import com.example.domain.entity.TripUserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Inject
@ApplicationContext
lateinit var mContext : Context

object DataUtil {
    var tripUser : TripUserEntity? = null
}