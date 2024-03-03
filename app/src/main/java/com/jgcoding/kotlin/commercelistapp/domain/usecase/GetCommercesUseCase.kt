package com.jgcoding.kotlin.commercelistapp.domain.usecase

import com.jgcoding.kotlin.commercelistapp.domain.Repository
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import javax.inject.Inject

class GetCommercesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getCommercesApi()
//    {
//        val commerces = repository.getCommercesApi()

//        return if (commerces.isNotEmpty()) {
//            repository.deleteAllCommercesDatabase()
////            repository.insertAllCommercesInDataBase(commerces)
//            commerces
//        }
//        else {
////            repository.getCommercesDatabase()
//            repository.getCommercesApi()
//        }
//    }
}