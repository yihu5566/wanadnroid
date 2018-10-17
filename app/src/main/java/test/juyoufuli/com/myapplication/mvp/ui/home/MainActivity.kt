package test.juyoufuli.com.myapplication.app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.FrameLayout
import com.jess.arms.base.BaseActivity
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import kotlinx.android.synthetic.main.activity_main.*
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract
import test.juyoufuli.com.myapplication.mvp.presenter.HomePresenter
import test.juyoufuli.com.myapplication.mvp.ui.home.MainFragment
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataFragment


class MainActivity : BaseActivity<HomePresenter>(), HomeContract.View {
    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
    }

    override fun hideLoading() {
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
    }

    private var flContent: FrameLayout? = null
    var currentFragment: Fragment? = null


    override fun initData(savedInstanceState: Bundle?) {
        flContent = findViewById(R.id.fl_content)
        add(MainFragment(), R.id.fl_content, "main")

    }

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtils.d("onSaveInstanceState...")

    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("onStart...")
        navigation.menu.getItem(1).isChecked = true
        navigation.menu.getItem(0).isChecked = false

        navigation.setOnNavigationItemSelectedListener { item ->
            item.isChecked = true
            when (item.itemId) {
                R.id.bottom_menu_home -> {
                    add(MainFragment(), R.id.fl_content, "main")
                    navigation.menu.getItem(0).isChecked = false
                    true
                }
                R.id.bottom_menu_found -> {
                    add(SystemDataFragment(), R.id.fl_content, "system")
                    navigation.menu.getItem(1).isChecked = false
                    true
                }
            }
            false
        }

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
                fragmentTransaction.hide(currentFragment).add(id, fragment, tag).commit();
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
            transaction.hide(currentFragment).add(id, fragment, tag).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment!!.setUserVisibleHint(false);
        currentFragment = fragment;
        currentFragment!!.setUserVisibleHint(true);
    }


    override fun onResume() {
        super.onResume()
        LogUtils.d("onResume...")

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
        val menuView = navigation.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false

            for (i in 0 until menuView.childCount) {
                val itemView = menuView.getChildAt(i) as BottomNavigationItemView
                itemView.setShiftingMode(false)
                itemView.setChecked(itemView.itemData.isChecked)
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }

}
