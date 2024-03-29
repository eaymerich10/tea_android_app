package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.LlistatOficinesDisponiblesApi;

/**
 *
 * Classe que gestiona l'activity per buscar oficines
 */
public class BuscarOficines extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private String url = "";
    private String codiAcces = "";
    private String dataIniciReserva = "";
    private String dataFiReserva = "";
    private String dataEt = "";
    TextView tv_data_inici_reserva, tv_data_fi_reserva, tv_data_inici_reserva_value, tv_data_fi_reserva_value;
    ImageView iv_data_inici_reserva, iv_data_fi_reserva;
    Button bt_buscar_oficines;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);
        tv_data_inici_reserva = (TextView) findViewById(R.id.tv_data_inici_reserva);
        tv_data_fi_reserva = (TextView) findViewById(R.id.tv_data_fi_reserva);
        tv_data_inici_reserva_value = (TextView) findViewById(R.id.tv_data_inici_reserva_value);
        tv_data_fi_reserva_value = (TextView) findViewById(R.id.tv_data_final_reserva_value);
        iv_data_inici_reserva = (ImageView) findViewById(R.id.iv_data_inici_reserva);
        iv_data_fi_reserva = (ImageView) findViewById(R.id.iv_data_fi_reserva);
        bt_buscar_oficines = (Button) findViewById(R.id.b_busca_oficines_disponibles);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        codiAcces = intent.getStringExtra("codiAcces");
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dataEt);
            }
        };
        iv_data_inici_reserva.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BuscarOficines.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dataEt = "INICI";
            }
        });
        iv_data_fi_reserva.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BuscarOficines.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dataEt = "FI";
            }
        });
        bt_buscar_oficines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correcte = noNullText(dataIniciReserva, dataFiReserva);
                if (correcte == true) {
                    LlistatOficinesDisponiblesApi llistatOficinesDisponiblesApi = new LlistatOficinesDisponiblesApi(BuscarOficines.this, url + "oficinesdisponibles/", codiAcces, dataIniciReserva, dataFiReserva);
                    llistatOficinesDisponiblesApi.llistat();
                }
            }
        });

    }

    private void updateLabel(String dataEt) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        if (dataEt.equals("INICI")) {
            dataIniciReserva = sdf.format(myCalendar.getTime());
            tv_data_inici_reserva_value.setText(dataIniciReserva.trim());
        }

        if (dataEt.equals("FI")) {
            dataFiReserva = sdf.format(myCalendar.getTime());
            tv_data_fi_reserva_value.setText(dataFiReserva.trim());
        }
    }

    public boolean noNullText(String dataIniciReserva, String dataFiReserva) {
        if (dataIniciReserva.length() < 1 || dataFiReserva.length() < 1) {
            //Si algun camp es buit, marcara l'error a la pantalla
            if (dataIniciReserva.length() < 1) {
                Toast.makeText(BuscarOficines.this, "Introdueix una data de entrada", Toast.LENGTH_SHORT).show();
            }
            if (dataFiReserva.length() < 1) {
                Toast.makeText(BuscarOficines.this, "Introdueix una data de sortida", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            return true;
        }

    }
}