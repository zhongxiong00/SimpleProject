package http.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import http.parse.DoubleDefault0Adapter;


/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： Gson解析单例
 **/
public class GsonUtil {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
            .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
            .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
            .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
            .registerTypeAdapter(Long.class, new LongDefault0Adapter())
            .registerTypeAdapter(long.class, new LongDefault0Adapter())
            .create();

    public static Gson getGson() {
        return gson;
    }

}
