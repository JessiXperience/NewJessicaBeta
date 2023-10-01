package jessica.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class JsonUtils {
	public static Gson gson;
    public static Gson prettyGson;
    public static JsonParser jsonParser;
    
    static {
        JsonUtils.gson = new Gson();
        JsonUtils.prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonUtils.jsonParser = new JsonParser();
    }
}
