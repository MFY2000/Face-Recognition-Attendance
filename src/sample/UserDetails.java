package sample;


public class UserDetails {
    private static String address;
    private static String fname;
    private static String code;
    private static String lname;
    private static String reg;
    private static String age;
    private static String Section;

    public static void setAddress(String address) {
        UserDetails.address = address;
    }

    public static void setAge(String age) {
        UserDetails.age = age;
    }

    public static void setCode(String code) {
        UserDetails.code = code;
    }

    public static void setFname(String fname) {
        UserDetails.fname = fname;
    }

    public static void setLname(String lname) {
        UserDetails.lname = lname;
    }

    public static void setReg(String reg) {
        UserDetails.reg = reg;
    }

    public static void setSection(String section) {
        Section = section;
    }

    public static String getAddress() {
        return address;
    }

    public static String getFname() {
        return fname;
    }

    public static String getAge() {
        return age;
    }

    public static String getCode() {
        return code;
    }

    public static String getLname() {
        return lname;
    }

    public static String getReg() {
        return reg;
    }

    public static String getSection() {
        return Section;
    }
}


final class UserHolder {

    private UserDetails user;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder() {
    }

    public static UserHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(UserDetails u) {
        this.user = u;
    }

    public UserDetails getUser() {
        return this.user;
    }
}
