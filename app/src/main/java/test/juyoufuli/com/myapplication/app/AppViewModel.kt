package test.juyoufuli.com.myapplication.app

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.tencent.connect.UserInfo
import com.we.jetpackmvvm.base.appContext
import com.we.jetpackmvvm.base.viewmodel.BaseViewModel
import com.we.jetpackmvvm.callback.livedata.event.EventLiveData
import test.juyoufuli.com.myapplication.app.utils.SPUtils

/**
 * @Author : dongfang
 * @Created Time : 2021-12-08  10:05
 * @Description:
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userInfo = UnPeekLiveData.Builder<UserInfo>().setAllowNullValue(true).create()

    //App主题模式，目前有0默认/1暗夜模式
    var appTheme = EventLiveData<Int>()

    //App主题颜色
    var appColor = EventLiveData<Int>()

    init {
        //默认值保存的账户信息，没有登陆过则为null
        userInfo.value = SPUtils.get(appContext, "userInfo", null) as UserInfo?
        //默认值颜色
        appColor.value = SPUtils.get(appContext, "theme", null) as Int?
    }
}