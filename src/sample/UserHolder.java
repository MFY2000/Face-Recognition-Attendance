package sample;

public final class UserHolder {

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
