package test.juyoufuli.com.myapplication.app.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.we.jetpackmvvm.base.appContext
import com.we.jetpackmvvm.network.BaseNetworkApi
import com.we.jetpackmvvm.network.interceptor.CacheInterceptor
import com.we.jetpackmvvm.network.interceptor.logging.LogInterceptor

import okhttp3.Cache
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.juyoufuli.com.myapplication.mvp.api.Api
import test.juyoufuli.com.myapplication.mvp.api.service.MainService
import java.io.File
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit


/**
 * @Author : dongfang
 * @Created Time : 2021-12-08  15:09
 * @Description:
 */

//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
//地址二
val apiService: MainService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(MainService::class.java, Api.APP_DOMAIN)
}

class NetworkApi : BaseNetworkApi() {
    companion object {
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "cxk_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(MyHeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(LogInterceptor())
            // token拦截器
//            addInterceptor(TokenInterceptor())
            //超时时间 连接、读、写
            connectTimeout(50, TimeUnit.SECONDS)
            readTimeout(50, TimeUnit.SECONDS)
            writeTimeout(50, TimeUnit.SECONDS)
//            proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("49.4.3.101", 30199)))
//            proxyAuthenticator { route, response -> //设置代理服务器账号密码
//                val credential: String = Credentials.basic("adhcU1iP", "94lqpvbac")
//                response.request().newBuilder()
//                    .header("Proxy-Authorization", credential)
//                    .build()
//            }
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }

    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }
}