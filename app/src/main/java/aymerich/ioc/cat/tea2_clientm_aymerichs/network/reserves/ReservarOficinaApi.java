package aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import java.io.UnsupportedEncodingException;

import aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures.CrearFacturaApi;

/**
 *
 * Classe per fer la request al servidor per reservar una oficina
 *
 */
public class ReservarOficinaApi {
    private String idOficina, dataIniciReserva, dataFiReserva;
    String url = "";
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String codiAcces = "";
    private String idReserva = "";
    private String idUsuari = "";
    private String resposta = "";
    private CrearFacturaApi crearFacturaApi;

    /**
     * Instantiates a new Reservar oficina api.
     *
     * @param context          the context
     * @param idOficina        the id oficina
     * @param dataIniciReserva the data inici FacturaDetailFragment
     * @param dataFiReserva    the data fi FacturaDetailFragment
     * @param codiAcces        the codi acces
     * @param url              the url
     * @throws JSONException the json exception
     */
    public ReservarOficinaApi(Context context, String idOficina, String dataIniciReserva, String dataFiReserva, String codiAcces , String url) throws JSONException {
        this.context = context;
        this.idOficina = idOficina;
        this.dataIniciReserva = dataIniciReserva;
        this.dataFiReserva = dataFiReserva;
        this.codiAcces = codiAcces;
        this.url = url;

    }

    /**
     * Reservar.
     */
    public void Reservar() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("codiAcces", codiAcces);
            jsonBody.put("idOficina", idOficina);
            jsonBody.put("dataIniciReserva", dataIniciReserva);
            jsonBody.put("dataFiReserva", dataFiReserva);
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
                    Log.i("LOG_RESPONSE", resposta);
                    switch (codi) {
                        case 200:
                            Toast.makeText(context, "Oficina Reservada", Toast.LENGTH_SHORT).show();
                            crearFacturaApi = new CrearFacturaApi(context, idReserva, idUsuari, codiAcces, url);
                            crearFacturaApi.Facturar();
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
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                    } else if (error instanceof ServerError) {
                        //handle if server error occurs with 5** status code
                        codiError = error.networkResponse.statusCode;
                        if (codiError == 400) {
                            Toast.makeText(context, "La oficina ja existeix!", Toast.LENGTH_SHORT).show();
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
                        resposta = new String(response.data);
                        try {
                            JSONObject jsonObject = new JSONObject(resposta);
                            idReserva = jsonObject.get("idReserva").toString();
                            idUsuari = jsonObject.get("idUsuari").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    }
}
