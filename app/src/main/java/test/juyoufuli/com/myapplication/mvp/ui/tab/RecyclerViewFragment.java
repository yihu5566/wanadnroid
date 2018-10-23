package test.juyoufuli.com.myapplication.mvp.ui.tab;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.di.component.DaggerSystemDataItemComponent;
import test.juyoufuli.com.myapplication.di.module.SystemDataItemModule;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract;
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataDetailsItemPresenter;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.BaseRecyclerViewAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity;

/**
 * Author : dongfang
 * Created Time : 2018-10-17  16:33
 * Description:
 */
public class RecyclerViewFragment extends BaseFragment<SystemDataDetailsItemPresenter> implements SystemDataDetailsItemContract.View {
    @BindView(R.id.rlv_pager)
    RecyclerView mRecyclerView;
    @Inject
    List<ArticleBean> mSystemData;
    @Inject
    SearchAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    private String cid;
    //上拉加载更多
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private int page;
    private int totalPage;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSystemDataItemComponent.builder().appComponent(appComponent).systemDataItemModule(new SystemDataItemModule(this)).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.view_pager_item, null);
        return inflate;
    }

    private boolean isFrist = true;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo("---initData---");
        if (isFrist) {
            isFrist = false;
            cid = getArguments().getString("cid");
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mPresenter.requestSystemDataList(String.valueOf(0), cid);
            initPaginate();

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<ArticleBean>() {
                @Override
                public void onItemClick(int clickId, int position, ArticleBean item) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("link", item.getLink());
                    intent.putExtra("title", item.getTitle());
                    launchActivity(intent);
                }
            });

        }
    }

    @Override
    public void setData(@Nullable Object data) {
        if (data != null && data instanceof Message) {
            cid = ((Message) data).getData().getString("cid");
            mPresenter.requestSystemDataList(String.valueOf(0), cid);
        }
    }


    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    if (totalPage > page) {
                        isLoadingMore = true;
                        mPresenter.requestSystemDataList(String.valueOf(page), cid);
                    }

                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return page == totalPage;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


    @Override
    public void refreshData(@NotNull ArticleResponse response) {
        page = response.getData().getCurPage();
        if (response.getData().getCurPage() == 1) {
            mAdapter.clearList();
            mAdapter.setList(response.getData().getDatas());
            totalPage = response.getData().getPageCount();
        } else {
            mAdapter.appendData(response.getData().getDatas());
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
