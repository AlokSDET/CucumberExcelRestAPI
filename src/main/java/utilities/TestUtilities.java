package utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class TestUtilities {

    public static JSONObject createJSONObject(Object... keyValuePair) throws JSONException {
        if (keyValuePair.length % 2 == 0) {
            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < keyValuePair.length; i++) {
                if (keyValuePair[i] == null) {
                    throw new NullPointerException("JSONObject key cannot be null");
                }
                jsonObject.put((String) keyValuePair[i], keyValuePair[i + 1]);
                i++;
            }
            return jsonObject;
        }
        return null;
    }
    
}
