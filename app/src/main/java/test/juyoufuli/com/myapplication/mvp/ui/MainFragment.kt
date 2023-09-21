package test.juyoufuli.com.myapplication.mvp.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import com.we.jetpackmvvm.ext.nav
import com.we.jetpackmvvm.ext.navigateAction
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.ext.initMain
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.databinding.FragmentMainBinding
import test.juyoufuli.com.myapplication.mvp.views.CustomTabView

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), CustomTabView.OnTabCheckListener {

    override fun initView(savedInstanceState: Bundle?) {
        binding.mainViewpager.initMain(this)
        initTabSelect()
        initUserInfo()
    }

    override fun attachBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {
    }

    private fun initTabSelect() {
        val tabHome = CustomTabView.Tab().setText("主页")
            .setCheckedColor(resources.getColor(R.color.black))
            .setNormalIcon(R.drawable.ic_account_balance_black_24dp_select)
            .setPressedIcon(R.drawable.ic_account_balance_black_select_24dp)
        binding.customTabView.addTab(tabHome)
        val tabDis = CustomTabView.Tab().setText("体系")
            .setCheckedColor(resources.getColor(R.color.black))
            .setNormalIcon(R.drawable.ic_import_contacts_black_select_24dp)
            .setPressedIcon(R.drawable.ic_import_contacts_black_24dp)
        binding.customTabView.addTab(tabDis)
        val tabProfile = CustomTabView.Tab().setText("项目")
            .setCheckedColor(resources.getColor(R.color.black))
            .setNormalIcon(R.drawable.ic_subject_black_24dp)
            .setPressedIcon(R.drawable.ic_subject_black_select_24dp)
        binding.customTabView.addTab(tabProfile)
        val navigation = CustomTabView.Tab().setText("导航")
            .setCheckedColor(resources.getColor(R.color.black))
            .setNormalIcon(R.drawable.ic_apps_black_24dp)
            .setPressedIcon(R.drawable.ic_apps_black_select_24dp)
        binding.customTabView.addTab(navigation)
        val wechatPublish = CustomTabView.Tab().setText("公众号")
            .setCheckedColor(resources.getColor(R.color.black))
            .setNormalIcon(R.drawable.ic_sms_black_24dp)
            .setPressedIcon(R.drawable.ic_sms_black_24dp_select)
        binding.customTabView.addTab(wechatPublish)
        //设置监听
        binding.customTabView.setOnTabCheckListener(this)
        // 默认选中tab
        binding.customTabView.setCurrentItem(0)
    }

    override fun onTabSelected(v: View?, position: Int) {
        binding.mainViewpager.setCurrentItem(position, false)
        binding.includedTitle.toolbarTitle.text = when (position) {
            0 -> "首页"
            1 -> "知识体系"
            2 -> "项目"
            3 -> "导航"
            4 -> "公众号"
            else -> "首页"
        }
    }

    private fun initUserInfo() {
        val headerLayout = binding.navigationView.getHeaderView(0)
        val tvPersonName = headerLayout?.findViewById<TextView>(R.id.tv_person_name)
        val tvPersonLogin = headerLayout.findViewById<TextView>(R.id.tv_person_login)
        val tvPersonRegister = headerLayout.findViewById<TextView>(R.id.tv_person_register)
        val ivPersonPhoto = headerLayout.findViewById<ImageView>(R.id.tv_person)
        val item = binding.navigationView.menu.getItem(1)?.actionView // 0-index header
        val switchView = item?.findViewById<Switch>(R.id.switchForActionBar)
//        //条目中的控件
        switchView?.setOnClickListener { v ->
            var mode = SPUtils.get(requireContext(), "night_mode", false) as Boolean
            var switch = v as Switch
            //通过switch
            if (!mode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            switch.isChecked = !mode
            SPUtils.put(requireContext(), "night_mode", !mode)
        }

        val isLogin = SPUtils.get(requireContext(), "isLogin", false) as Boolean
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_History -> {
                    binding.dlMainTab.closeDrawer(Gravity.LEFT)
                    //ActivityUtils.startActivity(Intent(this, CollectArticleActivity::class.java))
                }

                R.id.menu_Setting -> {

                }

                R.id.menu_AboutUs -> {
                    binding.dlMainTab.closeDrawer(Gravity.LEFT)
                    nav().navigateAction(
                        R.id.action_to_webViewFragmentFragment,
                        bundleOf("title" to "yihu5566", "link" to "https://www.github.com/yihu5566")
                    )
                }
            }
            false
        }
        binding.includedTitle.toolbarTitle.text = ("首页")
        binding.includedTitle.toolbarSearch.visibility = (View.VISIBLE)
        binding.includedTitle.toolbarMenu.visibility = (View.VISIBLE)
        binding.includedTitle.toolbarMenu.setOnClickListener {
            binding.dlMainTab.openDrawer(Gravity.LEFT)
        }
        binding.includedTitle.toolbarSearch.setOnClickListener {
            nav().navigateAction(R.id.action_homeFragment_to_searchView)
        }

        tvPersonLogin.setOnClickListener {
            if (isLogin) {
                binding.dlMainTab.closeDrawer(Gravity.LEFT)
//                mPresenter?.LoginOut()
            } else {
                binding.dlMainTab.closeDrawer(Gravity.LEFT)
//                val intent = Intent(this, LoginActivity::class.java)
//                intent.putExtra("type", 1)
//                ActivityUtils.startActivity(intent)
            }
        }
        tvPersonRegister.setOnClickListener {
            binding.dlMainTab.closeDrawer(Gravity.LEFT)
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.putExtra("type", 2)
//            ActivityUtils.startActivity(intent)
        }
    }

}
