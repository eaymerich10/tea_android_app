package aymerich.ioc.cat.tea2_clientm_aymerichs.network.users;

import android.content.Context;
import android.content.Intent;
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

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.users.Admin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.users.User;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * LoginApi class
 * Controla les funcions per iniciar sessió al servidor, fent una request i tractant la resposta
 */
public class LoginApi {
    private String username;
    private String password;
    private String url;
    private String codiAcces = "";
    private String rol = "";
    private Context context;
    private Bundle bundle = new Bundle();
    private int codi = 0;
    private int codiError = 0;
    private String resposta = "";

    /**
     * Instantiates a new Login api.
     *
     * @param context  the context
     * @param username the username
     * @param password the password
     * @param url      the url
     * @throws JSONException the json exception
     */
    public LoginApi(Context context, String username, String password, String url) throws JSONException {
        this.context = context;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    /**
     * Login.
     * Llença una request al servidor i tracta la resposta per obrir una activity admin o user
     */
    public void Login() {
        try {
            String url_login = url + "login/"; //URL actualitzada
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
                    Log.i("LOG_RESPONSE", rol);
                    switch (codi) {
                        case 200:
                            if (rol.equals("ADMINISTRADOR")) {
                                Toast.makeText(context, "Sessió inciada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Admin.class);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putExtra("url", url);
                                intent.putExtra("rol", rol);
                                startActivity(context, intent, bundle);
                            } else {
                                Toast.makeText(context, "Sessió inciada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, User.class);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putExtra("url", url);
                                intent.putExtra("rol", rol);
                                startActivity(context, intent, bundle);
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
                        codiError = error.networkResponse.statusCode;
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
                        resposta = new String(response.data);
                        try {
                            JSONObject jsonObject = new JSONObject(resposta);
                            codiAcces = jsonObject.get("codiAcces").toString();
                            rol = jsonObject.get("rolUsuari").toString();
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

            /* Alternativa que tracta una resposta en objecte json

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            JSONObject object = new JSONObject();
            try {
                //input your API parameters
                object.put("email",username);
                object.put("contrasenya",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Enter the correct url for your api service site
            //String url = getResources().getString(R.string.url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            tv_response.setText("String Response : "+ response.toString());
                            Log.i("LOG_RESPONSE", response.toString());
                            Log.i("LOG_RESPONSE", codiAcces);
                            sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            switch (codi) {
                                case 200:
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    editor.putString(Sp_Status, "LoggedIn");
                                    editor.commit();
                                    startActivity(new Intent(MainActivity.this, Admin.class));
                                    finish();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tv_response.setText("Error getting response");
                }
            }){



                @Override
                protected Response parseNetworkResponse(NetworkResponse response) {
                    codi = response.statusCode;
                    return super.parseNetworkResponse(response);
                }

        };
        requestQueue.add(jsonObjectRequest);


} catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
