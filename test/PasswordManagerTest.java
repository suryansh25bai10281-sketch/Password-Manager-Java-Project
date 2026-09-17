public class PasswordManagerTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testWeakPassword();
        testWebModeratePassword();
        testWebStrongPassword();
        testBankModeratePassword();
        testBankStrongPassword();

        testWebDetails();
        testBankDetails();

        testEncodedWebRecord();
        testEncodedBankRecord();

        testPasswordUpdate();
        testInvalidPasswordUpdate();

        testDifferentAccountTypes();
        testPasswordMasking();

        testMinimumLengthPassword();
        testLongPassword();

        System.out.println("\n-----------------------------");
        System.out.println("Tests Passed: " + passed);
        System.out.println("Tests Failed: " + failed);
        System.out.println("-----------------------------");

        if (failed == 0) {
            System.out.println("All tests completed successfully.");
        } else {
            System.out.println("Some tests failed.");
        }
    }

    private static void testWeakPassword() {

        try {
            new WebAccount(
                    "W1",
                    "Test",
                    "user",
                    "abc",
                    "example.com"
            );

            fail("Weak password should be rejected.");

        } catch (WeakPasswordException e) {
            pass("Weak password rejected");
        }
    }

    private static void testWebModeratePassword() {

        try {
            WebAccount account = new WebAccount(
                    "W2",
                    "GitHub",
                    "student",
                    "password",
                    "github.com"
            );

            check(
                    account.evaluateSecurity() == SecurityLevel.MODERATE,
                    "Web moderate password"
            );

        } catch (WeakPasswordException e) {
            fail("Web moderate password caused an exception.");
        }
    }

    private static void testWebStrongPassword() {

        try {
            WebAccount account = new WebAccount(
                    "W3",
                    "Gmail",
                    "student",
                    "Password123!",
                    "gmail.com"
            );

            check(
                    account.evaluateSecurity() == SecurityLevel.STRONG,
                    "Web strong password"
            );

        } catch (WeakPasswordException e) {
            fail("Web strong password caused an exception.");
        }
    }

    private static void testBankModeratePassword() {

        try {
            FinancialAccount account = new FinancialAccount(
                    "F1",
                    "Bank",
                    "student",
                    "bankpass1",
                    "12345678"
            );

            check(
                    account.evaluateSecurity() == SecurityLevel.MODERATE,
                    "Bank moderate password"
            );

        } catch (WeakPasswordException e) {
            fail("Bank moderate password caused an exception.");
        }
    }

    private static void testBankStrongPassword() {

        try {
            FinancialAccount account = new FinancialAccount(
                    "F2",
                    "Bank",
                    "student",
                    "BankPassword123!",
                    "12345678"
            );

            check(
                    account.evaluateSecurity() == SecurityLevel.STRONG,
                    "Bank strong password"
            );

        } catch (WeakPasswordException e) {
            fail("Bank strong password caused an exception.");
        }
    }

    private static void testWebDetails() {

        try {
            WebAccount account = new WebAccount(
                    "W4",
                    "Website",
                    "student",
                    "mypassword",
                    "example.com"
            );

            account.displayDetails();
            pass("Web account display");

        } catch (WeakPasswordException e) {
            fail("Web account display test failed.");
        }
    }

    private static void testBankDetails() {

        try {
            FinancialAccount account = new FinancialAccount(
                    "F3",
                    "Bank",
                    "student",
                    "bankpass1",
                    "12345678"
            );

            account.displayDetails();
            pass("Financial account display");

        } catch (WeakPasswordException e) {
            fail("Financial account display test failed.");
        }
    }

    private static void testEncodedWebRecord() {

        try {
            WebAccount account = new WebAccount(
                    "W5",
                    "GitHub",
                    "student",
                    "Password123!",
                    "github.com"
            );

            String encoded = account.toEncodedString();

            check(
                    encoded != null && !encoded.isEmpty(),
                    "Web record encoding"
            );

        } catch (WeakPasswordException e) {
            fail("Web encoding test failed.");
        }
    }

    private static void testEncodedBankRecord() {

        try {
            FinancialAccount account = new FinancialAccount(
                    "F4",
                    "Bank",
                    "student",
                    "BankPassword123!",
                    "12345678"
            );

            String encoded = account.toEncodedString();

            check(
                    encoded != null && !encoded.isEmpty(),
                    "Bank record encoding"
            );

        } catch (WeakPasswordException e) {
            fail("Bank encoding test failed.");
        }
    }

    private static void testPasswordUpdate() {

        try {
            WebAccount account = new WebAccount(
                    "W6",
                    "Test",
                    "student",
                    "oldpass",
                    "example.com"
            );

            account.setPassword("NewPassword1!");

            check(
                    account.getPassword().equals("NewPassword1!"),
                    "Password update"
            );

        } catch (WeakPasswordException e) {
            fail("Valid password update failed.");
        }
    }

    private static void testInvalidPasswordUpdate() {

        try {
            WebAccount account = new WebAccount(
                    "W7",
                    "Test",
                    "student",
                    "oldpass",
                    "example.com"
            );

            account.setPassword("abc");

            fail("Invalid password update should be rejected.");

        } catch (WeakPasswordException e) {
            pass("Invalid password update rejected");
        }
    }

    private static void testDifferentAccountTypes() {

        try {
            WebAccount web = new WebAccount(
                    "W8",
                    "Web",
                    "user",
                    "password",
                    "example.com"
            );

            FinancialAccount bank = new FinancialAccount(
                    "F5",
                    "Bank",
                    "user",
                    "bankpass1",
                    "12345678"
            );

            check(
                    web instanceof VaultItem
                            && bank instanceof VaultItem,
                    "Account inheritance"
            );

        } catch (WeakPasswordException e) {
            fail("Account inheritance test failed.");
        }
    }

    private static void testPasswordMasking() {

        try {
            WebAccount account = new WebAccount(
                    "W9",
                    "Test",
                    "student",
                    "Password123!",
                    "example.com"
            );

            account.displayDetails();
            pass("Password masking display");

        } catch (WeakPasswordException e) {
            fail("Password masking test failed.");
        }
    }

    private static void testMinimumLengthPassword() {

        try {
            WebAccount account = new WebAccount(
                    "W10",
                    "Test",
                    "student",
                    "abcdef",
                    "example.com"
            );

            check(
                    account.getPassword().length() == 6,
                    "Minimum password length"
            );

        } catch (WeakPasswordException e) {
            fail("Six-character password should be accepted.");
        }
    }

    private static void testLongPassword() {

        try {
            WebAccount account = new WebAccount(
                    "W11",
                    "Test",
                    "student",
                    "VeryLongPassword123!",
                    "example.com"
            );

            check(
                    account.evaluateSecurity() == SecurityLevel.STRONG,
                    "Long strong password"
            );

        } catch (WeakPasswordException e) {
            fail("Long password test failed.");
        }
    }

    private static void check(boolean condition, String testName) {

        if (condition) {
            pass(testName);
        } else {
            fail(testName);
        }
    }

    private static void pass(String testName) {

        passed++;
        System.out.println("PASS: " + testName);
    }

    private static void fail(String testName) {

        failed++;
        System.out.println("FAIL: " + testName);
    }
}