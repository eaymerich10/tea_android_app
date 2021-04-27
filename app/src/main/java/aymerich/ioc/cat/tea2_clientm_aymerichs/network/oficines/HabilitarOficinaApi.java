package aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;

/**
 *
 * Classe per fer la request al servidor per editar una oficina
 *
 */
public class HabilitarOficinaApi {

    String url = "";
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String codiAcces = "";
    private String idOficina = "";
    private String habilitada = "";

    /**
     * Instantiates a new Habilitar oficina api.
     *
     * @param context    the context
     * @param url        the url
     * @param codiAcces  the codi acces
     * @param idOficina  the id oficina
     * @param habilitada the habilitada
     * @throws JSONException the json exception
     */
    public HabilitarOficinaApi(Context context,  String url, String codiAcces, String idOficina, String habilitada) throws JSONException {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
        this.idOficina = idOficina;
        this.habilitada = habilitada;
    }

    /**
     * Habilitar oficina.
     */
    public void HabilitarOficina() {

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject oficina = new JSONObject();
        try {

            oficina.put("idOficina", idOficina);
            oficina.put("habilitada", habilitada);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = oficina.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "editaroficina/" + codiAcces,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG_RESPONSE", response);
                        Log.i("LOG_RESPONSE", idOficina);
                        switch (codi) {
                            case 200:
                                Toast.makeText(context, "Oficina Modificada", Toast.LENGTH_SHORT).show();
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

        queue.add(stringRequest);
    }
}

