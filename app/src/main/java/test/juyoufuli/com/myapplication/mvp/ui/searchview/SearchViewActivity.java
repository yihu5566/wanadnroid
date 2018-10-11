package test.juyoufuli.com.myapplication.mvp.ui.searchview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.di.component.DaggerSearchComponent;
import test.juyoufuli.com.myapplication.di.module.SearchModule;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.entity.Datas;
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract;
import test.juyoufuli.com.myapplication.mvp.presenter.SearchViewPresenter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.BaseRecyclerViewAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Author : ludf
 * Created Time : 2018-09-30  15:56
 * Description:
 */
public class SearchViewActivity extends BaseActivity<SearchViewPresenter> implements SearchContract.View, View.OnClickListener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbar_back;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.rlv_search_result)
    RecyclerView rlvSearchResult;
    @BindView(R.id.et_search_word)
    EditText etSearchWord;
    @BindView(R.id.btn_search_word)
    Button btnSearchWord;

    @Inject
    SearchAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    private String searchWord;

    private int page;
    private int totalPage;
    private Paginate mPaginate;
    private boolean isLoadingMore;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent.builder().appComponent(appComponent).searchModule(new SearchModule(this)).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.search_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initPaginate();
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    if (!isFirst) {
                        if (totalPage > page) {
                            isLoadingMore = true;
                            mPresenter.getSearchResult(page, searchWord);
                        }

                    } else {
                        isFirst = false;
                    }
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return page==totalPage;
                }
            };

            mPaginate = Paginate.with(rlvSearchResult, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(rlvSearchResult, mLayoutManager);
        rlvSearchResult.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<Datas>() {
            @Override
            public void onItemClick(int clickId, int position, Datas item) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", item.getLink());
                intent.putExtra("title", item.getTitle());
                launchActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar_title.setText("搜索");
        toolbar_back.setVisibility(View.VISIBLE);
        toolbar_back.setOnClickListener(this);
        btnSearchWord.setOnClickListener(this);


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
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                killMyself();
                break;
            case R.id.btn_search_word:
                if (mAdapter != null) {
                    mAdapter.clearList();
                    page = 0;
                }
                isLoadingMore = true;
                searchWord = etSearchWord.getText().toString();
                mPresenter.getSearchResult(page, searchWord);
                break;
            default:
        }

    }

    @NotNull
    @Override
    public Activity getActivity() {
        return this;
    }

    private boolean isFirst = true;

    @Override
    public void refreshList(@NotNull ArticleResponse response) {
        page = response.getData().getCurPage();

        if (response.getData().getCurPage() == 1) {
            mAdapter.setList(response.getData().getDatas());
            totalPage = response.getData().getPageCount();
        } else {
            mAdapter.appendData(response.getData().getDatas());
        }

        isLoadingMore = false;


    }
}
