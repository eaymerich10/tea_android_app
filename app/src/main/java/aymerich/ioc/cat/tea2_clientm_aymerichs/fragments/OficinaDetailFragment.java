package aymerich.ioc.cat.tea2_clientm_aymerichs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OficinaDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OficinaDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "param1";
    private static final String ARG_NOM= "param2";
    private static final String ARG_TIPUS= "param2";
    private static final String ARG_CAPACITAT= "param2";
    private static final String ARG_PREU= "param2";
    private static final String ARG_SERVEIS= "param2";
    private static final String ARG_HABILITADA= "param2";
    private static final String ARG_PROVINCIA= "param2";
    private static final String ARG_POBLACIO= "param2";
    private static final String ARG_DIRECCIO= "param2";
    private static final String ARG_ELIMINADA= "param2";

    TextView idOficinaTV, idOficinaData, nomOficinaTV, nomOficinaData, tipusOficinaTV, tipusOficinaData, capacitatOficinaTV, capacitatOficinaData, preuOficinaTV, preuOficinaData, serveisOficinaTV, serveisOficinaData;
    TextView habilitadaOficinaTV, habilitadaOficinaData, provinciaOficinaTV, provinciaOficinaData, poblacioOficinaTV, poblacioOficinaData, direccioOficinaTV, direccioOficinaData, eliminadaOficinaTV, eliminadaOficinaData;

    // TODO: Rename and change types of parameters
    private String mId, mNom, mTipus, mCapacitat, mPreu, mServeis, mHabilitada, mProvincia, mPoblacio, mDireccio, mEliminada;
    public OficinaDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OficinaDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OficinaDetailFragment newInstance(String mId, String mNom, String mTipus, String mCapacitat, String mPreu, String mServeis, String mHabilitada, String mProvincia, String mPoblacio, String mDireccio, String mEliminada) {
        OficinaDetailFragment fragment = new OficinaDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, mId);
        args.putString(ARG_NOM, mNom);
        args.putString(ARG_TIPUS, mTipus);
        args.putString(ARG_CAPACITAT, mCapacitat);
        args.putString(ARG_PREU, mPreu);
        args.putString(ARG_SERVEIS, mServeis);
        args.putString(ARG_HABILITADA, mHabilitada);
        args.putString(ARG_PROVINCIA, mProvincia);
        args.putString(ARG_POBLACIO, mPoblacio);
        args.putString(ARG_DIRECCIO, mDireccio);
        args.putString(ARG_ELIMINADA, mEliminada);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ARG_ID);
            mNom = getArguments().getString(ARG_NOM);
            mTipus = getArguments().getString(ARG_TIPUS);
            mCapacitat = getArguments().getString(ARG_CAPACITAT);
            mPreu = getArguments().getString(ARG_PREU);
            mServeis = getArguments().getString(ARG_SERVEIS);
            mHabilitada = getArguments().getString(ARG_HABILITADA);
            mProvincia = getArguments().getString(ARG_PROVINCIA);
            mPoblacio = getArguments().getString(ARG_POBLACIO);
            mDireccio = getArguments().getString(ARG_DIRECCIO);
            mEliminada = getArguments().getString(ARG_ELIMINADA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_oficina_detail, container, false);
        idOficinaTV = rootView.findViewById(R.id.id_oficina_frag);
        idOficinaData = rootView.findViewById(R.id.id_oficina_frag_value);
        nomOficinaTV = rootView.findViewById(R.id.nom_oficina_frag);
        nomOficinaData = rootView.findViewById(R.id.nom_oficina_frag_value);
        tipusOficinaTV = rootView.findViewById(R.id.tipus_oficina_frag);
        tipusOficinaData = rootView.findViewById(R.id.data_final_reserva_frag_value);
        capacitatOficinaTV = rootView.findViewById(R.id.capacitat_oficina_frag);
        capacitatOficinaData = rootView.findViewById(R.id.capacitat_oficina_frag_value);
        preuOficinaTV = rootView.findViewById(R.id.preu_oficina_frag);
        preuOficinaData = rootView.findViewById(R.id.preu_oficina_frag_value);
        serveisOficinaTV = rootView.findViewById(R.id.serveis_oficina_frag);
        serveisOficinaData = rootView.findViewById(R.id.serveis_oficina_frag_value);
        habilitadaOficinaTV = rootView.findViewById(R.id.habilitada_oficina_frag);
        habilitadaOficinaData = rootView.findViewById(R.id.habilitada_oficina_frag_value);
        provinciaOficinaTV = rootView.findViewById(R.id.provincia_oficina_frag);
        provinciaOficinaData = rootView.findViewById(R.id.provincia_oficina_frag_value);
        poblacioOficinaTV = rootView.findViewById(R.id.poblacio_oficina_frag);
        poblacioOficinaData = rootView.findViewById(R.id.poblacio_oficina_frag_value);
        direccioOficinaTV = rootView.findViewById(R.id.direccio_oficina_frag);
        direccioOficinaData = rootView.findViewById(R.id.direccio_oficina_frag_value);
        eliminadaOficinaTV = rootView.findViewById(R.id.eliminada_oficina_frag);
        eliminadaOficinaData = rootView.findViewById(R.id.eliminada_oficina_frag_value);
        return  rootView;
    }
}