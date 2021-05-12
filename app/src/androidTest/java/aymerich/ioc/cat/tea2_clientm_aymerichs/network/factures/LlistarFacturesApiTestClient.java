package aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures.LlistatFacturesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures.LlistatFacturesClient;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Factura;

import static androidx.core.content.ContextCompat.startActivity;
import static org.junit.Assert.assertEquals;

public class LlistarFacturesApiTestClient {
    final CountDownLatch signal = new CountDownLatch(1);
    private String url;
    private String codiAcces;
    private String rol;
    private String testResponse;
    private String respostaEsperada;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> facturesFinal = new ArrayList<String>();
    ArrayList<String> facturesString = new ArrayList<String>();
    ArrayList<String> idFactures = new ArrayList<String>();
    @Before
    public void setUp() throws Exception {
        url="https://192.168.1.23:8443/";
        rol = "CLIENT";
        codiAcces = "01597ac5-f899-40fb-995b-5aea2ca07175";
        context = InstrumentationRegistry.getInstrumentation().getContext();

    }

    @Test
    public void llistar() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "factures/" + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        signal.countDown();
                        Log.i("LOG_RESPONSE", response.toString());
                        respostaEsperada = response.toString();
                        testResponse = response.toString();
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
                                facturesFinal.add("ID Factura " + factura.getIdFactura() + " Nom Oficina " + factura.getNomOficina() + "\n ID Usuari " + factura.getIdUsuari());
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
        try {

            signal.await(5, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", respostaEsperada, testResponse);

    }
}