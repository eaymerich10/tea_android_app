package aymerich.ioc.cat.tea2_clientm_aymerichs.models;

public class Oficina {
    private String idOficina;
    private String nom;
    private String tipus;
    private String capacitat;
    private String preu;
    private String serveis;
    private String habilitada;
    private String eliminada;
    private String provincia;
    private String poblacio;
    private String direccio;

    public Oficina(String nom, String tipus, String capacitat, String preu) {
        this.nom = nom;
        this.tipus = tipus;
        this.capacitat = capacitat;
        this.preu = preu;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getCapacitat() {
        return capacitat;
    }

    public void setCapacitat(String capacitat) {
        this.capacitat = capacitat;
    }

    public String getPreu() {
        return preu;
    }

    public void setPreu(String preu) {
        this.preu = preu;
    }

    public String getServeis() {
        return serveis;
    }

    public void setServeis(String serveis) {
        this.serveis = serveis;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }


    public String getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(String habilitada) {
        this.habilitada = habilitada;
    }
    public String getEliminada() {
        return eliminada;
    }

    public void setEliminada(String eliminada) {
        this.eliminada = eliminada;
    }
}
