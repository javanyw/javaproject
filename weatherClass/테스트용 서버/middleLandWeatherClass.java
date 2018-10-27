import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class middleLandWeatherClass {
    private String serviceKey;
    private String middleLandWeather;
    private String date;
    private String result;
    private String regID;
    private int dateDays;
    public middleLandWeatherClass(String date, String regID, int dateDays){
        serviceKey = "%2Bl%2FKPa6QZkGmgPy%2F2ZredqUaNXOmfYXePtFsA%2BIJXkqw0R4wSeq1CVd%2BPY10FJpYKYvp3bBihTY0tKEje%2Fm8hg%3D%3D";
        middleLandWeather = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleLandWeather";
        this.regID = regID;
        this.date = date;
        this.dateDays=dateDays;
    }
    public void run(){

        StringBuilder resultBuilding = new StringBuilder("\n");
        StringBuilder urlBuilder = new StringBuilder(middleLandWeather);
        String urlAdr;
        urlBuilder.append("?ServiceKey=");
        urlBuilder.append(serviceKey);
        urlBuilder.append("&regId=");
        urlBuilder.append(regID);
        urlBuilder.append("&tmFc=");
        urlBuilder.append(date);
        urlBuilder.append("&numOfRows=1&pageNo=1");
        System.out.println(urlBuilder.toString());
        urlAdr=urlBuilder.toString();
        try {
            String datedays = Integer.toString(dateDays);
            //System.out.println(datedays);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder;


            URL url = new URL(urlAdr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String resulta = "";
            String line;
            while ((line = br.readLine()) != null) {
                resulta = resulta + line.trim();
            }
            InputSource is = new InputSource(new StringReader(resulta));
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName().contains(datedays)) {
                        if(node.getNodeName().contains("Am")) {
                            resultBuilding.append("오전 : ");
                            resultBuilding.append(node.getTextContent());
                        }
                        else if(node.getNodeName().contains("Pm")){
                            resultBuilding.append("오후 : ");
                            resultBuilding.append(node.getTextContent());
                        }
                        else{
                            resultBuilding.append(node.getTextContent());
                        }
                        resultBuilding.append("\n");

                    }

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        result = resultBuilding.toString();

    }
    public String getResult(){
        return result;
    }
}
