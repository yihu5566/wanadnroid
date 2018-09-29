package test.juyoufuli.com.myapplication.mvp.api.service

import io.reactivex.Observable
import retrofit2.http.GET
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract

/**
 * Author : ludf
 * Created Time : 2018-09-28  15:24
 * Description:
 */
interface SystemDataService {
    @GET("tree/json")
    fun getSystemDataList(): Observable<SystemDataResponse>
}