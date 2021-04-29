package aymerich.ioc.cat.tea2_clientm_aymerichs.models;

public class Reserva {

    private String idReserva;
    private String dataIniciReserva;
    private String dataFinalReserva;
    private String idOficina;
    private String idUsuari;

    /**
     * Instantiates a new Reserva.
     *
     * @param dataIniciReserva the data inici FacturaDetailFragment
     * @param dataFinalReserva the data final FacturaDetailFragment
     * @param idOficina        the id oficina
     * Classe model que gestiona les reserves
     */
    public Reserva(String dataIniciReserva, String dataFinalReserva, String idOficina){
        this.dataIniciReserva = dataIniciReserva;
        this.dataFinalReserva = dataFinalReserva;
        this.idOficina = idOficina;
        this.idUsuari = idUsuari;
    }

    /**
     * Gets id FacturaDetailFragment.
     *
     * @return the id FacturaDetailFragment
     */
    public String getIdReserva() {
        return idReserva;
    }

    /**
     * Sets id FacturaDetailFragment.
     *
     * @param idReserva the id FacturaDetailFragment
     */
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Gets data inici FacturaDetailFragment.
     *
     * @return the data inici FacturaDetailFragment
     */
    public String getDataIniciReserva() {
        return dataIniciReserva;
    }

    /**
     * Sets data inici FacturaDetailFragment.
     *
     * @param dataIniciReserva the data inici FacturaDetailFragment
     */
    public void setDataIniciReserva(String dataIniciReserva) {
        this.dataIniciReserva = dataIniciReserva;
    }

    /**
     * Gets data final FacturaDetailFragment.
     *
     * @return the data final FacturaDetailFragment
     */
    public String getDataFinalReserva() {
        return dataFinalReserva;
    }

    /**
     * Sets data final FacturaDetailFragment.
     *
     * @param dataFinalReserva the data final FacturaDetailFragment
     */
    public void setDataFinalReserva(String dataFinalReserva) {
        this.dataFinalReserva = dataFinalReserva;
    }

    /**
     * Gets id oficina.
     *
     * @return the id oficina
     */
    public String getIdOficina() {
        return idOficina;
    }

    /**
     * Sets id oficina.
     *
     * @param idOficina the id oficina
     */
    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
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
}
