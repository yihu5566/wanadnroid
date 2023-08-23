package test.juyoufuli.com.myapplication.app

import com.we.jetpackmvvm.callback.livedata.event.EventLiveData
import com.we.jetpackmvvm.ext.executeResponse
import com.we.jetpackmvvm.ext.util.loge
import com.we.jetpackmvvm.network.AppException
import com.we.jetpackmvvm.network.BaseResponse
import com.we.jetpackmvvm.network.ExceptionHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @Author : dongfang
 * @Created Time : 2023-08-23  14:54
 * @Description:
 */
open class BaseRepository {
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }

    /**
     * 过滤服务器结果，失败抛异常
     * @param block 请求体方法，必须要用suspend关键字修饰
     * @param success 成功回调
     * @param error 失败回调 可不传
     * @param isShowDialog 是否显示加载框
     * @param loadingMessage 加载框提示内容
     */
    fun <T> request(
        block: suspend () -> BaseResponse<T>,
        success: (T) -> Unit,
        error: (AppException) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = "请求网络中...",
    ): Job {
        //如果需要弹窗 通知Activity/fragment弹窗
        return GlobalScope.launch {
            runCatching {
                if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
                //请求体
                block()
            }.onSuccess {
                //网络请求成功 关闭弹窗
                loadingChange.dismissDialog.postValue(false)
                runCatching {
                    //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                    executeResponse(it) { t ->
                        success(t)
                    }
                }.onFailure { e ->
                    //打印错误消息
                    e.message?.loge()
                    //失败回调
                    error(ExceptionHandle.handleException(e))
                }
            }.onFailure {
                //网络请求异常 关闭弹窗
                loadingChange.dismissDialog.postValue(false)
                //打印错误消息
                it.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(it))
            }
        }
    }
}