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

import static org.junit.Assert.*;

public class RegisterApiTest {

    final CountDownLatch signal = new CountDownLatch(1);
    private String email, password, nom, cifEmp, direccio, poblacio, provincia, rol;
    String url = "";
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String codiAcces = "";

    @Before
    public void setUp() throws Exception {
        email = "userMock@email.com";
        password = "1234";
        nom = "user";
        cifEmp = "unCif";
        direccio = "unaDireccio";
        poblacio = "unaPoblacio";
        provincia = "unaProvincia";
        rol = "1";
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/registre";
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void register() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("contrasenya", password);
            jsonBody.put("nom", nom);
            jsonBody.put("cifEmpresa", cifEmp);
            jsonBody.put("direccio", direccio);
            jsonBody.put("poblacio", poblacio);
            jsonBody.put("provincia", provincia);
            jsonBody.put("rol", rol);
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                            Toast.makeText(context, "Usuari registrat", Toast.LENGTH_SHORT).show();
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
                        codiError = error.networkResponse.statusCode;
                        if (codiError == 400) {
                            Toast.makeText(context, "L'usuari ja existeix!", Toast.LENGTH_SHORT).show();
                        }

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