package Tiimae.TiimaeBot.service;

import kong.unirest.Unirest;

public class ApiCallServices {
    private static ApiCallServices singleInstance = null;

    private ApiCallServices() {
        Unirest.config().defaultBaseUrl("http://localhost:8080/api/1.0/");
    }

    public static ApiCallServices getInstance() {
        if (singleInstance == null) {
            singleInstance = new ApiCallServices();
        }

        return singleInstance;
    }

    public kong.unirest.HttpRequestWithBody post(String url) {

        return Unirest.post(url).header("accept", "application/json").header("Content-Type", "application/json");
    }

    public kong.unirest.GetRequest get(String url) {
        var defaultHeaders = Unirest.get(url).header("accept", "application/json").header("Content-Type", "application/json");

        return defaultHeaders;
    }

    public kong.unirest.HttpRequestWithBody put(String url) {
        var defaultHeaders = Unirest.put(url).header("accept", "application/json").header("Content-Type", "application/json");

        return defaultHeaders;
    }
}
