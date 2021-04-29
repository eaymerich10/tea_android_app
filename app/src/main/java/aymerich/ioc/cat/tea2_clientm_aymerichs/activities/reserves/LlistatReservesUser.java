package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Reserva;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves.EliminarReservaApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.Parser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.ResetURL;

/**
 *
 * Classe encarregada de l'activity per llistar les reserves del usuari
 * Sense comentaris a linies, ja que funciona igual que el llistat de oficines
 */
public class LlistatReservesUser extends AppCompatActivity {

    ListView llistaReserves;
    String itemLlista = "";
    String reservaString = "";
    String codiAcces = "";
    String url = "";
    Parser parser;
    ResetURL resetURL;
    ArrayList<String> reservesString = new ArrayList<String>();
    ArrayList<String> reservesFinal = new ArrayList<String>();
    ArrayList<String> idReserves = new ArrayList<String>();

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_reserves_user);
        llistaReserves = (ListView) findViewById(R.id.lv_llista_reserves_user);
        Intent intent = getIntent();
        codiAcces = intent.getStringExtra("codiAcces");
        resetURL = new ResetURL();
        url = resetURL.resetUrl(url);
        reservesFinal = intent.getStringArrayListExtra("reservesFinal");
        idReserves = intent.getStringArrayListExtra("idReserves");
        reservesString = intent.getStringArrayListExtra("reservesString");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LlistatReservesUser.this, android.R.layout.simple_list_item_1, reservesFinal);
        llistaReserves.setAdapter(adapter);
        llistaReserves.setDividerHeight(10);
        registerForContextMenu(llistaReserves);
        llistaReserves.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reservaString = llistaReserves.getItemAtPosition(position).toString();
                parser = new Parser();
                Reserva reserva = parser.parserStringToReserva(reservesString.get(position));
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentReserves);
                ((TextView) fragment.getView().findViewById(R.id.id_reserva_frag_value)).setText(reserva.getIdReserva());
                ((TextView) fragment.getView().findViewById(R.id.data_inici_reserva_frag_value)).setText(reserva.getDataIniciReserva());
                ((TextView) fragment.getView().findViewById(R.id.data_final_reserva_frag_value)).setText(reserva.getDataFinalReserva());
                ((TextView) fragment.getView().findViewById(R.id.id_oficina_reservada_frag_value)).setText(reserva.getIdOficina());
                ((TextView) fragment.getView().findViewById(R.id.id_usuari_reserva_frag_value)).setText(reserva.getIdUsuari());
            }
        });
        llistaReserves.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemLlista = llistaReserves.getItemAtPosition(position).toString();
                return false;
            }
        });
    }

    /**
     * On create context menu.
     *
     * @param menu     the menu
     * @param v        the v
     * @param menuInfo the menu info
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem eliminar = menu.add("Eliminar");

        eliminar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                for (int i = 0; i < reservesFinal.size(); i++) {
                    if (reservesFinal.get(i).equals(itemLlista)) {
                        String idReserva = idReserves.get(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LlistatReservesUser.this);
                        builder.setTitle("Eliminar Reserva");
                        builder.setMessage("Realment vols eliminar la FacturaDetailFragment?");
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EliminarReservaApi eliminarReservaApi = new EliminarReservaApi(LlistatReservesUser.this, url + "esborrarreserva/", codiAcces, idReserva);
                                eliminarReservaApi.Eliminar();
                                finish();

                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                return true;
            }
        });
    }


}