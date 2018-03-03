package sublet.util;



public class Path {
    public static class Web{
        public static final String INDEX = "/";
        public static final String ABOUT = "/about";
        public static final String USER = "/user";
        public static final String LISTING = "/listing";
        public static final String NEWLISTING = LISTING + "/create";
        public static final String LOGIN = USER + "/login";
        public static final String LOGOUT = USER + "/logout";
        public static final String NEWUSER = USER + "/register";


    }
    public static class Template{
        private static final String PAGELOC = "/velocity/pages/";
        public static final String INDEX = PAGELOC + "index.vm";
        public static final String ABOUT = PAGELOC + "about.vm";
        public static final String USER = PAGELOC + "user.vm";
        public static final String LISTING = PAGELOC + "listing.vm";
        public static final String LOGIN = PAGELOC + "login.vm";
        public static final String NEWUSER = PAGELOC + "newUser.vm";
    }
}
