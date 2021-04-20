package aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.AuthFailureError;
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

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines.OficinesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;

import static org.junit.Assert.assertEquals;

public class EditarOficinaApiTest {

    final CountDownLatch signal = new CountDownLatch(1);
    private String nom, tipus, capacitat, preu, serveis, direccio, poblacio, provincia;
    String url = "";
    private Oficina oficina = null;
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String codiAcces = "";
    private String idOficina = "";


    @Before
    public void setUp() throws Exception {
        nom = "ofiTest";
        tipus = "OFICINA_PRIVADA";
        capacitat = "10";
        preu = "29.95";
        serveis = "WIFI, ETHERNET";
        direccio = "unaDireccio";
        poblacio = "Barcelona";
        provincia = "Barcelona";
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/";
        codiAcces = "codiMock";
        context = InstrumentationRegistry.getInstrumentation().getContext();
        codi = 0;
    }

    @Test
    public void EditarOficina() {
        int capaInt = Integer.valueOf(capacitat);
        double preuD = Double.valueOf(preu);


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject oficina = new JSONObject();
            oficina.put("idOficina", idOficina);
            oficina.put("nom", nom);
            oficina.put("tipus", tipus);
            oficina.put("capacitat", capaInt);
            oficina.put("preu", preuD);
            oficina.put("serveis", serveis);
            oficina.put("provincia", provincia);
            oficina.put("poblacio", poblacio);
            oficina.put("direccio", direccio);
            final String mRequestBody = oficina.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "editaroficina/" + codiAcces, new Response.Listener<String>() {
                /**
                 * On response.
                 *
                 * @param response the response
                 */
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                    Log.i("LOG_RESPONSE", idOficina);
                    signal.countDown();
                    switch (codi) {
                        case 200:
                            Toast.makeText(context, "Oficina Modificada", Toast.LENGTH_SHORT).show();
//                            EditarOficina.fa.finish();
                            Intent intent = new Intent(context, OficinesAdmin.class);
                            intent.putExtra("codiAcces", codiAcces);
                            intent.putExtra("url", url);
                            //startActivity(context, intent, bundle);

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
            signal.await(10, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", 200, codi);
    }
}