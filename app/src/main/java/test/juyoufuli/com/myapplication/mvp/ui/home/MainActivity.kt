package test.juyoufuli.com.myapplication.app.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.JsonUtils
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.di.component.DaggerHomeComponent
import test.juyoufuli.com.myapplication.di.module.HomeModule
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import test.juyoufuli.com.myapplication.mvp.presenter.HomePresenter
import test.juyoufuli.com.myapplication.mvp.ui.account.CollectArticleActivity
import test.juyoufuli.com.myapplication.mvp.ui.account.LoginActivity
import test.juyoufuli.com.myapplication.mvp.ui.home.MainFragment
import test.juyoufuli.com.myapplication.mvp.ui.navigation.NavigationFragment
import test.juyoufuli.com.myapplication.mvp.ui.project.ProjectFragment
import test.juyoufuli.com.myapplication.mvp.ui.searchview.SearchViewActivity
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataFragment
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity


class MainActivity : BaseActivity<HomePresenter>(), HomeContract.View {
    @JvmField
    @BindView(R.id.fl_content)
    internal var flContent: FrameLayout? = null
    @JvmField
    @BindView(R.id.dl_main_tab)
    internal var dl_main_tab: DrawerLayout? = null
    @JvmField
    @BindView(R.id.navigationView)
    var mNavigationView: NavigationView? = null
    @JvmField
    @BindView(R.id.navigation)
    internal var navigation: BottomNavigationView? = null
    @JvmField
    @BindView(R.id.toolbar_title)
    internal var toolbar_title: TextView? = null
    @JvmField
    @BindView(R.id.toolbar_menu)
    internal var toolbar_menu: RelativeLayout? = null
    @JvmField
    @BindView(R.id.toolbar_search)
    internal var toolbar_search: RelativeLayout? = null

