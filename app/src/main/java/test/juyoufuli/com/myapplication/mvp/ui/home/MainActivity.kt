package test.juyoufuli.com.myapplication.app.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatDelegate
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.JsonUtils
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.app.utils.ToastUtils.showToast
import test.juyoufuli.com.myapplication.di.component.DaggerHomeComponent
import test.juyoufuli.com.myapplication.di.module.HomeModule
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract
import test.juyoufuli.com.myapplication.mvp.presenter.HomePresenter
import test.juyoufuli.com.myapplication.mvp.ui.account.CollectArticleActivity
import test.juyoufuli.com.myapplication.mvp.ui.account.LoginActivity
import test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao.WeChatNumberFragment
import test.juyoufuli.com.myapplication.mvp.ui.home.MainFragment
import test.juyoufuli.com.myapplication.mvp.ui.navigation.NavigationFragment
import test.juyoufuli.com.myapplication.mvp.ui.project.ProjectFragment
import test.juyoufuli.com.myapplication.mvp.ui.searchview.SearchViewActivity
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataFragment
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity


class MainActivity : BaseActivity<HomePresenter>(), HomeContract.View, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

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
    var currentFragmentIndex: Int? = 0

    var screenWidth: Int? = null
    var tvPersonLogin: TextView? = null
    var tvPersonRegister: TextView? = null
    var tvPersonName: TextView? = null
    var ivPersonPhoto: ImageView? = null

    var isLogin: Boolean = false
    var isSelect: Int = R.id.bottom_menu_home
    var fragmentTransaction: FragmentTransaction? = null
    var fragmentList: ArrayList<Fragment> = ArrayList()


    var isRecreat: Boolean = false


    private var lastClickTime: Long = 0//上次点击的时间

    private val spaceTime = 2000//时间间隔

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerHomeComponent.builder().appComponent(appComponent).homeModule(HomeModule(this)).build().inject(this)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.d("onSaveInstanceState..." + currentFragmentIndex)
        outState.putInt("currentFragmentIndex", currentFragmentIndex!!)
    }

    @SuppressLint("ResourceAsColor")
    override fun initData(savedInstanceState: Bundle?) {
        try {//避免重启太快 恢复
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            for (fragment in fragmentList) {
                fragmentTransaction.remove(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (savedInstanceState == null) {
            fragmentList.clear()
            fragmentList.add(MainFragment())
            fragmentList.add(SystemDataFragment())
            fragmentList.add(ProjectFragment())
            fragmentList.add(NavigationFragment())
            fragmentList.add(WeChatNumberFragment())

            add(0, R.id.fl_content, "main")
        } else {
            fragmentList.clear()
            fragmentList.add(MainFragment())
            fragmentList.add(SystemDataFragment())
            fragmentList.add(ProjectFragment())
            fragmentList.add(NavigationFragment())
            fragmentList.add(WeChatNumberFragment())

            LogUtils.d("initData剩余fragment..." + supportFragmentManager.fragments.size)

            var tagFragment = savedInstanceState.get("currentFragmentIndex") as Int
            currentFragment = fragmentList.get(tagFragment)
            when (tagFragment) {
                0 -> add(tagFragment, R.id.fl_content, "main")
                1 -> add(tagFragment, R.id.fl_content, "system")
                2 -> add(tagFragment, R.id.fl_content, "project")
                3 -> add(tagFragment, R.id.fl_content, "navigation")
                4 -> add(tagFragment, R.id.fl_content, "wechat")

            }


        }
//        flContent = findViewById(R.id.fl_content)
//        dl_main_tab = findViewById<DrawerLayout>(R.id.dl_main_tab)
//        mNavigationView = findViewById<NavigationView>(R.id.navigationView)
        var headerLayout = mNavigationView!!.getHeaderView(0) // 0-index header
        tvPersonName = headerLayout!!.findViewById(R.id.tv_person_name)
        tvPersonLogin = headerLayout!!.findViewById(R.id.tv_person_login)
        tvPersonRegister = headerLayout!!.findViewById(R.id.tv_person_register)

        ivPersonPhoto = headerLayout!!.findViewById(R.id.tv_person)

        var item = mNavigationView!!.menu.getItem(1).actionView // 0-index header
        var switchView = item!!.findViewById<Switch>(R.id.switchForActionBar)
//        //条目中的控件
        switchView!!.setOnClickListener(this@MainActivity)
        var window = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = window.defaultDisplay.width
//        StatusBarUtil.setColor(this, R.color.colorPrimary)
//        StatusBarUtil.setTranslucentForDrawerLayout(this, dl_main_tab)
        init()
    }


    fun init() {
        LogUtils.d("onStart...")

//        disableShiftMode()
        isLogin = SPUtils.get(this, "isLogin", false) as Boolean
        navigation!!.setOnNavigationItemSelectedListener { item ->
            item.isChecked = item.itemId == isSelect
            LogUtils.d("bottom_menu_home..." + supportFragmentManager.fragments.size)
            when (item.itemId) {
                R.id.bottom_menu_home -> {
                    add(0, R.id.fl_content, "main")
//                    navigation!!.menu.getItem(0).isChecked = false
                    toolbar_title!!.text = ("首页")
                    currentFragmentIndex = 0
                    true
                }
                R.id.bottom_menu_found -> {
                    add(1, R.id.fl_content, "system")
//                    navigation!!.menu.getItem(1).isChecked = false
                    toolbar_title!!.text = ("知识体系")
                    currentFragmentIndex = 1
                    true
                }
                R.id.bottom_menu_project -> {
                    add(2, R.id.fl_content, "project")
//                    navigation!!.menu.getItem(2).isChecked = false
                    toolbar_title!!.text = ("项目")
                    currentFragmentIndex = 2
                    true
                }
                R.id.bottom_menu_navigation -> {
                    add(3, R.id.fl_content, "navigation")
//                    navigation!!.menu.getItem(2).isChecked = false
                    toolbar_title!!.text = ("导航")
                    currentFragmentIndex = 3
                    true
                }

                R.id.bottom_menu_wechat -> {
                    add(4, R.id.fl_content, "wechat")
//                    navigation!!.menu.getItem(2).isChecked = false
                    toolbar_title!!.text = ("公众号")
                    currentFragmentIndex = 4
                    true
                }
            }
            isSelect = item.itemId
            false
        }


        mNavigationView!!.setNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menu_History -> {
                    dl_main_tab!!.closeDrawer(Gravity.LEFT)

                    launchActivity(Intent(this, CollectArticleActivity::class.java))
                    true
                }
                R.id.menu_Setting -> {
//                    var mode = SPUtils.get(applicationContext, "night_mode", false) as Boolean
                    //进入方法就先注册上监听
//                    var switch = item.actionView.findViewById(R.id.switchForActionBar) as Switch
                    //进入方法就先注册上监听
//                    if (!mode) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    } else {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    }
//                    switch.isChecked = !mode
//                    dl_main_tab!!.closeDrawer(Gravity.LEFT)
//                    launchActivity(Intent(this, SettingActivity::class.java))
                    true
                }
                R.id.menu_AboutUs -> {
                    dl_main_tab!!.closeDrawer(Gravity.LEFT)

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
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("type", 1)
                launchActivity(intent)
            }
        }
        tvPersonRegister!!.setOnClickListener {
            dl_main_tab!!.closeDrawer(Gravity.LEFT)
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("type", 2)
            launchActivity(intent)

        }

    }

    override fun recreate() {
//        if (!isRecreat) {
//            super.recreate()
//            return
//        }
//        isRecreat = false
        try {//避免重启太快 恢复
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            for (fragment in fragmentList) {
                fragmentTransaction.remove(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
        }
        fragmentList.clear()
        super.recreate()
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
        tvPersonRegister!!.visibility = View.GONE
    }


    fun add(idFragment: Int, id: Int, tag: String) {
        if (fragmentList.size == 0) return
        var fragment = fragmentList.get(idFragment)
        fragmentTransaction = supportFragmentManager.beginTransaction()
        //优先检查，fragment是否存在，避免重叠
        var tempFragment = supportFragmentManager.findFragmentByTag(tag)
        if (tempFragment != null) {
            fragment = tempFragment
        }
        if (fragment.isAdded) {
            addOrShowFragment(fragmentTransaction!!, fragment, id, tag)
        } else {
            if (currentFragment != null && currentFragment!!.isAdded()) {
                fragmentTransaction!!.hide(currentFragment!!).add(id, fragment, tag).commit()
            } else {
                fragmentTransaction!!.add(id, fragment, tag).commit()
            }
            currentFragment = fragment
        }
    }

    fun addOrShowFragment(transaction: FragmentTransaction, fragment: Fragment, id: Int, tag: String) {
        if (currentFragment == fragment)
            return
        if (!fragment.isAdded) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment!!).add(id, fragment, tag).commit()
        } else {
            transaction.hide(currentFragment!!).show(fragment).commit()
        }
        currentFragment!!.userVisibleHint = false
        currentFragment = fragment
        currentFragment!!.userVisibleHint = true
    }


    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume...")
        navigation!!.selectedItemId = isSelect

        dl_main_tab!!.addDrawerListener(object : DrawerLayout.DrawerListener {//添加开发者自己处理的监听者

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//                println("----onDrawerSlide-  $slideOffset")
            }

            override fun onDrawerOpened(drawerView: View) {
                println("----onDrawerOpened-")

                //设置一下状态
                var mode = SPUtils.get(applicationContext, "night_mode", false) as Boolean
                var switchView = drawerView.findViewById<Switch>(R.id.switchForActionBar)
                switchView!!.isChecked = mode
//                switchView!!.setOnCheckedChangeListener(this@MainActivity)
                switchView!!.setOnClickListener(this@MainActivity)

                val user = SPUtils.get(applicationContext, "user", "") as String
                if (!TextUtils.isEmpty(user)) {
                    val fromJsonToBean = JsonUtils.fromJsonToBean(user, LoginResponse::class.java) as LoginResponse
                    isLogin = true

                    tvPersonName!!.text = fromJsonToBean.data.username
                    tvPersonLogin!!.text = "退出登录"
                    Glide.with(applicationContext)
                            .load(R.drawable.head_photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(ivPersonPhoto!!)

                } else {
                    isLogin = false
                    tvPersonName!!.text = "用户名"
                    tvPersonLogin!!.text = "登陆"
                    ivPersonPhoto!!.setImageDrawable(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));

                }
                SPUtils.put(applicationContext, "isLogin", isLogin)


            }

            override fun onDrawerClosed(drawerView: View) {
                println("----onDrawerClosed-")

            }

            override fun onDrawerStateChanged(newState: Int) {
                println("----onDrawerStateChanged-  $newState")

            }
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        //通过switch
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        LogUtils.d("onDrawerOpened..." + isChecked)

        SPUtils.put(applicationContext, "night_mode", isChecked)
//        recreate()
    }

    override fun onClick(v: View) {
        LogUtils.d("onClick...触发了")
        if (v.id == R.id.switchForActionBar) {
            var mode = SPUtils.get(applicationContext, "night_mode", false) as Boolean
            var switch = v as Switch
            //通过switch
            if (!mode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            switch!!.isChecked = !mode
            LogUtils.d("onDrawerOpened..." + mode)
            SPUtils.put(applicationContext, "night_mode", !mode)
            isRecreat = true
            recreate()
        }
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
        LogUtils.d("onDestroy...")
        super.onDestroy()

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
        tvPersonLogin!!.text = "登陆"
        tvPersonRegister!!.visibility = View.VISIBLE

        SPUtils.clear(this)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }


    override fun showMessage(message: String) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val currentTime = System.currentTimeMillis()//当前系统时间
            val tempTime = currentTime - lastClickTime
            if (tempTime > spaceTime || tempTime < 0) {
                lastClickTime = currentTime
                showToast(this, "再点击一次退出")
                return true//禁用返回键
            } else {
                ArmsUtils.exitApp()
                return false//返回
            }
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }
}
