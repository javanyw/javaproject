public class regionCode implements Comparable<regionCode>{
    private String code;
    private String region;
    regionCode(String code, String region){
        this.code = code;
        this.region = region;

    }


    public String getCode() {
        return code;
    }



    public String getRegion() {
        return region;
    }



    @Override
    public int compareTo(regionCode o) {
        return 0;
    }
}
