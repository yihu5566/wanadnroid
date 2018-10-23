package test.juyoufuli.com.myapplication.mvp.ui.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.di.component.DaggerSystemDataComponent;
import test.juyoufuli.com.myapplication.di.module.SystemDataModule;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract;
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataPresenter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.SearchViewActivity;
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Author : ludf
 * Created Time : 2018-09-29  11:20
 * Description:
 */
public class SystemDataFragment extends BaseFragment<SystemDataPresenter> implements SystemDataContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Inject
    RxPermissions mRxPermissions;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    SystemDataAdapter mAdapter;
    @Inject
    ArrayList<String> tagName;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSystemDataComponent.builder().appComponent(appComponent).systemDataModule(new SystemDataModule(this)).build().inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment, null);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.requestSystemDataList();//打开 App 时自动加载列表

    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);

        mAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                LogUtils.debugInfo(((SystemBean) data).getName() + position);
                SystemBean data1 = (SystemBean) data;
                Intent intent = new Intent(getActivity(), SystemDataDetailsActivity.class);
                tagName.clear();
                StringBuffer stringb;
                for (int i = 0; i < data1.getChildren().size(); i++) {
                    stringb = new StringBuffer();
                    stringb.append(data1.getChildren().get(i).getId());
                    stringb.append("*");
                    stringb.append(data1.getChildren().get(i).getName());

                    tagName.add(stringb.toString());
                }
                intent.putStringArrayListExtra("tagName", tagName);

                launchActivity(intent);
            }
        });
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return this;
    }

    @NotNull
    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mPresenter.requestSystemDataList();//打开 App 时自动加载列表

    }

}
