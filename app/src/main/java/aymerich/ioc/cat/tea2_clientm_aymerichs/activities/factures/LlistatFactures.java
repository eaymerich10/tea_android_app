package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;

public class LlistatFactures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llistat_factures);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentFactures);

    }
}