package aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures;

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

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures.LlistatFacturesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures.LlistatFacturesClient;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Factura;

import static androidx.core.content.ContextCompat.startActivity;

public class LlistarFacturesApi {

    private String url;
    private String codiAcces;
    private String rol;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> facturesFinal = new ArrayList<String>();
    ArrayList<String> facturesString = new ArrayList<String>();
    ArrayList<String> idFactures = new ArrayList<String>();

    /**
     * Instantiates a new Llistar reserves api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     * @param rol       the rol
     */
    public LlistarFacturesApi(Context context, String url, String codiAcces, String rol) {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
        this.rol = rol;
    }

    /**
     * Llistar.
     */
    public void llistar() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "factures/" + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("LOG_RESPONSE", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                Factura factura = new Factura(null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null
                                        );
                                factura.setIdFactura(jresponse.getString("idFactura"));
                                factura.setDataCreacio(jresponse.getString("dataCreacio"));
                                factura.setDataIniciReserva(jresponse.getJSONObject("idReserva").getString("dataIniciReserva"));
                                factura.setDataFinalReserva(jresponse.getJSONObject("idReserva").getString("dataFiReserva"));
                                factura.setImpostos(jresponse.getString("impostos"));
                                factura.setNomOficina(jresponse.getJSONObject("idReserva").getJSONObject("idOficina").getString("nom"));
                                factura.setNomUsuariReserva(jresponse.getJSONObject("idReserva").getJSONObject("idUsuari").getString("nom"));
                                factura.setIdReserva(jresponse.getJSONObject("idReserva").getString("idReserva"));
                                factura.setPreuOficina(jresponse.getJSONObject("idReserva").getJSONObject("idOficina").getString("preu"));
                                factura.setTipusOficina(jresponse.getJSONObject("idReserva").getJSONObject("idOficina").getString("tipus"));
                                factura.setServeisOficina(jresponse.getJSONObject("idReserva").getJSONObject("idOficina").getString("serveis"));
                                factura.setSubTotal(jresponse.getString("subTotal"));
                                factura.setTotal(jresponse.getString("total"));
                                factura.setIdUsuari(jresponse.getJSONObject("idReserva").getJSONObject("idUsuari").getString("idUsuari"));
                                facturesString.add(jresponse.toString());
                                idFactures.add(factura.getIdFactura());
                                facturesFinal.add("ID Factura " + factura.getIdReserva() + " Nom Oficina " + factura.getNomOficina() + "\n ID Usuari " + factura.getIdUsuari());
                            }
                            if (rol.equals("ADMINISTRADOR")) {
                                Intent intent = new Intent(context, LlistatFacturesAdmin.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("facturesFinal", facturesFinal);
                                intent.putStringArrayListExtra("idFactures", idFactures);
                                intent.putStringArrayListExtra("facturesString", facturesString);
                                startActivity(context, intent, bundle);
                            } else {
                                Intent intent = new Intent(context, LlistatFacturesClient.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("facturesFinal", facturesFinal);
                                intent.putStringArrayListExtra("idFactures", idFactures);
                                intent.putStringArrayListExtra("facturesString", facturesString);
                                startActivity(context, intent, bundle);
                            }

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
