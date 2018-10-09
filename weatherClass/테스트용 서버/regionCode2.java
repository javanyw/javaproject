public class regionCode2 implements Comparable<regionCode2> {
    private String code;
    private String region;

    regionCode2(String code, String region){
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
    public int compareTo(regionCode2 o) {
        return 0;
    }
}
