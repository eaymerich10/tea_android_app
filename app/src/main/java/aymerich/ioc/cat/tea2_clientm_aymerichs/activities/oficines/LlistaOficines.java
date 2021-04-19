package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.Parser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.EliminarOficinaApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.HabilitarOficinaApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.LlistatOficinesApi;

public class LlistaOficines extends AppCompatActivity {

    ListView llistaSales;
    String itemLlista = "";
    String oficinaString = "";
    String codiAcces = "";
    String url = "";
    Parser parser;
    ArrayList<String> oficinesString = new ArrayList<String>();
    ArrayList<String> salesFinal = new ArrayList<String>();
    ArrayList<String> idSales = new ArrayList<String>();
    LlistatOficinesApi llistatOficinesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_oficines);
        llistaSales = (ListView) findViewById(R.id.lv_llista_oficines);
        Intent intent = getIntent();
        codiAcces = intent.getStringExtra("codiAcces");
        url = "http://192.168.0.29:8080/";
        salesFinal = intent.getStringArrayListExtra("salesFinal");
        idSales = intent.getStringArrayListExtra("idSales");
        oficinesString = intent.getStringArrayListExtra("oficinesString");
        llistatOficinesApi = new LlistatOficinesApi(LlistaOficines.this,url+"oficines/",codiAcces);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LlistaOficines.this, android.R.layout.simple_list_item_1, salesFinal);
        llistaSales.setAdapter(adapter);
        llistaSales.setDividerHeight(10);
        registerForContextMenu(llistaSales);
        llistaSales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oficinaString = llistaSales.getItemAtPosition(position).toString();
                parser = new Parser();
                Oficina oficina = parser.parserStringToOficina(oficinesString.get(position));
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem editar = menu.add("Editar");
        MenuItem eliminar = menu.add("Eliminar");
        MenuItem deshabilitar = menu.add("Canviar Habilitada");

        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                for (int i = 0; i < salesFinal.size(); i++) {
                    if (salesFinal.get(i).equals(itemLlista)) {
                        String idOficina = idSales.get(i);
                        String oficina = oficinesString.get(i);
                        Log.i("oficina", oficina);
                        Intent intent = new Intent(LlistaOficines.this, EditarOficina.class);
                        intent.putExtra("oficina", oficina);
                        intent.putExtra("codiAcces", codiAcces);
                        intent.putExtra("url", url);
                        intent.putExtra("idOficina", idOficina);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
        eliminar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                for (int i = 0; i < salesFinal.size(); i++) {
                    if (salesFinal.get(i).equals(itemLlista)) {
                        String idOficina = idSales.get(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LlistaOficines.this);
                        builder.setTitle("Eliminar Oficina");
                        builder.setMessage("Realment vols eliminar la sala?");
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EliminarOficinaApi eliminarOficinaApi = new EliminarOficinaApi(LlistaOficines.this, url+"baixaoficina/", codiAcces, idOficina);
                                eliminarOficinaApi.Eliminar();
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
        deshabilitar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                for (int i = 0; i < salesFinal.size(); i++) {
                    if (salesFinal.get(i).equals(itemLlista)) {
                        String idOficina = idSales.get(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(LlistaOficines.this);
                        builder.setTitle("Canviar oficina habilitada");
                        builder.setMessage("Quin és l'estat de la oficina?");
                        builder.setPositiveButton("Habilitada", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    HabilitarOficinaApi habilitarOficinaApi = new HabilitarOficinaApi(LlistaOficines.this,url,codiAcces,idOficina,"true");
                                    habilitarOficinaApi.HabilitarOficina();
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        builder.setNegativeButton("Deshabilitada", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HabilitarOficinaApi habilitarOficinaApi = null;
                                try {
                                    habilitarOficinaApi = new HabilitarOficinaApi(LlistaOficines.this,url,codiAcces,idOficina,"false");
                                    habilitarOficinaApi.HabilitarOficina();
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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