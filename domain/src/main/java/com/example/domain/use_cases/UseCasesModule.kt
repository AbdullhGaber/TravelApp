package com.example.domain.use_cases

import com.example.domain.repositories.auth.AuthRepository
import com.example.domain.repositories.trip.TripNotificationScheduler
import com.example.domain.repositories.trip.TripRepository
import com.example.domain.repositories.user.UserRepository
import com.example.domain.use_cases.auth.AuthUseCases
import com.example.domain.use_cases.auth.LoginUseCase
import com.example.domain.use_cases.auth.RegisterUseCase
import com.example.domain.use_cases.trip.AddTripUseCase
import com.example.domain.use_cases.trip.GetTripUseCase
import com.example.domain.use_cases.trip.ScheduleTripNotificationUseCase
import com.example.domain.use_cases.trip.TripUseCases
import com.example.domain.use_cases.user.GetUserUseCase
import com.example.domain.use_cases.user.SaveImageUseCase
import com.example.domain.use_cases.user.SaveUserUseCase
import com.example.domain.use_cases.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {
    @Provides
    fun provideAuthUseCases(
        authRepository: AuthRepository
    ) : AuthUseCases {
        return AuthUseCases(
            registerUseCase = RegisterUseCase(authRepository),
            loginUseCase = LoginUseCase(authRepository)
        )
    }

    @Provides
    fun provideUserUseCases(
        userRepository: UserRepository
    ) : UserUseCases{
        return UserUseCases(
            getUserUseCase = GetUserUseCase(userRepository),
            saveUserUseCase = SaveUserUseCase(userRepository),
            saveImageUseCase = SaveImageUseCase(userRepository)
        )
    }

    @Provides
    fun provideTripUseCase(
        tripRepository: TripRepository,
        tripNotificationScheduler: TripNotificationScheduler
    ) : TripUseCases{
        return TripUseCases(
            getTripUseCase = GetTripUseCase(tripRepository),
            scheduleTripNotificationUseCase = ScheduleTripNotificationUseCase(tripNotificationScheduler),
            addTripUseCase = AddTripUseCase(tripRepository)
        )
    }
}