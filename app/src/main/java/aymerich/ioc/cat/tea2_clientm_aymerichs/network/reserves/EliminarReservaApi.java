package aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 *
 * Classe per fer la request al servidor per eliminar una reserva
 *
 */
public class EliminarReservaApi {

    private String url;
    private String idReserva;
    private String codiAcces;
    private Context context;

    /**
     * Instantiates a new Eliminar reserva api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     * @param idReserva the id reserva
     */
    public EliminarReservaApi(Context context, String url, String codiAcces, String idReserva) {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
        this.idReserva = idReserva;
    }

    /**
     * Eliminar.
     */
    public void Eliminar() {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + codiAcces + "/" + idReserva,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response,
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error",
                        Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }
}

