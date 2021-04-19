package aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.LlistatOficinesDisponiblesApi;

public class BuscarOficines extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    private String url ="";
    private String codiAcces ="";
    private String dataIniciReserva = "";
    private String dataFiReserva = "";
    private String dataEt ="";
    TextView tv_data_inici_reserva, tv_data_fi_reserva;
    EditText et_data_inici_reserva, et_data_fi_reserva;
    Button bt_buscar_oficines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);
        tv_data_inici_reserva = (TextView) findViewById(R.id.tv_data_inici_reserva);
        tv_data_fi_reserva = (TextView) findViewById(R.id.tv_data_fi_reserva);
        et_data_inici_reserva = (EditText) findViewById(R.id.et_data_inici_reserva);
        et_data_fi_reserva = (EditText) findViewById(R.id.et_data_fi_reserva);
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
        et_data_inici_reserva.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BuscarOficines.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        dataEt = "INICI";
            }
        });
        et_data_fi_reserva.setOnClickListener(new View.OnClickListener() {

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
                LlistatOficinesDisponiblesApi llistatOficinesDisponiblesApi = new LlistatOficinesDisponiblesApi(BuscarOficines.this, url + "oficinesdisponibles/", codiAcces, dataIniciReserva, dataFiReserva);
                llistatOficinesDisponiblesApi.llistat();
            }
        });

    }

    private void updateLabel(String dataEt) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        if(dataEt.equals("INICI")){
            et_data_inici_reserva.setText(sdf.format(myCalendar.getTime()));
            dataIniciReserva = et_data_inici_reserva.getText().toString();
        }

        if(dataEt.equals("FI")) {
            et_data_fi_reserva.setText(sdf.format(myCalendar.getTime()));
            dataFiReserva = et_data_fi_reserva.getText().toString();
        }
    }

    }
