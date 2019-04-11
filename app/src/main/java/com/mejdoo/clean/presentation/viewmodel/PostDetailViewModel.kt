package com.mejdoo.clean.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.presentation.mapper.PostDetailMapper
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.model.ResourceStatus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PostDetailViewModel constructor(
    private val postDetailUseCase: PostDetailUseCase,
    private val mapper: PostDetailMapper
) : ViewModel() {


    val postDetailLiveData = MutableLiveData<Resource<PostDetail>>()

    private val compositeDisposable = CompositeDisposable()


    fun getPostDetail(postId: Int, userId: Int) =
        compositeDisposable.add(postDetailUseCase.getPostDetails(postId, userId)
            .doOnSubscribe {

                postDetailLiveData.postValue(
                    Resource(
                        status = ResourceStatus.LOADING,
                        data = null,
                        message = null
                    )
                )

            }
            .subscribeOn(Schedulers.io())
            .map { mapper.mapFromDomain(it) }
            .subscribe({


                postDetailLiveData.postValue(
                    Resource(
                        status = ResourceStatus.SUCCESS,
                        data = it,
                        message = null
                    )
                )

            }, {

                postDetailLiveData.postValue(
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
