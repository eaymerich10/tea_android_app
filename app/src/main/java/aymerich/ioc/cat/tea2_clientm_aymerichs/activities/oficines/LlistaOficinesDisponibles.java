package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines;

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

import org.json.JSONException;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves.ReservarOficinaApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.Parser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.ResetURL;

/**
 *
 * Classe encarregada de l'activitat per llistar les oficines disponibles per reservar
 *
 */
public class LlistaOficinesDisponibles extends AppCompatActivity {

    //Definició de Variables
    ListView llistaSales;
    String itemLlista = "";
    String oficinaString = "";
    String codiAcces = "";
    String url = "";
    String dataIniciReserva = "";
    String dataFiReserva = "";
    Parser parser;
    ResetURL resetURL;
    ArrayList<String> oficinesString = new ArrayList<String>();
    ArrayList<String> salesFinal = new ArrayList<String>();
    ArrayList<String> idSales = new ArrayList<String>();

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_oficines);
        llistaSales = (ListView) findViewById(R.id.lv_llista_oficines);
        Intent intent = getIntent();
        codiAcces = intent.getStringExtra("codiAcces");
        resetURL = new ResetURL();
        url = resetURL.resetUrl(url);
        dataIniciReserva = intent.getStringExtra("dataIniciReserva");
        dataFiReserva = intent.getStringExtra("dataFiReserva");
        salesFinal = intent.getStringArrayListExtra("salesFinal");
        idSales = intent.getStringArrayListExtra("idSales");
        oficinesString = intent.getStringArrayListExtra("oficinesString");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LlistaOficinesDisponibles.this, android.R.layout.simple_list_item_1, salesFinal);
        llistaSales.setAdapter(adapter);
        llistaSales.setDividerHeight(10);
        registerForContextMenu(llistaSales);
        llistaSales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oficinaString = llistaSales.getItemAtPosition(position).toString();
                parser = new Parser();
                Oficina oficina = parser.parserStringToOficina(oficinesString.get(position)); //Aconseguim una oficina a partir del String.
                //Crida al Fragment
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
                ((TextView) fragment.getView().findViewById(R.id.id_oficina_frag_value)).setText(oficina.getIdOficina());
                ((TextView) fragment.getView().findViewById(R.id.nom_oficina_frag_value)).setText(oficina.getNom());
                ((TextView) fragment.getView().findViewById(R.id.data_final_reserva_frag_value)).setText(oficina.getTipus());
                ((TextView) fragment.getView().findViewById(R.id.capacitat_oficina_frag_value)).setText(oficina.getCapacitat());
                ((TextView) fragment.getView().findViewById(R.id.preu_oficina_frag_value)).setText(oficina.getPreu());
                ((TextView) fragment.getView().findViewById(R.id.serveis_oficina_frag_value)).setText(oficina.getServeis());
                ((TextView) fragment.getView().findViewById(R.id.habilitada_oficina_frag_value)).setText(oficina.getHabilitada());
                ((TextView) fragment.getView().findViewById(R.id.provincia_oficina_frag_value)).setText(oficina.getProvincia());
                ((TextView) fragment.getView().findViewById(R.id.poblacio_oficina_frag_value)).setText(oficina.getPoblacio());
                ((TextView) fragment.getView().findViewById(R.id.direccio_oficina_frag_value)).setText(oficina.getDireccio());
                ((TextView) fragment.getView().findViewById(R.id.eliminada_oficina_frag_value)).setText(oficina.getEliminada());
            }
        });
        llistaSales.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemLlista = llistaSales.getItemAtPosition(position).toString();
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

        MenuItem reservar = menu.add("Reservar");

        reservar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                //Bucle per relacionar una oficina amb la oficina clicada
                for (int i = 0; i < salesFinal.size(); i++) {
                    if (salesFinal.get(i).equals(itemLlista)) {
                        String idOficina = idSales.get(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LlistaOficinesDisponibles.this);
                        builder.setTitle("Reservar Oficina");
                        builder.setMessage("Vols reservar aquesta oficina?");
                        builder.setPositiveButton("Reservar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    ReservarOficinaApi reservarOficinaApi = new ReservarOficinaApi(LlistaOficinesDisponibles.this, idOficina, dataIniciReserva, dataFiReserva, codiAcces, url+"reservaoficina/");
                                    reservarOficinaApi.Reservar();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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