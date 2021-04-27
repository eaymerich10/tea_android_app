package aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines;

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

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines.LlistaOficines;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;

import static androidx.core.content.ContextCompat.startActivity;
import static org.junit.Assert.assertEquals;

public class LlistatOficinesApiTest {

    private CountDownLatch signal = new CountDownLatch(1);
    private String url;
    private String codiAcces;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> salesFinal = new ArrayList<String>();
    ArrayList<String> oficinesString = new ArrayList<String>();
    ArrayList<String> idSales = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/llistaroficines";
        codiAcces = "codiMock";

    }

    @Test
    public void llistat() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("LOG_RESPONSE", response.toString());
                        signal.countDown();
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
        try {
            signal.await(10, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", 200, codi);
    }
}