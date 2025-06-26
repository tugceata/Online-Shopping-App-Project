public class LoginUser {
    private static String name;
    private static String surname;
    private static String userName;
    private static String email;
    private static String password;

    public static void setUser(String name, String surname, String userName, String email, String password) {
        LoginUser.name = name;
        LoginUser.surname = surname;
        LoginUser.userName = userName;
        LoginUser.email = email;
        LoginUser.password = password;
    }

    public static String getName() {
        return name;
    }

    public static String getSurname() {
        return surname;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }
}
