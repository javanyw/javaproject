import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class calDateDays {
    String today;
    String destinationDay;
    public calDateDays(String today, String destinationDay){
        this.today = today;
        this.destinationDay = destinationDay;
    }
    public int datedays()  {
        int result = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date todayDate = format.parse(destinationDay);
            Date destiDate = format.parse(today);
            long calDate = todayDate.getTime() - destiDate.getTime();
            long calDateDay = calDate / ( 24*60*60*1000);
            result = (int)calDateDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("날짜 차이"+result);
        return result;
    }
}
