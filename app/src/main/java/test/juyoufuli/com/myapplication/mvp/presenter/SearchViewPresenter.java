package test.juyoufuli.com.myapplication.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;

/**
 * Author : ludf
 * Created Time : 2018-09-30  15:58
 * Description:
 */
@ActivityScope
public class SearchViewPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    SearchAdapter mAdapter;

    @Inject
    public SearchViewPresenter(SearchContract.Model model, SearchContract.View rootView) {
        super(model, rootView);
    }

    public void getSearchResult(int page, String result) {
        mModel.getSearchResult(page, result)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))
//                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ArticleResponse>(mErrorHandler) {

                    @Override
                    public void onNext(ArticleResponse response) {
                        LogUtils.debugInfo(page + "--------------url");
                        mRootView.refreshList(response);

                    }
                });

    }


}
