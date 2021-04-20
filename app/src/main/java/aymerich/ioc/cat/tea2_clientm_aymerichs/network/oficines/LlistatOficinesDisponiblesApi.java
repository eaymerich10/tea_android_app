package aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines.LlistaOficinesDisponibles;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;

import static androidx.core.content.ContextCompat.startActivity;

/**
 *
 * Classe per fer la request al servidor per llistar les oficines disponibles entre dos dates
 *
 */
public class LlistatOficinesDisponiblesApi {

    private String url;
    private String codiAcces;
    private String dataInici;
    private String dataFi;
    private int codi = 0;
    private int codiError = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> salesFinal = new ArrayList<String>();
    ArrayList<String> oficinesString = new ArrayList<String>();
    ArrayList<String> idSales = new ArrayList<String>();

    /**
     * Instantiates a new Llistat oficines disponibles api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     * @param dataInici the data inici
     * @param dataFi    the data fi
     */
    public LlistatOficinesDisponiblesApi(Context context, String url, String codiAcces, String dataInici, String dataFi) {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
        this.dataInici = dataInici;
        this.dataFi = dataFi;
    }

    /**
     * Llistat.
     */
    public void llistat() {
        try{
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("dataInici", dataInici);
        jsonBody.put("dataFi", dataFi);
        final String mRequestBody = jsonBody.toString();

        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url + codiAcces,
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
                            Intent intent = new Intent(context, LlistaOficinesDisponibles.class);
                            intent.putExtra("url", url);
                            intent.putExtra("codiAcces", codiAcces);
                            intent.putStringArrayListExtra("salesFinal", salesFinal);
                            intent.putStringArrayListExtra("idSales", idSales);
                            intent.putStringArrayListExtra("oficinesString", oficinesString);
                            intent.putExtra("dataIniciReserva", dataInici);
                            intent.putExtra("dataFiReserva", dataFi);
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

        };



        requestQueue.add(jsonArrayRequest);
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

}

