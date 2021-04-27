package aymerich.ioc.cat.tea2_clientm_aymerichs.network.users;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

import static org.junit.Assert.assertEquals;

public class LoginApiTestOK {

    final CountDownLatch signal = new CountDownLatch(1);
    private String username;
    private String password;
    private String url;
    private boolean esAdmin;
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private String codiAcces = "";
    Context appContext = InstrumentationRegistry.getInstrumentation().getContext();

    @Before
    public void setUp() throws Exception {
        username = "admin@email.com";
        password = "1234";
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/login";
        esAdmin = true;
        context = appContext;
        codi = 0;
    }

    @Test
    public void login() {
        try {
            String url_login = url; //URL actualitzada
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject(); //construcció del objecte json
            jsonBody.put("email", username);
            jsonBody.put("contrasenya", password);
            final String mRequestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
                /**
                 * On response.
                 *
                 * @param response the response
                 */
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                    Log.i("LOG_RESPONSE", codiAcces);
                    signal.countDown();
                    switch (codi) {
                        case 200:
                            if (esAdmin == true) {

                            } else {

                            }


                    }
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
                    if (error instanceof NetworkError) {
                        //handle your network error here.
                        Toast.makeText(context, "Login Error 1", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ServerError) {
                        //handle if server error occurs with 5** status code
                        int codiError = error.networkResponse.statusCode;
                        if (codiError == 400) {
                            Toast.makeText(context, "L'usuari no existeix!", Toast.LENGTH_SHORT).show();
                        }


                    } else if (error instanceof AuthFailureError) {
                        //handle if authFailure occurs.This is generally because of invalid credentials
                        Toast.makeText(context, "L'email o contrasenya no coincideixen", Toast.LENGTH_SHORT).show();


                    } else if (error instanceof TimeoutError) {
                        //handle if socket time out is occurred.
                        Toast.makeText(context, "Error de conexió amb el servidor", Toast.LENGTH_SHORT).show();

                    }
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
                 * @throws AuthFailureError the auth failure error
                 */
                @Override
                public byte[] getBody() throws AuthFailureError {
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
                        codiAcces = new String(response.data);
                        codi = response.statusCode;
                    }

                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            signal.await(5, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Test Correcte", 200, codi);
    }

}
