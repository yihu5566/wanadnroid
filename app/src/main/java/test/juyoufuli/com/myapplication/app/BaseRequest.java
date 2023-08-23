package test.juyoufuli.com.myapplication.app;


import com.we.jetpackmvvm.network.BaseResponse;


import java.util.function.Function;

import test.juyoufuli.com.myapplication.app.exception.OnHttpDataNullException;
import test.juyoufuli.com.myapplication.app.exception.OnHttpServiceException;

/**
 * 作者: ludf
 * 创建时间: 2018-09-26 13:22
 * 类名称: BaseRequest
 * 描述: 请求返回的数据统一封装处理
 */
public class BaseRequest<T> implements Function<BaseResponse<T>, T> {


    /**
     * 统一处理 code 和 msg    只返回对应的data給各自的业务
     *
     * @param responseEntity
     * @return
     */
    @Override
    public T apply(BaseResponse<T> responseEntity) {
//        LogUtils.d("call");
        if (0 == responseEntity.getResponseCode()) {
            if (responseEntity.getResponseData() == null) {
                throw new OnHttpDataNullException(responseEntity.getResponseCode() + "::" + "http response data not be null");
            } else {
                return responseEntity.getResponseData();
            }
        } else {
            throw new OnHttpServiceException(responseEntity.getResponseCode() + "::" + responseEntity.getResponseMsg());
        }
    }
}
