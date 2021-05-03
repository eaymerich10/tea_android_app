package aymerich.ioc.cat.tea2_clientm_aymerichs.tools;

import org.json.JSONException;
import org.json.JSONObject;

import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Factura;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Reserva;

/**
 *
 * Classe per crear oficines o reserves a partir de una string amb format JSON
 *
 */
public class Parser {

    /**
     * Parser string to oficina oficina.
     *
     * @param oficinaString the oficina string
     * @return the oficina
     */
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

    /**
     * Parser string to FacturaDetailFragment FacturaDetailFragment.
     *
     * @param reservaString the FacturaDetailFragment string
     * @return the FacturaDetailFragment
     */
    public Reserva parserStringToReserva(String reservaString){
        Reserva reserva = new Reserva(null, null, null);
        try {
            JSONObject jsonOficina = new JSONObject(reservaString);
            reserva.setIdReserva(jsonOficina.getString("idReserva"));
            reserva.setDataIniciReserva(jsonOficina.getString("dataIniciReserva"));
            reserva.setDataFinalReserva(jsonOficina.getString("dataFiReserva"));
            reserva.setIdOficina(jsonOficina.getJSONObject("idOficina").getString("idOficina"));
            reserva.setIdUsuari(jsonOficina.getJSONObject("idUsuari").getString("idUsuari"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reserva;
    }
    public Factura parserStringToFactura(String facturaString){
        Factura factura = new Factura(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        try {
            JSONObject jsonFactura = new JSONObject(facturaString);
            factura.setIdFactura(jsonFactura.getString("idFactura"));
            factura.setDataCreacio(jsonFactura.getString("dataCreacio"));
            factura.setDataIniciReserva(jsonFactura.getJSONObject("idReserva").getString("dataIniciReserva"));
            factura.setDataFinalReserva(jsonFactura.getJSONObject("idReserva").getString("dataFiReserva"));
            factura.setImpostos(jsonFactura.getString("impostos"));
            factura.setNomOficina(jsonFactura.getJSONObject("idReserva").getJSONObject("idOficina").getString("nom"));
            factura.setNomUsuariReserva(jsonFactura.getJSONObject("idReserva").getJSONObject("idUsuari").getString("nom"));
            factura.setIdReserva(jsonFactura.getJSONObject("idReserva").getString("idReserva"));
            factura.setPreuOficina(jsonFactura.getJSONObject("idReserva").getJSONObject("idOficina").getString("preu"));
            factura.setTipusOficina(jsonFactura.getJSONObject("idReserva").getJSONObject("idOficina").getString("tipus"));
            factura.setServeisOficina(jsonFactura.getJSONObject("idReserva").getJSONObject("idOficina").getString("serveis"));
            factura.setSubTotal(jsonFactura.getString("subTotal"));
            factura.setTotal(jsonFactura.getString("total"));
            factura.setIdUsuari(jsonFactura.getJSONObject("idReserva").getJSONObject("idUsuari").getString("idUsuari"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return factura;
    }
}
