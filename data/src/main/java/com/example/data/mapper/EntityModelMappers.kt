package com.example.data.mapper

import com.example.data.model.ImageDataModel
import com.example.data.model.ImageResponseModel
import com.example.data.model.NoteModel
import com.example.data.model.TripModel
import com.example.data.model.TripUserModel
import com.example.domain.entity.ImageDataEntity
import com.example.domain.entity.ImageResponseEntity
import com.example.domain.entity.NoteEntity
import com.example.domain.entity.TripEntity
import com.example.domain.entity.TripUserEntity

fun ImageResponseEntity.toModel() : ImageResponseModel{
    return ImageResponseModel(
        status = this.status,
        success = this.success,
        data = this.data?.toModel()
    )
}

fun ImageResponseModel.toEntity() : ImageResponseEntity{
    return ImageResponseEntity(
        status = this.status,
        success = this.success,
        data = this.data?.toEntity()
    )
}

fun ImageDataEntity.toModel() : ImageDataModel{
    return ImageDataModel(link = link)
}

fun ImageDataModel.toEntity() : ImageDataEntity{
    return ImageDataEntity(link = link)
}

fun TripUserEntity.toModel() : TripUserModel{
    return TripUserModel(uid, email, name, phoneNumber, imageURL, imagePath)
}

fun TripUserModel.toEntity() : TripUserEntity{
    return TripUserEntity(uid, email, name, phoneNumber, imageURL, imagePath)
}

fun TripEntity.toModel() : TripModel{
    return TripModel(
       id ?:"",
       uid,
       notes.map{it.text},
       status,
       name,
       startDestination,
       endDestination,
       date,
       time,
       returnDate,
       returnTime,
       type,
       hasFirstTripTimeCome,
       hasSecondTripTimeCome
    )
}

fun TripModel.toEntity() : TripEntity{
    return TripEntity(
       id,
       uid,
       notes.map{NoteEntity(text = it)},
       status,
       name,
       startDestination,
       endDestination,
       date,
       time,
       returnDate,
       returnTime,
       type,
       hasFirstTripTimeCome,
       hasSecondTripTimeCome
    )
}

fun NoteEntity.toModel() : NoteModel{
    return NoteModel(
        text = text,
        uid = uid,
        tripId = tripId,
        addedTime = addedTime
    )
}

fun NoteModel.toEntity() : NoteEntity{
    return NoteEntity(
        text = text,
        uid = uid,
        tripId = tripId,
        addedTime = addedTime
    )
}