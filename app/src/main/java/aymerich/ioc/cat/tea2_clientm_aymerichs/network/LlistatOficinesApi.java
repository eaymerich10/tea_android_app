package aymerich.ioc.cat.tea2_clientm_aymerichs.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.LlistaOficines;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * LogoutApi class
 * Controla les funcions per tancar sessió
 */
public class LlistatOficinesApi {

    private String url;
    private String codiAcces;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> salesFinal = new ArrayList<String>();
    ArrayList<String> oficinesString = new ArrayList<String>();
    ArrayList<String> idSales = new ArrayList<String>();

    /**
     * Instantiates a new Logout api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     */
    public LlistatOficinesApi(Context context, String url, String codiAcces) {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
    }

    /**
     * Logout.
     * Llença una request per tancar sessió i tracta la resposta
     */
    public void llistat() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("LOG_RESPONSE", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jresponse =
                                        response.getJSONObject(i);
                                Oficina oficina = new Oficina(null, null, null, null);
                                oficina.setIdOficina(jresponse.getString("idOficina"));
                                oficina.setNom(jresponse.getString("nom"));
                                oficina.setTipus(jresponse.getString("tipus"));
                                oficina.setCapacitat(jresponse.getString("capacitat"));
                                oficina.setHabilitada(jresponse.getString("habilitada"));
                                oficina.setEliminada(jresponse.getString("eliminat"));
                                oficina.setPreu(jresponse.getString("preu"));
                                oficina.setProvincia(jresponse.getString("provincia"));
                                oficina.setPoblacio(jresponse.getString("poblacio"));
                                oficina.setDireccio(jresponse.getString("direccio"));
                                oficina.setServeis(jresponse.getString("serveis"));
                                oficinesString.add(jresponse.toString());
                                idSales.add(oficina.getIdOficina());
                                salesFinal.add("Nom " + oficina.getNom() +  " Tipus " + oficina.getTipus() +" Preu " + oficina.getPreu()+ " Provincia " + oficina.getProvincia() +" Habilitada " + oficina.getHabilitada());
                            }
                            Intent intent = new Intent(context, LlistaOficines.class);
                            intent.putExtra("url", url);
                            intent.putExtra("codiAcces", codiAcces);
                            intent.putStringArrayListExtra("salesFinal", salesFinal);
                            intent.putStringArrayListExtra("idSales", idSales);
                            intent.putStringArrayListExtra("oficinesString", oficinesString);
                            startActivity(context, intent, bundle);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
                Toast.makeText(context, "Error",
                        Toast.LENGTH_SHORT).show();
            }
        }) {


        };
        requestQueue.add(jsonArrayRequest);


    }

}

