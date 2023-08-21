## --------------------------------------------基本指令区--------------------------------------------#
#-ignorewarnings                                      # 是否忽略警告
#-optimizationpasses 5                               # 指定代码的压缩级别(在0~7之间，默认为5)
#-dontusemixedcaseclassnames                         # 是否使用大小写混合(windows大小写不敏感，建议加入)
#-dontskipnonpubliclibraryclasses                    # 是否混淆非公共的库的类
#-dontskipnonpubliclibraryclassmembers               # 是否混淆非公共的库的类的成员
#-verbose                                            # 混淆时是否记录日志(混淆后会生成映射文件)
#-printmapping proguardMapping.txt
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
#
## 添加支持的jar(引入libs下的所有jar包)
#-libraryjars libs(*.jar;)
#
## 将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute SourceFile
#
## 保持注解不被混淆
#-keepattributes *Annotation*
#-keep class * extends java.lang.annotation.Annotation {*;}
#
## 保持泛型不被混淆
#-keepattributes Signature
## 保持反射不被混淆
#-keepattributes EnclosingMethod
## 保持异常不被混淆
#-keepattributes Exceptions
## 保持内部类不被混淆
#-keepattributes Exceptions,InnerClasses
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
## --------------------------------------------默认保留区--------------------------------------------#
#
## 保持基本组件不被混淆
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#
##androidx包使用混淆
#-keep class com.google.android.material.** {*;}
#-keep class androidx.** {*;}
#-keep public class * extends androidx.**
#-keep interface androidx.** {*;}
#-dontwarn com.google.android.material.**
#-dontnote com.google.android.material.**
#-dontwarn androidx.**
## 保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## 保留自定义控件(继承自View)不被混淆
#-keep public class * extends android.view.View {
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
## 保留指定格式的构造方法不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
## 保留在Activity中的方法参数是view的方法(避免布局文件里面onClick被影响)
#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View);
#}
#
## 保持枚举 enum 类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
## 保持R(资源)下的所有类及其方法不能被混淆
#-keep class **.R$* { *; }
#
## 保持 Parcelable 序列化的类不被混淆(注：aidl文件不能去混淆)
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
#
## 需要序列化和反序列化的类不能被混淆(注：Java反射用到的类也不能被混淆)
#-keepnames class * implements java.io.Serializable
#
## 保持 Serializable 序列化的类成员不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## 保持 BaseAdapter 类不被混淆
#-keep public class * extends android.widget.BaseAdapter { *; }
## 保持 CusorAdapter 类不被混淆
##-keep public class * extends android.widget.CusorAdapter{ *; }
#
#################support###############
#-keep class android.support.** { *; }
#-keep interface android.support.** { *; }
#-dontwarn android.support.**
#
#-keep class com.google.android.material.** {*;}
#-keep class androidx.** {*;}
#-keep public class * extends androidx.**
#-keep interface androidx.** {*;}
#-dontwarn com.google.android.material.**
#-dontnote com.google.android.material.**
#-dontwarn androidx.**
## --------------------------------------------webView区--------------------------------------------#
## WebView处理，项目中没有使用到webView忽略即可
## 保持Android与JavaScript进行交互的类不被混淆
#-keep class **.AndroidJavaScript { *; }
#-keepclassmembers class * extends android.webkit.WebViewClient {
#     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
#     public boolean *(android.webkit.WebView,java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.WebChromeClient {
#     public void *(android.webkit.WebView,java.lang.String);
#}
#
## 网络请求相关
#-keep public class android.net.http.SslError
#
## --------------------------------------------定制区域区--------------------------------------------#
#
##---------------------------------1.实体类---------------------------------
#-keep class com.we.westarry.** { *; }
#-keep class com.we.jetpackmvvm.** { *; }
##实体
#-keep class com.we.westarry.data.model.bean.** { *; }
################# ViewBinding & DataBinding ###############
#-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
#  public static * inflate(android.view.LayoutInflater);
#  public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
#  public static * bind(android.view.View);
#}
#
#
##--------------------------------------------------------------------------
#
#################retrofit###############
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
##########banner###########
#-keep class androidx.recyclerview.widget.**{*;}
#-keep class androidx.viewpager2.widget.**{*;}
##皮肤库
#-keep class org.alee.** {*;}
##升级库
#-keep class com.xuexiang.xupdate.entity.** { *; }
#
##注意，如果你使用的是自定义Api解析器解析，还需要给你自定义Api实体配上混淆，如下是本demo中配置的自定义Api实体混淆规则：
#-keep class com.xuexiang.xupdatedemo.entity.** { *; }
##弹窗库
#-dontwarn razerdp.basepopup.**
#-keep class razerdp.basepopup.**{*;}
##华为扫码
#-ignorewarnings
#-keepattributes *Annotation*
#-keepattributes Exceptions
#-keepattributes InnerClasses
#-keepattributes Signature
#-keepattributes SourceFile,LineNumberTable
#-keep class com.huawei.hianalytics.**{*;}
#-keep class com.huawei.updatesdk.**{*;}
#-keep class com.huawei.hms.**{*;}
## Glide ---->
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
## for DexGuard only
##-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#
##阿里推送混淆¬
#-keepclasseswithmembernames class ** {
#    native <methods>;
#}
#-keepattributes Signature
#-keep class sun.misc.Unsafe { *; }
#-keep class com.taobao.** {*;}
#-keep class com.alibaba.** {*;}
#-keep class com.alipay.** {*;}
#-keep class com.ut.** {*;}
#-keep class com.ta.** {*;}
#-keep class anet.**{*;}
#-keep class anetwork.**{*;}
#-keep class org.android.spdy.**{*;}
#-keep class org.android.agoo.**{*;}
#-keep class android.os.**{*;}
#-keep class org.json.**{*;}
#-dontwarn com.taobao.**
#-dontwarn com.alibaba.**
#-dontwarn com.alipay.**
#-dontwarn anet.**
#-dontwarn org.android.spdy.**
#-dontwarn org.android.agoo.**
#-dontwarn anetwork.**
#-dontwarn com.ut.**
#-dontwarn com.ta.**
#
##图片选择-----------
#-keep class com.luck.picture.lib.** { *; }
#
## 如果引入了Camerax库请添加混淆
#-keep class com.luck.lib.camerax.** { *; }
#
##如果引入了Ucrop库请添加混淆
#-dontwarn com.yalantis.ucrop**
#-keep class com.yalantis.ucrop** { *; }
#-keep interface com.yalantis.ucrop** { *; }
#
##权限
#-keep class com.hjq.permissions.** {*;}
##日历
#-keepclasseswithmembers class * {
#public <init>(android.content.Context);
#}
#-keep class com.haibin.calendarview.** { *; }
## Gson
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.** {*;}
#-keep class com.google.**{*;}
#
#
#
#
