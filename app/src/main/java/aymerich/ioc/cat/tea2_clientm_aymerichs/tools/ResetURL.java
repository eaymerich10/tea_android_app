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
        url = "http://192.168.1.92:8080/";
        return url;
    }
}
