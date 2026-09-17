public class WebAccount extends VaultItem {

    private String siteUrl;

    public WebAccount(String itemId, String title, String username,
                      String password, String siteUrl)
            throws WeakPasswordException {

        super(itemId, title, username, password);
        this.siteUrl = siteUrl;
    }

    @Override
    public SecurityLevel evaluateSecurity() {

        String pass = getPassword();

        if (pass.length() >= 10
                && pass.matches(".*[0-9].*")
                && pass.matches(".*[!@#$%^&*].*")) {

            return SecurityLevel.STRONG;

        } else if (pass.length() >= 8) {

            return SecurityLevel.MODERATE;
        }

        return SecurityLevel.WEAK;
    }

    @Override
    public String toEncodedString() {

        String raw = String.format(
                "WEB|%s|%s|%s|%s|%s|%s",
                getItemId(),
                getTitle(),
                getUsername(),
                getPassword(),
                siteUrl,
                evaluateSecurity().name()
        );

        return encode(raw);
    }

    @Override
    public void displayDetails() {

        System.out.printf(
                "[WEB]  ID: %s | Title: %-15s | User: %-12s | Pass: %-8s | URL: %s\n",
                getItemId(),
                getTitle(),
                getUsername(),
                maskPassword(),
                siteUrl
        );
    }
}
