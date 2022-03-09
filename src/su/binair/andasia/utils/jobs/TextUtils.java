package su.binair.andasia.utils.jobs;

public class TextUtils
{
    public static String formatJobsDescription(final String str) {
        str.toLowerCase();
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
