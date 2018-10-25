package test.juyoufuli.com.myapplication.mvp.ui.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import butterknife.BindView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.ui.home.MainActivity
import test.juyoufuli.com.myapplication.app.utils.JsonUtils
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.di.component.DaggerLoginComponent
import test.juyoufuli.com.myapplication.di.module.LoginModule
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.LoginContract
import test.juyoufuli.com.myapplication.mvp.presenter.LoginPresenter

/**
 * Author : dongfang
 * Created Time : 2018-10-19  14:04
 * Description:
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View, View.OnClickListener {
    @JvmField
    @BindView(R.id.toolbar_back)
    internal var toolbar_back: RelativeLayout? = null
    @JvmField
    @BindView(R.id.toolbar_title)
    internal var toolbar_title: TextView? = null
    @JvmField
    @BindView(R.id.et_username)
    internal var et_username: EditText? = null
    @JvmField
    @BindView(R.id.et_password)
    internal var et_password: EditText? = null
    @JvmField
    @BindView(R.id.et_password_again)
    internal var et_password_again: EditText? = null
    @JvmField
    @BindView(R.id.bt_go)
    internal var bt_go: Button? = null
    @JvmField
    @BindView(R.id.bt_register)
    internal var bt_register: Button? = null
    @JvmField
    @BindView(R.id.login_root)
    internal var loginRoot: RelativeLayout? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent.builder().appComponent(appComponent).loginModule(LoginModule(this)).build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar_title!!.text = "搜索"
        toolbar_back!!.visibility = View.VISIBLE
        toolbar_back!!.setOnClickListener(this)
        bt_go!!.setOnClickListener(this)
        bt_register!!.setOnClickListener(this)
        loginRoot!!.setOnTouchListener { view, motionEvent ->
            hideSoft()
            false
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {

    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
        finish()
    }

    override fun killMyself() {
        finish()
    }

    lateinit var name: String
    lateinit var password: String

    override fun onClick(view: View) {
        when (view.id) {
            R.id.toolbar_back -> killMyself()
            R.id.bt_go -> {
                name = et_username!!.text.toString().trim { it <= ' ' }
                password = et_password!!.text.toString().trim { it <= ' ' }
                mPresenter!!.login(name, password)
            }
            R.id.bt_register -> {
                name = et_username!!.text.toString().trim { it <= ' ' }
                password = et_password!!.text.toString().trim { it <= ' ' }
                val respassword = et_password_again!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(respassword)) {
                    ArmsUtils.makeText(this, "密码不能为空")
                    return
                }

                if (password != respassword) {
                    ArmsUtils.makeText(this, "两次密码不一致")
                    return
                }
                mPresenter!!.register(name, password, respassword)
            }
        }
    }

    override fun getActivity(): Activity {
        return this
    }


    /**
     * 隐藏键盘
     */
    private fun hideSoft() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(loginRoot!!.windowToken, 0)
    }

    override fun loginSucceed(response: LoginResponse) {
        if (response.data == null) return
        SPUtils.put(this, "user", JsonUtils.toJson(response))
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", if (TextUtils.isEmpty(response.data.username)) "" else response.data.username)
        intent.putExtra("password", if (TextUtils.isEmpty(response.data.password)) "" else response.data.password)

        launchActivity(intent)
        killMyself()
    }
}
