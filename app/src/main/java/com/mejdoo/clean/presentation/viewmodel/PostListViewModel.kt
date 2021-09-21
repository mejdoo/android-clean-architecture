package com.mejdoo.clean.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.presentation.mapper.PostItemMapper
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.model.ResourceStatus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostListViewModel constructor(
        private val postListUseCase: PostListUseCase,
        private val mapper: PostItemMapper
) : ViewModel() {


    val postItemsLiveData = MutableLiveData<Resource<List<PostItem>>>()

    private val compositeDisposable = CompositeDisposable()


    fun getPostList() =
            compositeDisposable.add(postListUseCase.getPostList()
                    .doOnSubscribe {

                        postItemsLiveData.postValue(
                                Resource(
                                        status = ResourceStatus.LOADING,
                                        data = null,
                                        message = null
                                )
                        )

                    }
                    .subscribeOn(Schedulers.io())
                    .map { mapper.mapListFromDomain(it) }
                    .subscribe({


                        postItemsLiveData.postValue(
                                Resource(
                                        status = ResourceStatus.SUCCESS,
                                        data = it,
                                        message = null
                                )
                        )

                    }, {

                        postItemsLiveData.postValue(
                                Resource(
                                        status = ResourceStatus.ERROR,
                                        data = null,
                                        message = it.message
                                )
                        )

                    })
            )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}
