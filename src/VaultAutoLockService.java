public class VaultAutoLockService implements Runnable {

    private int lockDurationSeconds;

    public VaultAutoLockService(int seconds) {
        this.lockDurationSeconds = seconds;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(lockDurationSeconds * 1000L);

            PasswordManagerApp.isLocked = true;

            System.out.println(
                    "\n[SYSTEM] Session time limit reached. Vault is now locked."
            );

        } catch (InterruptedException e) {
            // Timer stopped when the user exits normally.
        }
    }
}
