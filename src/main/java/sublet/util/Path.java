package sublet.util;


public class Path {
    public static class Web{
        public static final String INDEX = "/";
        public static final String ABOUT = "/about";
        public static final String USER = "/user";
        public static final String NEWLISTING = USER + "/new";


    }
    public static class Template{
        private static final String PAGELOC = "/velocity/pages/";
        public static final String INDEX = PAGELOC + "index.vm";
        public static final String ABOUT = PAGELOC + "about.vm";
        public static final String NEWLISTING = PAGELOC + "new.vm";
        public static final String USER = PAGELOC + "user.vm";

    }
}
