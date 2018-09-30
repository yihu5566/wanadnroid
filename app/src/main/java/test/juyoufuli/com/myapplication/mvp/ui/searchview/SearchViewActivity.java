package test.juyoufuli.com.myapplication.mvp.ui.searchview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract;
import test.juyoufuli.com.myapplication.mvp.presenter.SearchViewPresenter;

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

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.search_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar_title.setText("搜索");
        toolbar_back.setVisibility(View.VISIBLE);
        toolbar_back.setOnClickListener(this);
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
            default:
        }

    }
}
