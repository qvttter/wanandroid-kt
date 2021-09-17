package com.easyway.ipu.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.li.mykotlinapp.bean.BannerBean
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.CancellationException
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn


/**
 * Created by luyao
 * on 2019/1/29 9:58
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {
    /**
     * 由v层持有，控制其loading的显示与隐藏
     */
    val isLoading = MutableStateFlow(false)
    /**
     * 由v层持有，显示信息
     */
    val message = MutableStateFlow("")


    val mException: MutableLiveData<Exception> = MutableLiveData()

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    suspend fun <T> runIO(block: suspend CoroutineScope.() -> T) :  T {
        return withContext(Dispatchers.IO) {
            block()
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }


    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun suspendingMerge(
        vararg params: Deferred<Any>,
        errorBlock: suspend CoroutineScope.() -> Unit
    ): MutableList<Any> {
        val list = mutableListOf<Any>()
        try {
            list.addAll(mutableListOf(params.map { it.await() }))
        } catch (e: Exception) {
            errorBlock(viewModelScope)
        }
        return list
    }

//    suspend fun executeResponse(
//        response: BaseResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
//        errorBlock: suspend CoroutineScope.() -> Unit
//    ) {
//        coroutineScope {
//            if (response.isSuccess) {
//                successBlock
//            } else {
//                errorBlock
//            }
//        }
//    }

    suspend fun executeResponse(
        response: Any, successBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (response == -1) errorBlock()
            else successBlock()
        }
    }

}