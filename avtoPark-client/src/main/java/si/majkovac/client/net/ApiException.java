package si.majkovac.client.net;

public class ApiException extends RuntimeException {
    private final int statusCode;
    private final String body;

    public ApiException(int statusCode, String body) {
        super("API error: " + statusCode + " body=" + body);
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() { return statusCode; }
    public String getBody() { return body; }
}
