package aymerich.ioc.cat.tea2_clientm_aymerichs.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CanviPassApiTest {

    final CountDownLatch signal = new CountDownLatch(1);
    private String url, codi, pass;
    private Context context;
    private String testResponse;

    @Before
    public void setUp() throws Exception {
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/canvipass/";
        codi = "codimock";
        pass = "1234";
        context = InstrumentationRegistry.getInstrumentation().getContext();

    }

    @Test
    public void canvi() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("contrasenya", pass);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + codi, new Response.Listener<String>() {
                /**
                 * On response.
                 *
                 * @param response the response
                 */
                @Override
                public void onResponse(String response) {
                    signal.countDown();
                    testResponse = response;
                    Toast.makeText(context, "Contrasenya modificada.", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                /**
                 * On error response.
                 *
                 * @param error the error
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_RESPONSE", error.toString());
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                /**
                 * Gets body content type.
                 *
                 * @return the body content type
                 */
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                /**
                 * Get body byte [ ].
                 *
                 * @return the byte [ ]
                 */
                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                /**
                 * Parse network response response.
                 *
                 * @param response the response
                 * @return the response
                 */
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }

                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        try {
            signal.await(5, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", "200", testResponse);
    }
}