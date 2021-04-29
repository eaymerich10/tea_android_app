package aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves;

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

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.LlistaReservesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.LlistatReservesUser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Reserva;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * Classe per fer la request al servidor per llistar les reserves
 */
public class LlistarReservesApi {

    private String url;
    private String codiAcces;
    private String rol;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> reservesFinal = new ArrayList<String>();
    ArrayList<String> reservesString = new ArrayList<String>();
    ArrayList<String> idReserves = new ArrayList<String>();

    /**
     * Instantiates a new Llistar reserves api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     * @param rol       the rol
     */
    public LlistarReservesApi(Context context, String url, String codiAcces, String rol) {
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "reserves/" + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("LOG_RESPONSE", response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                Reserva reserva = new Reserva(null, null, null);
                                reserva.setIdReserva(jresponse.getString("idReserva"));
                                reserva.setDataIniciReserva(jresponse.getString("dataIniciReserva"));
                                reserva.setDataFinalReserva(jresponse.getString("dataFiReserva"));
                                reserva.setIdOficina(jresponse.getJSONObject("idOficina").getString("idOficina"));
                                reserva.setIdUsuari(jresponse.getJSONObject("idUsuari").getString("idUsuari"));
                                reservesString.add(jresponse.toString());
                                idReserves.add(reserva.getIdReserva());
                                reservesFinal.add("ID FacturaDetailFragment " + reserva.getIdReserva() + " ID Oficina " + reserva.getIdOficina() + " ID Usuari " + reserva.getIdUsuari());
                            }
                            if (rol.equals("ADMINISTRADOR")) {
                                Intent intent = new Intent(context, LlistaReservesAdmin.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("reservesFinal", reservesFinal);
                                intent.putStringArrayListExtra("idReserves", idReserves);
                                intent.putStringArrayListExtra("reservesString", reservesString);
                                startActivity(context, intent, bundle);
                            } else {
                                Intent intent = new Intent(context, LlistatReservesUser.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("reservesFinal", reservesFinal);
                                intent.putStringArrayListExtra("idReserves", idReserves);
                                intent.putStringArrayListExtra("reservesString", reservesString);
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

