package test.juyoufuli.com.myapplication.mvp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.app.utils.ImageLoaderUtils;
import test.juyoufuli.com.myapplication.di.component.DaggerMainComponent;
import test.juyoufuli.com.myapplication.di.module.MainModule;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor;
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract;
import test.juyoufuli.com.myapplication.mvp.presenter.MainPresenter;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleItemHolder;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.SearchViewActivity;
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @Inject
    RxPermissions mRxPermissions;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ArticleAdapter mAdapter;


    private Paginate mPaginate;
    private boolean isLoadingMore;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initPaginate();
        mPresenter.requestUsers(true);
        mPresenter.requestBannerDataList();
    }

    private void initBanner(List<BannerInfor> mBannerList) {
        ArrayList<String> imagePath = new ArrayList<>();
        ArrayList<String> imageTitle = new ArrayList<>();
        for (BannerInfor testResponse : mBannerList) {
            imagePath.add(testResponse.getImagePath());
            imageTitle.add(testResponse.getTitle());
        }
        bannerGuideContent.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                ImageLoaderUtils.loadImage((ImageView) itemView, model, getContext());

            }
        });

        bannerGuideContent.setData(imagePath, imageTitle);
        bannerGuideContent.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", mBannerList.get(position).getUrl());
                intent.putExtra("title", mBannerList.get(position).getTitle());
                launchActivity(intent);
            }
        });
    }


    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestUsers(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()){
//            @Override
//            public boolean canScrollVertically() {
//                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
//                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
//                return false;
//            }
//        });


        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, viewType, data, position) -> {
//                     LogUtils.debugInfo(((Datas)data).getLink()+position);
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("link", ((ArticleBean) data).getLink());
            intent.putExtra("title", ((ArticleBean) data).getTitle());
            launchActivity(intent);
        });

        mAdapter.setChildClickListener(new ArticleItemHolder.ChildClickListener() {
            @Override
            public void viewClick(int viewid, int position, ArticleBean data) {
                if (data.isCheck()) {
                    LogUtils.debugInfo((data).getId() + "--true--" + position);
                } else {
                    LogUtils.debugInfo((data).getId() + "--false--" + position);

                }
//                mPresenter.collectArticle(data.getId()+"");
            }
        });
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @NotNull
    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onRefresh() {
        mPresenter.requestUsers(true);
    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        mPresenter.requestUsers(true);//打开 App 时自动加载列表
//        mPresenter.requestBannerDataList();
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void updateBanner(@NotNull BannerResponse systemDataResponse) {
        initBanner(systemDataResponse.getData());

    }
}
