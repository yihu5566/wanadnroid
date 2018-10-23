package test.juyoufuli.com.myapplication.mvp.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.app.ui.home.MainActivity;
import test.juyoufuli.com.myapplication.app.utils.JsonUtils;
import test.juyoufuli.com.myapplication.app.utils.SPUtils;
import test.juyoufuli.com.myapplication.di.component.DaggerLoginComponent;
import test.juyoufuli.com.myapplication.di.module.LoginModule;
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.LoginContract;
import test.juyoufuli.com.myapplication.mvp.presenter.LoginPresenter;

/**
 * Author : dongfang
 * Created Time : 2018-10-19  14:04
 * Description:
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbar_back;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_again)
    EditText et_password_again;
    @BindView(R.id.bt_go)
    Button bt_go;
    @BindView(R.id.bt_register)
    Button bt_register;
    @BindView(R.id.login_root)
    RelativeLayout loginRoot;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(new LoginModule(this)).build().inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar_title.setText("搜索");
        toolbar_back.setVisibility(View.VISIBLE);
        toolbar_back.setOnClickListener(this);
        bt_go.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        loginRoot.setOnTouchListener((view, motionEvent) -> {
            hideSoft();
            return false;
        });
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
        finish();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                killMyself();
                break;
            case R.id.bt_go:
                String name = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                mPresenter.login(name, password);
                break;
            case R.id.bt_register:
                name = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                String respassword = et_password_again.getText().toString().trim();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(respassword)) {
                    ArmsUtils.makeText(this, "密码不能为空");
                    return;
                }

                if (!password.equals(respassword)) {
                    ArmsUtils.makeText(this, "两次密码不一致");
                    return;
                }
                mPresenter.register(name, password, respassword);
                break;

            default:
        }
    }

    @NotNull
    @Override
    public Activity getActivity() {
        return this;
    }


    /**
     * 隐藏键盘
     */
    private void hideSoft() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginRoot.getWindowToken(), 0);
    }

    @Override
    public void loginSucceed(@NotNull LoginResponse response) {
        if (response.getData() == null) return;
        SPUtils.put(this, "user", JsonUtils.toJson(response));
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", TextUtils.isEmpty(response.getData().getUsername()) ? "" : response.getData().getUsername());
        intent.putExtra("password", TextUtils.isEmpty(response.getData().getPassword()) ? "" : response.getData().getPassword());

        launchActivity(intent);
        killMyself();
    }
}
