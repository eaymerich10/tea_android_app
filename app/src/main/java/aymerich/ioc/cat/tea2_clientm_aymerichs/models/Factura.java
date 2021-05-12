package aymerich.ioc.cat.tea2_clientm_aymerichs.models;

/**
 *
 * Classe model factura.
 */
public class Factura {
    private String idFactura;
    private String dataCreacio;
    private String dataIniciReserva;
    private String dataFinalReserva;
    private String impostos;
    private String nomOficina;
    private String nomUsuariReserva;

    /**
     * Gets id factura.
     *
     * @return the id factura
     */
    public String getIdFactura() {
        return idFactura;
    }

    /**
     * Sets id factura.
     *
     * @param idFactura the id factura
     */
    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * Gets data creacio.
     *
     * @return the data creacio
     */
    public String getDataCreacio() {
        return dataCreacio;
    }

    /**
     * Sets data creacio.
     *
     * @param dataCreacio the data creacio
     */
    public void setDataCreacio(String dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    /**
     * Gets data inici reserva.
     *
     * @return the data inici reserva
     */
    public String getDataIniciReserva() {
        return dataIniciReserva;
    }

    /**
     * Sets data inici reserva.
     *
     * @param dataIniciReserva the data inici reserva
     */
    public void setDataIniciReserva(String dataIniciReserva) {
        this.dataIniciReserva = dataIniciReserva;
    }

    /**
     * Gets data final reserva.
     *
     * @return the data final reserva
     */
    public String getDataFinalReserva() {
        return dataFinalReserva;
    }

    /**
     * Sets data final reserva.
     *
     * @param dataFinalReserva the data final reserva
     */
    public void setDataFinalReserva(String dataFinalReserva) {
        this.dataFinalReserva = dataFinalReserva;
    }

    /**
     * Gets impostos.
     *
     * @return the impostos
     */
    public String getImpostos() {
        return impostos;
    }

    /**
     * Sets impostos.
     *
     * @param impostos the impostos
     */
    public void setImpostos(String impostos) {
        this.impostos = impostos;
    }

    /**
     * Gets nom oficina.
     *
     * @return the nom oficina
     */
    public String getNomOficina() {
        return nomOficina;
    }

    /**
     * Sets nom oficina.
     *
     * @param nomOficina the nom oficina
     */
    public void setNomOficina(String nomOficina) {
        this.nomOficina = nomOficina;
    }

    /**
     * Gets nom usuari reserva.
     *
     * @return the nom usuari reserva
     */
    public String getNomUsuariReserva() {
        return nomUsuariReserva;
    }

    /**
     * Sets nom usuari reserva.
     *
     * @param nomUsuariReserva the nom usuari reserva
     */
    public void setNomUsuariReserva(String nomUsuariReserva) {
        this.nomUsuariReserva = nomUsuariReserva;
    }

    /**
     * Gets id reserva.
     *
     * @return the id reserva
     */
    public String getIdReserva() {
        return idReserva;
    }

    /**
     * Sets id reserva.
     *
     * @param idReserva the id reserva
     */
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Gets preu oficina.
     *
     * @return the preu oficina
     */
    public String getPreuOficina() {
        return preuOficina;
    }

    /**
     * Sets preu oficina.
     *
     * @param preuOficina the preu oficina
     */
    public void setPreuOficina(String preuOficina) {
        this.preuOficina = preuOficina;
    }

    /**
     * Gets tipus oficina.
     *
     * @return the tipus oficina
     */
    public String getTipusOficina() {
        return tipusOficina;
    }

    /**
     * Sets tipus oficina.
     *
     * @param tipusOficina the tipus oficina
     */
    public void setTipusOficina(String tipusOficina) {
        this.tipusOficina = tipusOficina;
    }

    /**
     * Gets serveis oficina.
     *
     * @return the serveis oficina
     */
    public String getServeisOficina() {
        return serveisOficina;
    }

    /**
     * Sets serveis oficina.
     *
     * @param serveisOficina the serveis oficina
     */
    public void setServeisOficina(String serveisOficina) {
        this.serveisOficina = serveisOficina;
    }

    /**
     * Gets sub total.
     *
     * @return the sub total
     */
    public String getSubTotal() {
        return subTotal;
    }

    /**
     * Sets sub total.
     *
     * @param subTotal the sub total
     */
    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * Gets id usuari.
     *
     * @return the id usuari
     */
    public String getIdUsuari() {
        return idUsuari;
    }

    /**
     * Sets id usuari.
     *
     * @param idUsuari the id usuari
     */
    public void setIdUsuari(String idUsuari) {
        this.idUsuari = idUsuari;
    }

    private String idReserva;
    private String preuOficina;
    private String tipusOficina;
    private String serveisOficina;
    private String subTotal;
    private String total;
    private String idUsuari;


    /**
     * Instantiates a new Factura.
     *
     * @param idFactura        the id factura
     * @param dataCreacio      the data creacio
     * @param dataIniciReserva the data inici reserva
     * @param dataFinalReserva the data final reserva
     * @param impostos         the impostos
     * @param nomOficina       the nom oficina
     * @param nomUsuariReserva the nom usuari reserva
     * @param idReserva        the id reserva
     * @param preuOficina      the preu oficina
     * @param tipusOficina     the tipus oficina
     * @param serveisOficina   the serveis oficina
     * @param subTotal         the sub total
     * @param total            the total
     * @param idUsuari         the id usuari
     */
    public Factura(String idFactura, String dataCreacio, String dataIniciReserva, String dataFinalReserva, String impostos, String nomOficina, String nomUsuariReserva, String idReserva, String preuOficina, String tipusOficina, String serveisOficina, String subTotal, String total, String idUsuari) {
        this.idFactura = idFactura;
        this.dataCreacio = dataCreacio;
        this.dataIniciReserva = dataIniciReserva;
        this.dataFinalReserva = dataFinalReserva;
        this.impostos = impostos;
        this.nomOficina = nomOficina;
        this.nomUsuariReserva = nomUsuariReserva;
        this.idReserva = idReserva;
        this.preuOficina = preuOficina;
        this.tipusOficina = tipusOficina;
        this.serveisOficina = serveisOficina;
        this.subTotal = subTotal;
        this.total = total;
        this.idUsuari = idUsuari;
    }
}
