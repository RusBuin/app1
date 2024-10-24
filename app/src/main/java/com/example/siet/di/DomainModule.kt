//package com.example.siet.di
//
//import com.example.siet.domain.GetDataUseCase
//import com.example.siet.domain.SaveUserUseCase
//import com.example.siet.domain.repository.UserRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//class DomainModule {
//
//    @Provides
//    fun provideGetDataUseCase(userRepository: UserRepository): GetDataUseCase {
//        return GetDataUseCase(userRepository)
//    }
//
//    @Provides
//    fun provideSaveUserUseCase(userRepository: UserRepository): SaveUserUseCase {
//        return SaveUserUseCase(userRepository)
//    }
//}