    var currentFragment: Fragment? = null
    var screenWidth: Int? = null
    var tvPersonLogin: TextView? = null
    var tvPersonName: TextView? = null
    var isLogin: Boolean = false
    var isSelect: Int = R.id.bottom_menu_home

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent.builder().appComponent(appComponent).homeModule(HomeModule(this)).build().inject(this)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtils.d("onSaveInstanceState...")

    }

    @SuppressLint("ResourceAsColor")
    override fun initData(savedInstanceState: Bundle?) {
//        flContent = findViewById(R.id.fl_content)
//        dl_main_tab = findViewById<DrawerLayout>(R.id.dl_main_tab)
//        mNavigationView = findViewById<NavigationView>(R.id.navigationView)
        var headerLayout = mNavigationView!!.getHeaderView(0); // 0-index header
        tvPersonName = headerLayout!!.findViewById<TextView>(R.id.tv_person_name)

        tvPersonLogin = headerLayout!!.findViewById<TextView>(R.id.tv_person_login)

        add(MainFragment(), R.id.fl_content, "main")

        var window = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = window.defaultDisplay.width
//        StatusBarUtil.setColor(this, R.color.colorPrimary)
//        StatusBarUtil.setTranslucentForDrawerLayout(this, dl_main_tab)

        init()
    }

    fun init() {
        super.onStart()
        LogUtils.d("onStart...")
//        navigation!!.menu.getItem(0).isChecked = true
//        navigation!!.menu.getItem(1).isChecked = false
//        navigation!!.menu.getItem(2).isChecked = false
        disableShiftMode();
        isLogin = SPUtils.get(this, "isLogin", false) as Boolean

        navigation!!.setOnNavigationItemSelectedListener { item ->

            item.isChecked = item.itemId == isSelect

            when (item.itemId) {
                R.id.bottom_menu_home -> {
                    add(MainFragment(), R.id.fl_content, "main")
//                    navigation!!.menu.getItem(0).isChecked = false
                    toolbar_title!!.text = ("首页")
                    true
                }
                R.id.bottom_menu_found -> {
                    add(SystemDataFragment(), R.id.fl_content, "system")
//                    navigation!!.menu.getItem(1).isChecked = false
                    toolbar_title!!.text = ("知识体系")
                    true
                }
                R.id.bottom_menu_project -> {
                    add(ProjectFragment(), R.id.fl_content, "project")
//                    navigation!!.menu.getItem(2).isChecked = false
                    toolbar_title!!.text = ("项目")
                    true
                }
                R.id.bottom_menu_navigation -> {
                    add(NavigationFragment(), R.id.fl_content, "navigation")
//                    navigation!!.menu.getItem(2).isChecked = false
                    toolbar_title!!.text = ("导航")
                    true
                }
            }
            isSelect = item.itemId
            false
        }

        mNavigationView!!.setNavigationItemSelectedListener { item ->

            dl_main_tab!!.closeDrawer(Gravity.LEFT)
            when (item.itemId) {
                R.id.menu_History -> {
                    launchActivity(Intent(this, CollectArticleActivity::class.java))
                    true
                }
                R.id.menu_Setting -> {
                    ArmsUtils.makeText(this, "开发中，敬请期待")
                    true
                }
                R.id.menu_AboutUs -> {
                    var intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("link", "https://www.github.com/yihu5566")
                    intent.putExtra("title", "yihu5566")
                    launchActivity(intent)
                    true
                }
            }
            false
        }
        toolbar_title!!.text = ("首页")
        toolbar_search!!.visibility = (View.VISIBLE)
        toolbar_menu!!.visibility = (View.VISIBLE)
        toolbar_menu!!.setOnClickListener {
            dl_main_tab!!.openDrawer(Gravity.LEFT)
        }
        toolbar_search!!.setOnClickListener { launchActivity(Intent(this, SearchViewActivity::class.java)) }

        tvPersonLogin!!.setOnClickListener {
            if (isLogin) {
                dl_main_tab!!.closeDrawer(Gravity.LEFT)
                mPresenter!!.LoginOut()
            } else {
                dl_main_tab!!.closeDrawer(Gravity.LEFT)
                launchActivity(Intent(this, LoginActivity::class.java))
            }
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (TextUtils.isEmpty(intent!!.getStringExtra("username")) || TextUtils.isEmpty(intent!!.getStringExtra("password"))) {
            isLogin = false
            return
        }
        isLogin = true
        SPUtils.put(this, "isLogin", isLogin)
        tvPersonName!!.text = intent!!.getStringExtra("username")
        tvPersonLogin!!.text = "退出登录"
    }


    fun add(fragment: Fragment, id: Int, tag: String) {
        var fragment = fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //优先检查，fragment是否存在，避免重叠
        var tempFragment = supportFragmentManager.findFragmentByTag(tag)
        if (tempFragment != null) {
            fragment = tempFragment
        }
        if (fragment.isAdded()) {
            addOrShowFragment(fragmentTransaction, fragment, id, tag);
        } else {
            if (currentFragment != null && currentFragment!!.isAdded()) {
                fragmentTransaction.hide(currentFragment!!).add(id, fragment, tag).commit();
            } else {
                fragmentTransaction.add(id, fragment, tag).commit();
            }
            currentFragment = fragment;
        }
    }

    fun addOrShowFragment(transaction: FragmentTransaction, fragment: Fragment, id: Int, tag: String) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment!!).add(id, fragment, tag).commit();
        } else {
            transaction.hide(currentFragment!!).show(fragment).commit();
        }
        currentFragment!!.setUserVisibleHint(false);
        currentFragment = fragment;
        currentFragment!!.setUserVisibleHint(true);
    }


    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume...")

        dl_main_tab!!.addDrawerListener(object : DrawerLayout.DrawerListener {//添加开发者自己处理的监听者

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                println("----onDrawerSlide-  $slideOffset")
            }

            override fun onDrawerOpened(drawerView: View) {
                println("----onDrawerOpened-")

                val user = SPUtils.get(this@MainActivity, "user", "") as String
                if (!TextUtils.isEmpty(user)) {
                    val fromJsonToBean = JsonUtils.fromJsonToBean(user, LoginResponse::class.java) as LoginResponse
                    isLogin = true

                    tvPersonName!!.text = fromJsonToBean.data.username
                    tvPersonLogin!!.text = "退出登录"

                } else {
                    isLogin = false
                    tvPersonName!!.text = "用户名"
                    tvPersonLogin!!.text = "前往登陆"

                }
                SPUtils.put(this@MainActivity, "isLogin", isLogin)

            }

            override fun onDrawerClosed(drawerView: View) {
                println("----onDrawerClosed-")

            }

            override fun onDrawerStateChanged(newState: Int) {
                println("----onDrawerStateChanged-  $newState")

            }
        })

    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("onPause...")

    }

    override fun onStop() {
        super.onStop()
        LogUtils.d("onStop...")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("onDestroy...")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.d("onConfigurationChanged...")

    }

    @SuppressLint("RestrictedApi")
    private fun disableShiftMode() {
        val menuView = navigation!!.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false

            for (i in 0 until menuView.childCount) {
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
//                itemView.setShiftingMode(false)
                itemView.setChecked(itemView.itemData.isChecked)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }

    override fun loginOutSucceed() {
        isLogin = false
        SPUtils.put(this, "isLogin", isLogin)
        tvPersonName!!.text = "用户名"
        tvPersonLogin!!.text = "前往登陆"
        SPUtils.clear(this)
    }

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
        ArmsUtils.exitApp()
    }

    override fun showMessage(message: String) {
    }
}
