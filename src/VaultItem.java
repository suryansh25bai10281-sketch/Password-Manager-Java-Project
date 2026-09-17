import java.util.Base64;

public abstract class VaultItem implements VaultSecurable {

    private String itemId;
    private String title;
    private String username;
    private String password;

    public VaultItem(String itemId, String title, String username, String password)
            throws WeakPasswordException {

        if (password == null || password.length() < 6) {
            throw new WeakPasswordException(
                    "Password too short (Minimum 6 characters).");
        }

        this.itemId = itemId;
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws WeakPasswordException {

        if (password == null || password.length() < 6) {
            throw new WeakPasswordException(
                    "Password too short (Minimum 6 characters).");
        }

        this.password = password;
    }

    protected String maskPassword() {
        if (password.length() <= 2) {
            return "**";
        }

        return password.charAt(0) + "****"
                + password.charAt(password.length() - 1);
    }

    protected String encode(String data) {
        return Base64.getEncoder()
                .encodeToString(data.getBytes());
    }

    public abstract void displayDetails();
}