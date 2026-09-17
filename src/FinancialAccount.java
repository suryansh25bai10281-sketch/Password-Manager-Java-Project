public class FinancialAccount extends VaultItem {

    private String accountNumber;

    public FinancialAccount(String itemId, String title, String username,
                            String password, String accountNumber)
            throws WeakPasswordException {

        super(itemId, title, username, password);
        this.accountNumber = accountNumber;
    }

    @Override
    public SecurityLevel evaluateSecurity() {

        String pass = getPassword();

        if (pass.length() >= 12
                && pass.matches(".*[0-9].*")
                && pass.matches(".*[!@#$%^&*].*")) {

            return SecurityLevel.STRONG;

        } else if (pass.length() >= 8
                && pass.matches(".*[0-9].*")) {

            return SecurityLevel.MODERATE;
        }

        return SecurityLevel.WEAK;
    }

    @Override
    public String toEncodedString() {

        String raw = String.format(
                "BANK|%s|%s|%s|%s|%s|%s",
                getItemId(),
                getTitle(),
                getUsername(),
                getPassword(),
                accountNumber,
                evaluateSecurity().name()
        );

        return encode(raw);
    }

    @Override
    public void displayDetails() {

        System.out.printf(
                "[BANK] ID: %s | Title: %-15s | User: %-12s | Pass: %-8s | Acc: %s\n",
                getItemId(),
                getTitle(),
                getUsername(),
                maskPassword(),
                accountNumber
        );
    }
}
