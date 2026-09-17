public enum SecurityLevel {
    WEAK("Short password or does not meet the required conditions."),
    MODERATE("Password meets the minimum length requirements."),
    STRONG("Long password containing numbers and special characters.");

    private final String description;

    SecurityLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
