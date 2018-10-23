package com.li.mykotlinapp.util;


import com.li.mykotlinapp.base.BaseListResponse;

import java.util.List;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:  Ivan
 * Date:    Jan 02, 2018
 */

public class RxUtil {

    private static final String TAG = "RxUtil";

    public static <T> ObservableTransformer<T, T> trans_io_main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> trans_io_io() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static CompletableTransformer trans_complete_io_main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> MaybeTransformer<T, T> trans_maybe_io_main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable<List<T>> getData(BaseListResponse<T> response) {
        return Observable.create(emitter -> {
            if (response.getErrorCode()==0) {
                emitter.onNext(response.getData());
                emitter.onComplete();
            } else {
                emitter.onError(new Throwable(response.getErrorMsg()));
            }
        });
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
//    public static <T> Observable<T> getObject(BaseObjectResponse<T> response) {
//        return Observable.create(emitter -> {
//            if (response.isSuccess()) {
//                T data = response.getData();
//                if (data != null) {
//                    emitter.onNext(data);
//                    emitter.onComplete();
//                } else {
//                    emitter.onError(new Throwable("暂无数据"));
//                }
//            } else {
//                if (response.getMessage()!=null){
//                    emitter.onError(new Throwable(response.getMessage()));
//                }else {
//                    emitter.onError(new Throwable("未知错误"));
//                }
//            }
//        });
//    }
//
//    public static <T> Observable<String> getMessage(BaseResponse<T> response) {
//        return Observable.create(emitter -> {
//            if (response.isSuccess()) {
//                emitter.onNext(response.getMessage());
//                emitter.onComplete();
//            } else {
//                emitter.onError(new Throwable(response.getMessage()));
//            }
//        });
//    }
//
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
