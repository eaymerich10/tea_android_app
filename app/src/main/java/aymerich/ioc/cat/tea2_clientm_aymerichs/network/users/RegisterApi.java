package aymerich.ioc.cat.tea2_clientm_aymerichs.network.users;

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

/**
 * RegisterApi class
 * Tracta les funcions per crear un nou usuari al servidor
 */
public class RegisterApi {
    private String email, password, nom, cifEmp, direccio, poblacio, provincia, rol;
    String url = "";
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String codiAcces = "";

    /**
     * Instantiates a new Register api.
     *
     * @param context   the context
     * @param email     the email
     * @param password  the password
     * @param nom       the nom
     * @param cifEmp    the cif emp
     * @param direccio  the direccio
     * @param poblacio  the poblacio
     * @param provincia the provincia
     * @param rol       the rol
     * @param url       the url
     * @throws JSONException the json exception
     */
    public RegisterApi(Context context, String email, String password, String nom, String cifEmp, String direccio, String poblacio, String provincia, String rol, String url) throws JSONException {
        this.context = context;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.cifEmp = cifEmp;
        this.direccio = direccio;
        this.poblacio = poblacio;
        this.provincia = provincia;
        this.rol = rol;
        this.url = url;

    }

    /**
     * Register.
     * Llença una request al servidor per crear un nou usuari i tracta la resposta
     */
    public void Register() {
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

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "registre/", new Response.Listener<String>() {
                /**
                 * On response.
                 *
                 * @param response the response
                 */
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_RESPONSE", response);
                    Log.i("LOG_RESPONSE", codiAcces);
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
