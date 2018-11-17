package life.laboratory.rosbank2018.server;

public class User {
    private String Login;
    private String Password;
    private String Page;

    public User(String login, String password, String page) {
        this.Login = login;
        this.Password = password;
        this.Page = page;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }
}
