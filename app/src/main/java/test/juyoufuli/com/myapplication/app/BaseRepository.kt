package test.juyoufuli.com.myapplication.app

import com.we.jetpackmvvm.callback.livedata.event.EventLiveData
import com.we.jetpackmvvm.ext.executeResponse
import com.we.jetpackmvvm.ext.util.loge
import com.we.jetpackmvvm.network.AppException
import com.we.jetpackmvvm.network.BaseResponse
import com.we.jetpackmvvm.network.ExceptionHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Author : dongfang
 * @Created Time : 2023-08-23  14:54
 * @Description:
 */
open class BaseRepository {
    private val mainScope = MainScope()

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
    ): Job {
        //如果需要弹窗 通知Activity/fragment弹窗
        return mainScope.launch {
            runCatching {
                //请求体
                block()
            }.onSuccess {
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
                //打印错误消息
                it.message?.loge()
                //失败回调
                error(ExceptionHandle.handleException(it))
            }
        }
    }
}