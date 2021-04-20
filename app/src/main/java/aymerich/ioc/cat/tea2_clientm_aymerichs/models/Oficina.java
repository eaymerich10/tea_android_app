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

    /**
     * Instantiates a new Oficina.
     *
     * @param nom       the nom
     * @param tipus     the tipus
     * @param capacitat the capacitat
     * @param preu      the preu
     * Classe model de les oficines
     */
    public Oficina(String nom, String tipus, String capacitat, String preu) {
        this.nom = nom;
        this.tipus = tipus;
        this.capacitat = capacitat;
        this.preu = preu;
    }


    /**
     * Gets nom.
     *
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets nom.
     *
     * @param nom the nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets tipus.
     *
     * @return the tipus
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Sets tipus.
     *
     * @param tipus the tipus
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /**
     * Gets capacitat.
     *
     * @return the capacitat
     */
    public String getCapacitat() {
        return capacitat;
    }

    /**
     * Sets capacitat.
     *
     * @param capacitat the capacitat
     */
    public void setCapacitat(String capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * Gets preu.
     *
     * @return the preu
     */
    public String getPreu() {
        return preu;
    }

    /**
     * Sets preu.
     *
     * @param preu the preu
     */
    public void setPreu(String preu) {
        this.preu = preu;
    }

    /**
     * Gets serveis.
     *
     * @return the serveis
     */
    public String getServeis() {
        return serveis;
    }

    /**
     * Sets serveis.
     *
     * @param serveis the serveis
     */
    public void setServeis(String serveis) {
        this.serveis = serveis;
    }

    /**
     * Gets provincia.
     *
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets provincia.
     *
     * @param provincia the provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Gets poblacio.
     *
     * @return the poblacio
     */
    public String getPoblacio() {
        return poblacio;
    }

    /**
     * Sets poblacio.
     *
     * @param poblacio the poblacio
     */
    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    /**
     * Gets direccio.
     *
     * @return the direccio
     */
    public String getDireccio() {
        return direccio;
    }

    /**
     * Sets direccio.
     *
     * @param direccio the direccio
     */
    public void setDireccio(String direccio) {
        this.direccio = direccio;
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
     * Gets habilitada.
     *
     * @return the habilitada
     */
    public String getHabilitada() {
        return habilitada;
    }

    /**
     * Sets habilitada.
     *
     * @param habilitada the habilitada
     */
    public void setHabilitada(String habilitada) {
        this.habilitada = habilitada;
    }

    /**
     * Gets eliminada.
     *
     * @return the eliminada
     */
    public String getEliminada() {
        return eliminada;
    }

    /**
     * Sets eliminada.
     *
     * @param eliminada the eliminada
     */
    public void setEliminada(String eliminada) {
        this.eliminada = eliminada;
    }
}
