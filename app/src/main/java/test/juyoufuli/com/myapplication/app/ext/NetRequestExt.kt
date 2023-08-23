package test.juyoufuli.com.myapplication.app.ext

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.MavericksViewModel
import com.facebook.stetho.inspector.protocol.module.Network.LoadingFailedParams
import com.we.jetpackmvvm.ext.executeResponse
import com.we.jetpackmvvm.ext.util.loge
import com.we.jetpackmvvm.network.AppException
import com.we.jetpackmvvm.network.BaseResponse
import com.we.jetpackmvvm.network.ExceptionHandle
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import test.juyoufuli.com.myapplication.app.MvRxViewModel

/**
 * @Author : dongfang
 * @Created Time : 2023-08-23  14:44
 * @Description:
 */

