package life.laboratory.rosbank2018.server;

public class User {
    private String Login;
    private String Password;

    public User(String login, String password) {
        this.Login = login;
        this.Password = password;
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
}
