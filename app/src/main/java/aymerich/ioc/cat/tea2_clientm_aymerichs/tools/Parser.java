package aymerich.ioc.cat.tea2_clientm_aymerichs.tools;

import org.json.JSONException;
import org.json.JSONObject;

import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;

public class Parser {

    public Oficina parserStringToOficina(String oficinaString){
        Oficina oficina = new Oficina(null, null, null, null);
        try {
            JSONObject jsonOficina = new JSONObject(oficinaString);
            oficina.setIdOficina(jsonOficina.getString("idOficina"));
            oficina.setNom(jsonOficina.getString("nom"));
            oficina.setTipus(jsonOficina.getString("tipus"));
            oficina.setCapacitat(jsonOficina.getString("capacitat"));
            oficina.setHabilitada(jsonOficina.getString("habilitada"));
            oficina.setEliminada(jsonOficina.getString("eliminat"));
            oficina.setPreu(jsonOficina.getString("preu"));
            oficina.setProvincia(jsonOficina.getString("provincia"));
            oficina.setPoblacio(jsonOficina.getString("poblacio"));
            oficina.setDireccio(jsonOficina.getString("direccio"));
            oficina.setServeis(jsonOficina.getString("serveis"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oficina;
    }
}
