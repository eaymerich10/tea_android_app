package aymerich.ioc.cat.tea2_clientm_aymerichs.models;

public class Reserva {

    private String idReserva;
    private String dataIniciReserva;
    private String dataFinalReserva;
    private String idOficina;
    private String idUsuari;

    public Reserva(String dataIniciReserva, String dataFinalReserva, String idOficina){
        this.dataIniciReserva = dataIniciReserva;
        this.dataFinalReserva = dataFinalReserva;
        this.idOficina = idOficina;
        this.idUsuari = idUsuari;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getDataIniciReserva() {
        return dataIniciReserva;
    }

    public void setDataIniciReserva(String dataIniciReserva) {
        this.dataIniciReserva = dataIniciReserva;
    }

    public String getDataFinalReserva() {
        return dataFinalReserva;
    }

    public void setDataFinalReserva(String dataFinalReserva) {
        this.dataFinalReserva = dataFinalReserva;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public String getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(String idUsuari) {
        this.idUsuari = idUsuari;
    }
}
