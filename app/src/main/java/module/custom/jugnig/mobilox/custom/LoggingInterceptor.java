package module.custom.jugnig.mobilox.custom;


import android.util.Log;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d(TAG, String.format("Sending request %s on %s%n%s%s%s",
                request.url(), chain.connection(), request.headers(),request.body().contentLength(),request.body().contentType()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        Log.d(TAG, "Response code---" + response.code());
        if (response.code() == 200)
            Log.d(TAG, "response--" + response.body().contentType().toString());
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), response.body().string()))
                .build();
    }
}
