package su.binair.andasia.utils.time;

public class TimeUtils
{
    public static String secondToMinSec(final int pTime) {
        final int min = pTime / 60;
        final int sec = pTime - min * 60;
        final String strMin = setZero(min);
        final String strSec = setZero(sec);
        return String.format("%s:%s", strMin, strSec);
    }
    
    public static String setZero(final int number) {
        return (number >= 10) ? Integer.toString(number) : String.format("0%s", Integer.toString(number));
    }
}
