package com.li.mykotlinapp.util

import io.reactivex.android.schedulers.AndroidSchedulers
import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.BasePageResponse
import com.li.mykotlinapp.base.PageDataBean
import com.li.mykotlinapp.base.BaseObjectResponse
import com.li.mykotlinapp.base.BaseResponse
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * Author:  Ivan
 * Date:    Jan 02, 2018
 */
object RxUtil {
    private const val TAG = "RxUtil"
    fun <T> trans_io_main(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> trans_io_io(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
        }
    }

    fun trans_complete_io_main(): CompletableTransformer {
        return CompletableTransformer { upstream: Completable ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> trans_maybe_io_main(): MaybeTransformer<T, T> {
        return MaybeTransformer { upstream: Maybe<T> ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> getData(response: BaseListResponse<T>): Observable<List<T>> {
        return Observable.create { emitter: ObservableEmitter<List<T>> ->
            if (response.errorCode == 0) {
                emitter.onNext(response.data)
                emitter.onComplete()
            } else {
                emitter.onError(Throwable(response.errorMsg))
            }
        }
    }

    fun <T> getPage(response: BasePageResponse<T>): Observable<PageDataBean<T>> {
        return Observable.create { emitter: ObservableEmitter<PageDataBean<T>> ->
            if (response.errorCode == 0) {
                emitter.onNext(response.data)
                emitter.onComplete()
            } else {
                emitter.onError(Throwable(response.errorMsg))
            }
        }
    }

    //    public static <T> Observable<T> getDataAt0(BaseResponse<T> response) {
    //        return Observable.create(emitter -> {
    //            if (response.isSuccess()) {
    //                List<T> data = response.getData();
    //                if (data != null && data.size() > 0) {
    //                    emitter.onNext(data.get(0));
    //                    emitter.onComplete();
    //                } else {
    //                    emitter.onError(new Throwable("暂无数据"));
    //                }
    //            } else {
    //                emitter.onError(new Throwable(response.getMessage()));
    //            }
    //        });
    //    }
    //
    fun <T> getObject(response: BaseObjectResponse<T>): Observable<T> {
        return Observable.create { emitter: ObservableEmitter<T> ->
            if (response.errorCode == 0) {
                val data = response.data
                if (data != null) {
                    emitter.onNext(data)
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable("暂无数据"))
                }
            } else {
                if (response.errorMsg != null) {
                    emitter.onError(Throwable(response.errorMsg))
                } else {
                    emitter.onError(Throwable("未知错误"))
                }
            }
        }
    }

    //
    fun <T> getMessage(response: BaseResponse<T>): Observable<String?> {
        return Observable.create { emitter: ObservableEmitter<String?> ->
            if (response.errorCode == 0) {
                emitter.onComplete()
            } else {
                emitter.onError(Throwable(response.errorMsg))
            }
        }
    } //
    //    public static <T> Observable<String> getSilgleMsg(BaseResponse<T> response) {
    //        return Observable.create(emitter -> {
    //            if (response.isSuccess()) {
    //                emitter.onNext("操作成功");
    //                emitter.onComplete();
    //            } else {
    //                emitter.onError(new Throwable(response.getMessage()));
    //            }
    //        });
    //    }
    //
    //    public static <T> Single<List<T>> getSingleData(BaseResponse<T> response) {
    //        return Single.create(emitter -> {
    //            if (response.isSuccess()) {
    //                emitter.onSuccess(response.getData());
    //            } else {
    //                emitter.onError(new Throwable(response.getMessage()));
    //            }
    //        });
    //    }
}