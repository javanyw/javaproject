import java.io.BufferedReader;

public abstract class abstractServer {
    private String urlAdrress;
    private int responseCode;
    private BufferedReader rd;
    private String result;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public BufferedReader getRd() {
        return rd;
    }

    public void setRd(BufferedReader rd) {
        this.rd = rd;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getUrlAdrress() {
        return urlAdrress;
    }

    public void setUrlAdrress(String urlAdrress) {
        this.urlAdrress = urlAdrress;
    }
}
