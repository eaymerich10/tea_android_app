package aymerich.ioc.cat.tea2_clientm_aymerichs.tools;

/**
 *
 * Classe per reiniciar la url en cas de necessitat
 *
 */
public class ResetURL {

    /**
     * Reset url string.
     *
     * @param url the url
     * @return the string
     */
    public String resetUrl(String url){
        url = "https://192.168.1.23:8443/";
        return url;
    }
}
