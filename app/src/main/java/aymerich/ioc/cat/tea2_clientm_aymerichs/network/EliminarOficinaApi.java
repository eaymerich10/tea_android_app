package aymerich.ioc.cat.tea2_clientm_aymerichs.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * LogoutApi class
 * Controla les funcions per tancar sessió
 */
public class EliminarOficinaApi {

    private String url;
    private String idOficina;
    private String codiAcces;
    private Context context;

    /**
     * Instantiates a new Logout api.
     *
     * @param context   the context
     * @param url       the url
     * @param codiAcces the codi acces
     */
    public EliminarOficinaApi(Context context, String url, String codiAcces, String idOficina) {
        this.context = context;
        this.url = url;
        this.codiAcces = codiAcces;
        this.idOficina = idOficina;
    }

    /**
     * Logout.
     * Llença una request per tancar sessió i tracta la resposta
     */
    public void Eliminar() {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + codiAcces + "/" + idOficina,
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
