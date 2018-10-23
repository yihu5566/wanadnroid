package test.juyoufuli.com.myapplication.app.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类名：JsonUtils
 *
 * @author wuxin<br/>
 *         实现的主要功能:
 *         创建日期：16/7/20
 *         修改者，修改日期16/7/20，修改内容。
 */
public class JsonUtils {
    private static Gson gson = null;
    private static Gson gsonNoNulls = null;

    static {
        if (gson == null) {
            gson = new GsonBuilder()
                    .serializeNulls()
                    .registerTypeAdapterFactory(new NullTypeToEmptyAdapterFactory<>())
                    .create();
        }
        if (gsonNoNulls == null) {
            gsonNoNulls = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullTypeToEmptyAdapterFactory<>())
                    .create();
        }
    }


    /**
     * 获取Gson实例
     *
     * @return gson
     */
    public static Gson getGsonInstance () {
        return gson;
    }

    /**
     * 转成json
     *
     * @param object
     *
     * @return json
     */
    public static String toJson (Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    public static String toJsonNoSerializeNulls (Object object) {
        String gsonString = null;
        if (gsonNoNulls != null) {
            gsonString = gsonNoNulls.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param jsonString
     * @param cls
     *
     * @return
     */
    public static <T> T fromJsonToBean (String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
//        JSON.toJavaObject(JSON.parseObject(jsonString),cls);
        return t;
    }

    /**
     * 转成list
     *
     * @param jsonString
     * @param cls
     *
     * @return
     */
    public static <T> List<T> fromJsonToList (String jsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
//            list = gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType());
            list = new ArrayList<>();
            JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
//        list = JSON.parseArray(jsonString,cls);
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param jsonString
     *
     * @return
     */
    public static <T> List<Map<String, T>> fromJsonToListMaps (String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param jsonString
     *
     * @return
     */
    public static <T> Map<String, T> fromJsonToMaps (String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    public static Map<String, String> fromBeanToMap (Object o) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String proName = field.getName();
            String proValue = null;
            try {
                proValue = String.valueOf(field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            LogUtils.d("key：" + proName + " , value:" + proValue);
            if (!TextUtils.isEmpty(proValue) && !proValue.equals("null"))
                map.put(proName, proValue);
        }
        return map;
    }

}
