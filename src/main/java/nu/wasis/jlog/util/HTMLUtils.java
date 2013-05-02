package nu.wasis.jlog.util;

public final class HTMLUtils {

    private HTMLUtils() {
        // static only
    }

    // This is not a `save' stripping. Use for internal html only.
    public static String stripHtmlTags(final String string) {
        return string.replaceAll("<(.|\n)*?>", "");
    }

}
