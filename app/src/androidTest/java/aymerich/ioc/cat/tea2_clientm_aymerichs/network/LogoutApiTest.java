package aymerich.ioc.cat.tea2_clientm_aymerichs.network;

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

public class LogoutApiTest {

    final CountDownLatch signal = new CountDownLatch(1);
    private String url;
    private String codiAcces;
    private Context context;
    private String testResponse = "";

    @Before
    public void setUp() throws Exception {
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/logout/";
        codiAcces = "codimock";
        context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void logout() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + codiAcces,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        signal.countDown();
                        testResponse = response;
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
        try {
            signal.await(5, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", "Sessio Finalitzada", testResponse);
    }
}