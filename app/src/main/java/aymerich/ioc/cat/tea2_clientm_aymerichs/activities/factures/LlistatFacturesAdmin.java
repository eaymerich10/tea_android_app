package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Factura;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures.EliminarFacturaApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.Parser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.ResetURL;

/**
 *
 * Classe encarregada de l'activity per llistar factures del Administrador.
 * Sense comentaris a linies, ja que funciona igual que el llistat de oficines
 */
public class LlistatFacturesAdmin extends AppCompatActivity {

    ListView llistafactures;
    String itemLlista = "";
    String reservaString = "";
    String codiAcces = "";
    String url = "";
    Parser parser;
    ResetURL resetURL;
    ArrayList<String> facturesString = new ArrayList<String>();
    ArrayList<String> facturesFinal = new ArrayList<String>();
    ArrayList<String> idfactures = new ArrayList<String>();

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_factures);
        llistafactures = (ListView) findViewById(R.id.lv_llista_factures);
        Intent intent = getIntent();
        codiAcces = intent.getStringExtra("codiAcces");
        resetURL = new ResetURL();
        url = resetURL.resetUrl(url);
        facturesFinal = intent.getStringArrayListExtra("facturesFinal");
        idfactures = intent.getStringArrayListExtra("idFactures");
        facturesString = intent.getStringArrayListExtra("facturesString");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LlistatFacturesAdmin.this, android.R.layout.simple_list_item_1, facturesFinal);
        llistafactures.setAdapter(adapter);
        llistafactures.setDividerHeight(10);
        registerForContextMenu(llistafactures);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentFactures);
        ft.hide(fragment);
        ft.commit();
        llistafactures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reservaString = llistafactures.getItemAtPosition(position).toString();
                parser = new Parser();
                Factura factura = parser.parserStringToFactura(facturesString.get(position));
                FragmentManager fragManager = getSupportFragmentManager();
                FragmentTransaction fragT = fragManager.beginTransaction();
                Fragment frag = fragManager.findFragmentById(R.id.fragmentFactures);
                ((TextView) frag.getView().findViewById(R.id.tv_num_factura)).setText("ID Factura\n"+factura.getIdFactura());
                ((TextView) frag.getView().findViewById(R.id.tv_data_factura)).setText("Data Creació\n"+factura.getDataCreacio());
                ((TextView) frag.getView().findViewById(R.id.tv_num_reserva_factura)).setText("ID Reserva\n"+factura.getIdReserva());
                ((TextView) frag.getView().findViewById(R.id.tv_nom_usuari_factura)).setText("Nom Client\n"+factura.getNomUsuariReserva());
                ((TextView) frag.getView().findViewById(R.id.tv_nom_oficina_factura)).setText("Nom Oficina\n"+factura.getNomOficina());
                ((TextView) frag.getView().findViewById(R.id.tv_tipus_oficina_factura)).setText("Tipus Oficina\n"+factura.getTipusOficina());
                ((TextView) frag.getView().findViewById(R.id.tv_preu_oficina_factura)).setText("Preu Oficina\n"+factura.getPreuOficina());
                ((TextView) frag.getView().findViewById(R.id.tv_serveis_oficina_factura)).setText("Serveis Oficina\n"+factura.getServeisOficina());
                ((TextView) frag.getView().findViewById(R.id.tv_data_inici_reserva_factura)).setText("Data Entrada\n"+factura.getDataIniciReserva());
                ((TextView) frag.getView().findViewById(R.id.tv_data_final_reserva_factura)).setText("Data Sortida\n"+factura.getDataFinalReserva());
                ((TextView) frag.getView().findViewById(R.id.tv_base_res_factura)).setText(factura.getSubTotal());
                ((TextView) frag.getView().findViewById(R.id.tv_total_impostos_res_factura)).setText(factura.getImpostos());
                ((TextView) frag.getView().findViewById(R.id.tv_subtotal_factura)).setText("Subtotal\n"+factura.getSubTotal());
                ((TextView) frag.getView().findViewById(R.id.tv_impost_final_factura)).setText("Impostos\n"+factura.getImpostos());
                ((TextView) frag.getView().findViewById(R.id.tv_total_factura)).setText("Total Final\n"+factura.getTotal());
                fragT.show(frag);
                fragT.commit();
                llistafactures.setVisibility(View.INVISIBLE);
            }
        });
        llistafactures.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemLlista = llistafactures.getItemAtPosition(position).toString();
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

                for (int i = 0; i < facturesFinal.size(); i++) {
                    if (facturesFinal.get(i).equals(itemLlista)) {
                        String idFactura = idfactures.get(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LlistatFacturesAdmin.this);
                        builder.setTitle("Eliminar Factura");
                        builder.setMessage("Realment vols eliminar la factura?");
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EliminarFacturaApi eliminarFacturaApi = new EliminarFacturaApi(LlistatFacturesAdmin.this, url +"esborrarfactura/", codiAcces, idFactura);
                                eliminarFacturaApi.Eliminar();
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