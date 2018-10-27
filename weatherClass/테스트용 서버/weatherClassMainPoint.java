import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class weatherClassMainPoint {
    private int caldays;
    public static List<regionCode> regionCodes = new LinkedList<>();
    public static List<regionCode2> regionCode2s = new LinkedList<>();
    public static List<regionCode3> regionCode3s = new LinkedList<>();
    StringBuilder resultBuilding = new StringBuilder("");
    weatherClassMainPoint(String request){
        initialize();
        initialize2();
        initialize3();

        String[] parts=request.split("-");

        String part = parts[0];
        String part1 = parts[1];
        regionCode regionCode1 = (regionCodes.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));//중기 육상, 신뢰도
        regionCode2 regionCode2 = (regionCode2s.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));//기온
        regionCode3 regionCode3 = (regionCode3s.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));
        getDate gd = new getDate();
        String today = gd.getDate();//요청시 보낼 때 쓸 거
        String simpleToday = gd.SimpleFormToday();// 날짜 계산용
        StringBuilder DestinationBuilder = new StringBuilder("");
        String destinationday;
        String destinationparts[] = part1.split(",");
        String dpy = destinationparts[0];
        String dpm = destinationparts[1];
        int dpmi = Integer.parseInt(dpm);
        if(dpmi<10){
            DestinationBuilder.append("0");
            DestinationBuilder.append(dpmi);
            dpm = DestinationBuilder.toString();
        }
        DestinationBuilder=new StringBuilder("");
        String dpd = destinationparts[2];
        int dpdi = Integer.parseInt(dpd);
        if(dpdi<10){
            DestinationBuilder.append("0");
            DestinationBuilder.append(dpdi);
            dpd = DestinationBuilder.toString();
        }
        StringBuilder destinationBuilder = new StringBuilder("");
        destinationBuilder.append(dpy);
        destinationBuilder.append("-");
        destinationBuilder.append(dpm);
        destinationBuilder.append("-");
        destinationBuilder.append(dpd);
        destinationday = destinationBuilder.toString();
        calDateDays cd = new calDateDays(simpleToday,destinationday);
        caldays = cd.datedays();
        if(caldays<0){
            //과거인데 과거는 우리가 처리할 필요도 없고 요청도 하지 않을 것이다 이게 불러지면 오류다

        }
        else if(caldays<3){
            //오늘~모레까지의 날씨를 처리 구현해야 할 부분

        }
        else if(caldays<11){
            //지금 내가 만든 거 3일~10일까지 처리 가능
            middleLandWeatherClass mL = new middleLandWeatherClass(today, regionCode1.getCode(),caldays);
            mL.run();
            resultBuilding.append(mL.getResult());


            middleTemperatureClass mT = new middleTemperatureClass(today,regionCode2.getCode(),caldays);
            mT.run();
            resultBuilding.append(mT.getResult());

            middleLandWeatherConfClass mLW = new middleLandWeatherConfClass(today,regionCode1.getCode(),caldays);
            mLW.run();
            resultBuilding.append(mLW.getResult());

            System.out.println(resultBuilding.toString());
        }
        else{
            //너무나도 먼 미래라서 알 수 없다 다른 api를 추가하면 될 지도 모르겠지만 걍 10일을 최대 한도로 하자 아니면 다른 api 알아서 구해와라 추가해준다
        }
    }
    public String getResult(){
        return  resultBuilding.toString();
    }
    public static void initialize(){
        regionCode[] data ={//중기 육상, 신뢰도
                new regionCode("11B00000","서울, 인천, 경기도, 수원, 파주"),
                new regionCode("11D10000","강원도영서, 춘천, 원주"),
                new regionCode("11D20000","강원도영동, 영동"),
                new regionCode("11C20000","대전, 세종, 충청남도, 서산"),
                new regionCode("11C10000", "충청북도"),
                new regionCode("11F20000","광주, 전라남도, 목포, 여수"),
                new regionCode("11F10000","전라북도, 전주, 군산"),
                new regionCode("11H10000","대구, 경상북도, 안동, 포항"),
                new regionCode("11H20000","부산, 울산, 경상남도, 창원"),
                new regionCode("11G0000","제주도, 서귀포")
        };
        for(regionCode p : data){
            regionCodes.add(p);
        }
    }
    public static void initialize2(){
        regionCode2[] data = {//기온
                new regionCode2("11B10101","서울"),
                new regionCode2("11B20201", "인천"),
                new regionCode2("11B20601","수원"),
                new regionCode2("11B20305", "파주"),
                new regionCode2("11D10301", "춘천"),
                new regionCode2("11D10401", "원주"),
                new regionCode2("11D20501", "강릉"),
                new regionCode2("11C20401","대전"),
                new regionCode2("11C20101","서산"),
                new regionCode2("11C20404","세종"),
                new regionCode2("11C10301", "청주"),
                new regionCode2("11G00201", "제주"),
                new regionCode2("11G00401","서귀포"),
                new regionCode2("11F20501","광주"),
                new regionCode2("21F20801","목포"),
                new regionCode2("11F20401","여수"),
                new regionCode2("11F10201","전주"),
                new regionCode2("21F10501","군산"),
                new regionCode2("11H20201","부산"),
                new regionCode2("11H20101","울산"),
                new regionCode2("11H20301","창원"),
                new regionCode2("11H10701","대구"),
                new regionCode2("11H10501","안동"),
                new regionCode2("11H10201","포항")

        };
        for(regionCode2 p : data){
            regionCode2s.add(p);
        }
    }
    public static void initialize3(){
        regionCode3[] data = {
                new regionCode3("11B10101","서울"),
                new regionCode3("11B20201","인천"),
                new regionCode3("11B20601","수원"),
                new regionCode3("11B20605","성남"),
                new regionCode3("11B20602","안양"),
                new regionCode3("11B10103","광명"),
                new regionCode3("11B10102","과천"),
                new regionCode3("11B20606","평택"),
                new regionCode3("11B20603","오산"),
                new regionCode3("11B20609","의왕"),
                new regionCode3("11B20612","용인"),
                new regionCode3("11B20610","군포"),
                new regionCode3("11B20611","안성"),
                new regionCode3("11B20604","화성"),
                new regionCode3("11B20503","양평"),
                new regionCode3("11B20501","구리"),
                new regionCode3("11B20502","남양주"),
                new regionCode3("11B20504","하남"),
                new regionCode3("11B20701","이천"),
                new regionCode3("11B20703","여주"),
                new regionCode3("11B20702","광주"),//광주광역시가 아니고 경기 광주인데 이거 어캐 구별할 지 토의 해야함.
                new regionCode3("11B20301","의정부"),
                new regionCode3("11B20302","고양"),
                new regionCode3("11B20305","파주"),
                new regionCode3("11B20304","양주"),
                new regionCode3("11B20401","동두천"),
                new regionCode3("11B20402","연천"),
                new regionCode3("11B20403","포천"),
                new regionCode3("11B20404","가평"),
                new regionCode3("11B20101","강화"),
                new regionCode3("11B20102","김포"),
                new regionCode3("11B20202","시흥"),
                new regionCode3("11B20204","부천"),
                new regionCode3("11B20203","안산"),
                new regionCode3("11A00101","백령도"),
                new regionCode3("11H20201","부산"),
                new regionCode3("11H20101","울산"),
                new regionCode3("11H20304","김해"),
                new regionCode3("11H20102","양산"),
                new regionCode3("11H20301","창원"),
                new regionCode3("11H20601","밀양"),
                new regionCode3("11H20603","함안"),
                new regionCode3("11H20604","창녕"),
                new regionCode3("11H20602","의령"),
                new regionCode3("11H20701","진주"),
                new regionCode3("11H20704","하동"),
                new regionCode3("11H20402","사천"),
                new regionCode3("11H20502","거창"),
                new regionCode3("11H20503","합천"),
                new regionCode3("11H20703","산청"),
                new regionCode3("11H20501","함양"),
                new regionCode3("11H20401","통영"),
                new regionCode3("11H20403","거제"),
                new regionCode3("11H20404","고성"),
                new regionCode3("11H20405","남해"),
                new regionCode3("11H10701","대구"),
                new regionCode3("11H10702","영천"),
                new regionCode3("11H10703","경산"),
                new regionCode3("11H10704","청도"),
                new regionCode3("11H10705","칠곡"),
                new regionCode3("11H10601","김천"),
                new regionCode3("11H10602","구미"),
                new regionCode3("11H10603","군위"),
                new regionCode3("11H10604","고령"),
                new regionCode3("11H10605","성주"),
                new regionCode3("11H10501","안동"),
                new regionCode3("11H10502","의성"),
                new regionCode3("11H10503","청송"),
                new regionCode3("11H10302","상주"),
                new regionCode3("11H10301","문경"),
                new regionCode3("11H10303","예천"),
                new regionCode3("11H10401","영주"),
                new regionCode3("11H10402","봉화"),
                new regionCode3("11H10403","영양"),
                new regionCode3("11H10101","울진"),
                new regionCode3("11H10102","영덕"),
                new regionCode3("11H10201","포항"),
                new regionCode3("11H10202","경주"),
                new regionCode3("11E00101","울릉도"),
                new regionCode3("11E00102","독도"),
                new regionCode3("11F20501","광주"),//광주광역시임 그래서 위의 경기도 광주랑 어떻게 구별할 지 논의 해봐야함
                new regionCode3("11F20503","나주"),
                new regionCode3("11F20502","장성"),
                new regionCode3("11F20504","담양"),
                new regionCode3("11F20505","화순"),
                new regionCode3("21F20102","영광"),
                new regionCode3("21F20101","합평"),
                new regionCode3("21F20801","목포"),
                new regionCode3("21F20804","무안"),
                new regionCode3("21F20802","영암"),
                new regionCode3("21F20201","진도"),
                new regionCode3("21F20803","신안"),
                new regionCode3("11F20701","흑산도"),
                new regionCode3("11F206030","순천"),//??
                new regionCode3("11F20405","순천시"),//???
                new regionCode3("11F20402","광양"),
                new regionCode3("11F20601","구례"),
                new regionCode3("11F20602","곡성"),
                new regionCode3("11F20301","완도"),
                new regionCode3("11F20303","강진"),
                new regionCode3("11F20304","장흠"),
                new regionCode3("11F20302","해남"),
                new regionCode3("11F20401","여수"),
                new regionCode3("11F20403","고흥"),
                new regionCode3("11F20404","보성"),
                new regionCode3("11F10201","전주"),
                new regionCode3("11F10202","익산"),
                new regionCode3("21F10501","군산"),
                new regionCode3("11F10203","정읍"),
                new regionCode3("21F10502","김제"),
                new regionCode3("11F10401","남원"),
                new regionCode3("21F10601","고창"),
                new regionCode3("11F10302","무주"),
                new regionCode3("21F10602","부안"),
                new regionCode3("11F10403","순창"),
                new regionCode3("11F10204","완주"),
                new regionCode3("11F10402","임실"),
                new regionCode3("11F10301","장수"),
                new regionCode3("11F10303","진안"),
                new regionCode3("11C20401","대전"),
                new regionCode3("11C20404","세종"),
                new regionCode3("11C20402","공주"),
                new regionCode3("11C20602","논산"),
                new regionCode3("11C20403","계룡"),
                new regionCode3("11C20601","금산"),
                new regionCode3("11C20301","천안"),
                new regionCode3("11C20302","아산"),
                new regionCode3("11C20303","예산"),
                new regionCode3("11C20101","서산"),
                new regionCode3("11C20102","태안"),
                new regionCode3("11C20103","당진"),
                new regionCode3("11C20104","홍성"),
                new regionCode3("11C20201","보령"),
                new regionCode3("11C20202","서천"),
                new regionCode3("11C20502","청양"),
                new regionCode3("11C20501","부여"),
                new regionCode3("11C10301","청주"),
                new regionCode3("11C10304","증평"),
                new regionCode3("11C10303","괴산"),
                new regionCode3("11C10102","진천"),
                new regionCode3("11C10101","충주"),
                new regionCode3("11C10103","음성"),
                new regionCode3("11C10201","제천"),
                new regionCode3("11C10202","단양"),
                new regionCode3("11C10302","보은"),
                new regionCode3("11C10403","옥천"),
                new regionCode3("11C10402","영동"),
                new regionCode3("11C10401","추풍령"),
                new regionCode3("11D10101","철원"),
                new regionCode3("11D10102","화천"),
                new regionCode3("11D10201","인제"),
                new regionCode3("11D10202","양구"),
                new regionCode3("11D10301","춘천"),
                new regionCode3("11D10302","홍천"),
                new regionCode3("11D10401","원주"),
                new regionCode3("11D10402","횡성"),
                new regionCode3("11D10501","영월"),
                new regionCode3("11D10502","정선"),
                new regionCode3("11D10503","평창"),
                new regionCode3("11D20201","대관령"),
                new regionCode3("11D20401","속초"),
                new regionCode3("11D20402","고성"),
                new regionCode3("11D20403","양양"),
                new regionCode3("11D20501","강릉"),
                new regionCode3("11D20601","동해"),
                new regionCode3("11D20602","삼척"),
                new regionCode3("11D20301","태백"),
                new regionCode3("11G00201","제주"),
                new regionCode3("11G00401","서귀포"),
                new regionCode3("11G00101","성산"),
                new regionCode3("11G00501","고산"),
                new regionCode3("11G00302","성판악"),
                new regionCode3("11G00601","이어도"),
                new regionCode3("11G00800","추자도")
        };
        for(regionCode3 p : data){
            regionCode3s.add(p);
        }
    }
}
