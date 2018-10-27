import java.util.Calendar;
import java.util.GregorianCalendar;

public class getDate {
    private Calendar cal;
    private int month;
    private int date;
    private int year;
    private int hour;
    String dateBuild;
    public String SimpleFormToday(){
        String SimpleToday;
        StringBuilder SimpleTodayBuilder = new StringBuilder("");
        SimpleTodayBuilder.append(year);
        SimpleTodayBuilder.append("-");
        if(month<10)
            SimpleTodayBuilder.append("0");
        SimpleTodayBuilder.append(month);
        SimpleTodayBuilder.append("-");
        if(date<10)
            SimpleTodayBuilder.append("0");
         SimpleTodayBuilder.append(date);
         SimpleToday=SimpleTodayBuilder.toString();
        return SimpleToday;
    }
    public String getDate(){

        StringBuilder resultBuilding = new StringBuilder("");

        cal = Calendar.getInstance();//컴퓨타에서 시간 가져오는 방법
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        /*
        TimeZone time; //서버에서 시간 가져오는 방법
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z Z)");

        time = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(time);
        System.out.format("%s%n%s%n%n", time.getDisplayName(), df.format(date));
        */
        //System.out.println(hour);
        if(hour>18){

        }
        else if(hour<6){
            Calendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.add(Calendar.DATE, -1);
            year = gregorianCalendar.get(Calendar.YEAR);
            month = (gregorianCalendar.get(Calendar.MONTH)+1);
            date = gregorianCalendar.get(Calendar.DATE);
            System.out.println("새벽에 돌리네");
        }
        else{

        }
        resultBuilding.append(year);
        if(month<10)
            resultBuilding.append("0");
        resultBuilding.append(month);
        if(date<10){
            resultBuilding.append("0");
        }
        resultBuilding.append(date);
        if(hour<6||hour>=18){
            resultBuilding.append("1800");
        }
        else if(hour>=6||hour<18){
            resultBuilding.append("0600");
        }
        dateBuild=resultBuilding.toString();
        return dateBuild;
    }
}
