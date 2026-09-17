public interface VaultSecurable {

    SecurityLevel evaluateSecurity();

    String toEncodedString();
}