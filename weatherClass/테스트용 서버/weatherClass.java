//이 파일은 이제 안 씀
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.print.attribute.standard.Destination;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class weatherClass {
    private Calendar cal;
    private int month;
    private int date;
    private int year;
    private String serviceKey;
    private String middleLandWeather;
    private String getMiddleTemperature;
    private String getMiddleLandWeatherConf;
    private String request;
    private String regi;
    private String code;
    private String result;
    public static List<regionCode> regionCodes = new LinkedList<>();
    public static List<regionCode2> regionCode2s = new LinkedList<>();

    weatherClass(String request) {

        cal = Calendar.getInstance();//컴퓨터에서 시간 가져오는 방법
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        /*
        TimeZone time; //서버에서 시간 가져오는 방법
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");

        time = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(time);
        System.out.format("%s%n%s%n%n", time.getDisplayName(), df.format(date));
        */
        serviceKey = "%2Bl%2FKPa6QZkGmgPy%2F2ZredqUaNXOmfYXePtFsA%2BIJXkqw0R4wSeq1CVd%2BPY10FJpYKYvp3bBihTY0tKEje%2Fm8hg%3D%3D";
        middleLandWeather = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleLandWeather";//맑음 흐림 이런 거
        getMiddleTemperature = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleTemperature";// 온도
        getMiddleLandWeatherConf = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleLandWeatherConf";//신뢰도

        initialize();
        initialize2();
        this.request = request;
    }

    public String run() throws ParseException {


        String[] parts=request.split("-");
        StringBuilder resultBuilding = new StringBuilder("");
        String part = parts[0];
        String part2 = parts[1];
        String part3 = parts[2];
        regionCode regionco = (regionCodes.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));
        String code = regionco.getCode();
        StringBuilder urlAdr = new StringBuilder(middleLandWeather);
        urlAdr.append("?ServiceKey=");

        urlAdr.append(serviceKey);
        urlAdr.append("&regId=");
        urlAdr.append(code);
        urlAdr.append("&tmFc=");
        urlAdr.append(year);
        if(month<10)
            urlAdr.append("0");
        urlAdr.append(month);
        if(date<10){
            urlAdr.append("0");
        }
        urlAdr.append(date);
        urlAdr.append("0600");

        urlAdr.append("&numOfRows=1&pageNo=1");
        System.out.println(urlAdr.toString());
        BufferedReader br = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        StringBuilder todayBuild = new StringBuilder("");
        todayBuild.append(year);
        todayBuild.append("-");
        if(month<10)
            todayBuild.append("0");
        todayBuild.append(month);
        todayBuild.append("-");
        if(date<10)
            todayBuild.append("0");
        todayBuild.append(date);
        String today = todayBuild.toString();
        StringBuilder destinationBuilder = new StringBuilder("");
        String destinationday;
        String destinationparts[] = part3.split(",");
        String dpy = destinationparts[0];
        String dpm = destinationparts[1];
        String dpd = destinationparts[2];
        destinationBuilder.append(dpy);
        destinationBuilder.append("-");
        destinationBuilder.append(dpm);
        destinationBuilder.append("-");
        destinationBuilder.append(dpd);
        destinationday = destinationBuilder.toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        Date todayDate = format.parse(destinationday);
        Date destiDate = format.parse(today);
        long calDate = todayDate.getTime() - destiDate.getTime();
        long calDateDays = calDate / ( 24*60*60*1000);

        //calDateDays = Math.abs(calDateDays);
        if(calDateDays>3&&calDateDays<=10){//3일후
            String datedays = Long.toString(calDateDays);
            //System.out.print("날짜 차이 : ");
            //System.out.println(datedays);
            try {
                URL url = new URL(urlAdr.toString());
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
                String resulta = "";
                String line;
                while ((line = br.readLine()) != null) {
                    resulta = resulta + line.trim();
                }
                InputSource is = new InputSource(new StringReader(resulta));
                builder = factory.newDocumentBuilder();
                doc = builder.parse(is);
                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("//items/item");
                NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    NodeList child = nodeList.item(i).getChildNodes();
                    for (int j = 0; j < child.getLength(); j++) {
                        Node node = child.item(j);
                        if (node.getNodeName().contains(datedays)) {

                            resultBuilding.append(node.getTextContent());


                            resultBuilding.append("\n");
                            break;
                        }

                    }
                }
                StringBuilder stringBuilder = new StringBuilder(getMiddleTemperature);
                stringBuilder.append("?ServiceKey=");
                stringBuilder.append(serviceKey);
                stringBuilder.append("&regId=");
                regionCode2 regionCode2 = (regionCode2s.stream().filter(e -> e.getRegion().contains(part)).findFirst().orElse(null));
                String code2 = regionCode2.getCode();
                stringBuilder.append(code2);
                stringBuilder.append("&tmFc=");
                stringBuilder.append(year);
                if (month < 10)
                    stringBuilder.append("0");
                stringBuilder.append(month);
                if (date < 10)
                    stringBuilder.append("0");
                stringBuilder.append(date);
                stringBuilder.append("0600");
                stringBuilder.append("&numOfRows=1&pageNo=1");
                System.out.println(stringBuilder.toString());

                BufferedReader br2 = null;
                DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
                factory2.setNamespaceAware(true);
                DocumentBuilder builder2;
                Document doc2 = null;
                URL url2 = new URL(stringBuilder.toString());
                HttpURLConnection urlconnection2 = (HttpURLConnection) url2.openConnection();
                br2 = new BufferedReader(new InputStreamReader(urlconnection2.getInputStream(), "UTF-8"));
                String resulta2 = "";
                String line2;
                while ((line2 = br2.readLine()) != null) {
                    resulta2 = resulta2 + line2.trim();
                }
                InputSource is2 = new InputSource(new StringReader(resulta2));
                builder2 = factory2.newDocumentBuilder();
                doc2 = builder2.parse(is2);
                XPathFactory xpathFactory2 = XPathFactory.newInstance();
                XPath xpath2 = xpathFactory2.newXPath();
                XPathExpression expr2 = xpath2.compile("//items/item");
                NodeList nodeList2 = (NodeList) expr2.evaluate(doc2, XPathConstants.NODESET);
                int f=0;
                for (int i = 0; i < nodeList2.getLength(); i++) {
                    NodeList child2 = nodeList2.item(i).getChildNodes();
                    for (int j = 0; j < child2.getLength(); j++) {
                        Node node2 = child2.item(j);
                        if (node2.getNodeName().contains(datedays)) {
                            if(f==0){
                                resultBuilding.append("최고 온도 : ");
                                f++;
                            }
                            else if(f==1){
                                resultBuilding.append("최저 온도 : ");
                            }
                            resultBuilding.append(node2.getTextContent());


                            resultBuilding.append("\n");

                        }

                    }

                }
                StringBuilder stringBuilder3 = new StringBuilder(getMiddleLandWeatherConf);
                stringBuilder3.append("?ServiceKey=");
                stringBuilder3.append(serviceKey);
                stringBuilder3.append("&regId=");
                stringBuilder3.append(code);
                stringBuilder3.append("&tmFc=");
                stringBuilder3.append(year);
                if(month<10)
                    stringBuilder3.append("0");
                stringBuilder3.append(month);
                if(date<10)
                    stringBuilder3.append("0");
                stringBuilder3.append(date);
                stringBuilder3.append("0600");
                stringBuilder3.append("&numOfRows=1&pageNo=1");
                System.out.println(stringBuilder3.toString());
                BufferedReader br3 = null;
                DocumentBuilderFactory factory3 = DocumentBuilderFactory.newInstance();
                factory3.setNamespaceAware(true);
                DocumentBuilder builder3;
                Document doc3 = null;
                URL url3 = new URL(stringBuilder3.toString());
                HttpURLConnection urlconnection3 = (HttpURLConnection) url3.openConnection();
                br3 = new BufferedReader(new InputStreamReader(urlconnection3.getInputStream(), "UTF-8"));
                String resulta3 = "";
                String line3;
                while ((line3 = br3.readLine()) != null) {
                    resulta3 = resulta3 + line3.trim();
                }
                InputSource is3 = new InputSource(new StringReader(resulta3));
                builder3 = factory3.newDocumentBuilder();
                doc3 = builder3.parse(is3);
                XPathFactory xpathFactory3 = XPathFactory.newInstance();
                XPath xpath3 = xpathFactory3.newXPath();
                XPathExpression expr3 = xpath3.compile("//items/item");
                NodeList nodeList3 = (NodeList) expr3.evaluate(doc3, XPathConstants.NODESET);
                int count=0;
                for (int i = 0; i < nodeList3.getLength(); i++) {
                    NodeList child3 = nodeList3.item(i).getChildNodes();
                    for (int j = 0; j < child3.getLength(); j++) {
                        Node node3 = child3.item(j);
                        if (node3.getNodeName().contains(datedays)) {
                                if(count==0){
                                    count++;
                                    resultBuilding.append("오전");
                                }else if(count==1){
                                    count++;
                                    resultBuilding.append("오후");
                                }

                                resultBuilding.append("신뢰도 : ");



                            resultBuilding.append(node3.getTextContent());


                            resultBuilding.append("\n");

                        }

                    }

                }



            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }

        }
        else if(calDateDays>10){//너무 먼 미래
            resultBuilding.append("너무 먼 미래라서 알 수가 없다");
        }
        else if(calDateDays<2&&calDateDays>=0){//모레~현재

        }
        else if(calDateDays<0){//과거

        }

        result=resultBuilding.toString();

        //System.out.println(result);








        return result;
    }
    public static void initialize(){
        regionCode[] data ={//중기 육상, 신뢰도
                new regionCode("11B00000","서울, 인천,경기도,수원,파주"),
                new regionCode("11D10000","강원도영서,춘천, 원주"),
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
        regionCode2[] data = {
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
}
