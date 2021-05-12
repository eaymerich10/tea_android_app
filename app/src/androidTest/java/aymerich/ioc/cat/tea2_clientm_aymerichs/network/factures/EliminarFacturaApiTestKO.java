package aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures;

import android.content.Context;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class EliminarFacturaApiTestKO {
    Context context;
    private String url = "";
    private String codiAcces = "";
    private String idFactura = "";
    private String testResponse;
    private String respostaEsperada;
    private CountDownLatch signal = new CountDownLatch(1);

    @Before
    public void setUp() throws Exception {
        url="https://192.168.1.23:8443/esborrarfactura";
        codiAcces = "df65ec1d-e3de-4565-8bfb-43025e02997c";
        idFactura = "131af401-9569-45d9-b645-c3224c2c2ca9";
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void eliminar() {

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + codiAcces + "/" + idFactura,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response,
                                Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                signal.countDown();
                respostaEsperada = error.toString();
                testResponse = error.toString();
                Toast.makeText(context, "Error",
                        Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);
        try {

            signal.await(5, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", respostaEsperada, testResponse);
    }
}