package aymerich.ioc.cat.tea2_clientm_aymerichs.models;

public class Factura {
    private String idFactura;
    private String dataCreacio;
    private String dataIniciReserva;
    private String dataFinalReserva;
    private String impostos;
    private String nomOficina;
    private String nomUsuariReserva;

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(String dataCreacio) {
        this.dataCreacio = dataCreacio;
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

    public String getImpostos() {
        return impostos;
    }

    public void setImpostos(String impostos) {
        this.impostos = impostos;
    }

    public String getNomOficina() {
        return nomOficina;
    }

    public void setNomOficina(String nomOficina) {
        this.nomOficina = nomOficina;
    }

    public String getNomUsuariReserva() {
        return nomUsuariReserva;
    }

    public void setNomUsuariReserva(String nomUsuariReserva) {
        this.nomUsuariReserva = nomUsuariReserva;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getPreuOficina() {
        return preuOficina;
    }

    public void setPreuOficina(String preuOficina) {
        this.preuOficina = preuOficina;
    }

    public String getTipusOficina() {
        return tipusOficina;
    }

    public void setTipusOficina(String tipusOficina) {
        this.tipusOficina = tipusOficina;
    }

    public String getServeisOficina() {
        return serveisOficina;
    }

    public void setServeisOficina(String serveisOficina) {
        this.serveisOficina = serveisOficina;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIdUsuari() {
        return idUsuari;
    }

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
