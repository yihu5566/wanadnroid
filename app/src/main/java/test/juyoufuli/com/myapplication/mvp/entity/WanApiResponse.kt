package test.juyoufuli.com.myapplication.mvp.entity

import com.we.jetpackmvvm.network.BaseResponse

data class WanApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) :
    BaseResponse<T>() {

    override fun isSucces() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

}