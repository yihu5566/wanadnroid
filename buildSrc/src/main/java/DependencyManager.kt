/**
 * 依赖版本号
 *
 * @author Qu Yunshuo
 * @since 4/24/21 4:01 PM
 */
object Version {
    // AndroidX--------------------------------------------------------------
    const val AppCompat = "1.3.1"
    const val CoreKtx = "1.7.0"
    const val ConstraintLayout = "2.1.3"                // 约束布局
    const val NavigationKtx = "2.3.5"
    const val FragmentKtx = "1.4.1"
    const val DataStore = "1.0.0"
    const val Swiperefreshlayout = "1.1.0"

    // Lifecycle相关（ViewModel & LiveData & Lifecycle）
    const val Lifecycle = "2.4.1"
    const val LifecycleExtensions = "2.2.0"
    const val MultiDex = "2.0.1"
    const val TestExtJunit = "1.1.3"
    const val TestEspresso = "3.3.0"

    // Android---------------------------------------------------------------
    const val Junit = "4.13.2"
    const val Material = "1.5.0-alpha01"                        // 材料设计UI套件

    // Kotlin----------------------------------------------------------------
    const val Kotlin = "1.6.21"

    // GitHub----------------------------------------------------------------
    const val OkHttp = "4.9.0"                          // OkHttp
    const val OkHttpInterceptorLogging = "4.9.1"        // OkHttp 请求Log拦截器
    const val Retrofit = "2.9.0"                        // Retrofit
    const val RetrofitConverterGson = "2.9.0"           // Retrofit Gson 转换器
    const val Gson = "2.8.7"                            // Gson
    const val MMKV = "1.2.9"                            // 腾讯 MMKV 替代SP
    const val AutoSize = "1.2.1"                        // 屏幕适配
    const val ARoute = "1.5.1"                          // 阿里路由
    const val ARouteCompiler = "1.5.1"                  // 阿里路由 APT
    const val RecyclerViewAdapter = "3.0.4"             // RecyclerViewAdapter
    const val EventBus = "3.2.0"                        // 事件总线
    const val PermissionX = "1.4.0"                     // 权限申请
    const val LeakCanary = "2.7"                        // 检测内存泄漏
    const val AutoService = "1.0"                       // 自动生成SPI暴露服务文件
    const val skin = "3.4.0"                            // 换肤
    const val UnpeekLivedata = "7.2.0-beta1"            // 防止数据倒灌

}

/**
 * AndroidX相关依赖
 *
 * @author Qu Yunshuo
 * @since 4/24/21 4:01 PM
 */
object AndroidX {
    const val AppCompat = "androidx.appcompat:appcompat:${Version.AppCompat}"
    const val Swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Version.Swiperefreshlayout}"
    const val ConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.ConstraintLayout}"
    const val NavigationKtx = "androidx.navigation:navigation-ui-ktx:${Version.NavigationKtx}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Version.NavigationKtx}"
    const val FragmentKtx = "androidx.fragment:fragment-ktx:${Version.FragmentKtx}"
    const val CoreKtx = "androidx.core:core-ktx:${Version.CoreKtx}"
    const val ViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Lifecycle}"
    const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Lifecycle}"
    const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Lifecycle}"
    const val LifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Version.Lifecycle}"
    const val LifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Version.LifecycleExtensions}"
    const val DataStore = "androidx.datastore:datastore-preferences:${Version.DataStore}"
    const val Material = "com.google.android.material:material:${Version.Material}"
    const val MultiDex = "androidx.multidex:multidex:${Version.MultiDex}"
    const val Junit = "junit:junit:${Version.Junit}"
    const val TestExtJunit = "androidx.test.ext:junit:${Version.TestExtJunit}"
    const val TestEspresso = "androidx.test.espresso:espresso-core:${Version.TestEspresso}"

}

/**
 * Kotlin相关依赖
 *
 * @author Qu Yunshuo
 * @since 4/24/21 4:02 PM
 */
object Kotlin {
    const val StdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib:${Version.Kotlin}"
    const val KotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.Kotlin}"
}

/**
 * GitHub及其他相关依赖
 *
 * @author Qu Yunshuo
 * @since 4/24/21 4:02 PM
 */
object GitHub {
    const val reflex = "com.github.CoderAlee:Reflex:1.2.0"
    const val paintedskin = "com.github.CoderAlee.PaintedSkin:painted-skin:${Version.skin}"
    const val skin_standard_plugin = "com.github.CoderAlee.PaintedSkin:standard-plugin:${Version.skin}"
    const val skin_constraintlayout_compat =
        "com.github.CoderAlee.PaintedSkin:constraintlayout-compat:${Version.skin}"
    const val wheelPicker = "com.github.gzu-liyujiang.AndroidPicker:WheelPicker:4.1.7"
    const val calendarView = "com.haibin:calendarview:3.7.1"
    const val bannerViewPager = "com.github.zhpanvip:bannerviewpager:3.5.6"
    const val crash = "cat.ereza:customactivityoncrash:2.4.0"

    const val glide = "com.github.bumptech.glide:glide:4.12.0"
    const val glideComplier = "com.github.bumptech.glide:compiler:4.13.0"

    //https://github.com/wasabeef/glide-transformations
    const val grideTransformations = "jp.wasabeef:glide-transformations:4.3.0"
    const val autoSize = "com.github.JessYanCoding:AndroidAutoSize:v1.2.1"
    const val loadSir = "com.kingja.loadsir:loadsir:1.3.8"
    const val cymChadAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"
    const val mmkv = "com.tencent:mmkv:1.0.22"
    const val utilCodex = "com.blankj:utilcodex:1.30.6"
    const val xPermissions = "com.github.getActivity:XXPermissions:13.2"
    const val a3dMap = "com.amap.api:3dmap:8.1.0"
    const val aMapLocation = "com.amap.api:location:5.6.2"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofit2Gson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val PersistentCookieJar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"
    const val retrofitUrlManager = "me.jessyan:retrofit-url-manager:1.4.0"
    const val koin = "io.insert-koin:koin-android:3.1.2"
    const val koinCompat = "io.insert-koin:koin-android-compat:3.1.2"
    const val aliPush = "com.aliyun.ams:alicloud-android-push:3.7.3"
    const val scanQr = "com.huawei.hms:scanplus:2.3.0.300"
    const val basePopup = "io.github.razerdp:BasePopup:3.2.0"
    const val photoView = "com.github.chrisbanes:PhotoView:2.3.0"
    const val wchatPay = "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:+"
    const val qq = "com.tencent.tauth:qqopensdk:3.52.0"
    const val pictureSelector = "io.github.lucksiege:pictureselector:v3.0.9"
    const val pictureSelectorCamerax = "io.github.lucksiege:camerax:v3.0.9'"
    const val UnpeekLivedata = "com.kunminx.arch:unpeek-livedata:${Version.UnpeekLivedata}"
    const val Jsoup = "org.jsoup:jsoup:1.14.3"
    const val XUpdate = "com.github.xuexiangjys:XUpdate:2.0.9"
    const val XUpdateAPI = "com.github.xuexiangjys.XUpdateAPI:xupdate-easy:1.0.1"
}

