package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class APIDataProviders {

    @DataProvider(name = "getList")
    public Object[][] getList(Method method) {
        return getExpectedList("api/" + declaringClass(method), method.getName());
    }

    private Object[][] getExpectedList(String folder, String dataProviderName) {
        List<List<Object>> dataList = new ArrayList<>();
        JsonArray jsonArray;
        try {
            jsonArray = JsonParser.parseString(readFile("src/main/resources/" + folder + "/" +
                    dataProviderName + ".json")).getAsJsonArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            List<Object> elementList = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                JsonElement element = jsonObject.get(key);
                if (element.isJsonArray()) {
                    elementList.add(element);
                } else {
                    elementList.add(element.getAsString());
                }
            }
            dataList.add(elementList);
        }
        return dataList.stream().map(List::toArray).toArray(Object[][]::new);
    }

    private String declaringClass(Method method) {
        String declaringClass = method.getDeclaringClass().getName();
        declaringClass = declaringClass.substring(declaringClass.lastIndexOf(".") + 1);
        declaringClass = declaringClass.substring(0, 1).toLowerCase() + declaringClass.substring(1);
        return declaringClass;
    }

    private String readFile(String filePath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
