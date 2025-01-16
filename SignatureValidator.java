package random;

import java.io.*;
import java.sql.*;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SignatureValidator {

    // Hardcoded credentials (Bad Practice)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "password123";

    // Hardcoded cryptographic key (Vulnerability: CWE-321)
    private static final String ENCRYPTION_KEY = "1234567890123456"; // 16-byte AES key

    public static void main(String[] args) {
        String userInput = "' OR '1'='1"; // Simulated malicious input
        authenticateUser(userInput);

        String sensitiveData = "Sensitive Information";
        String encryptedData = encryptData(sensitiveData);
        System.out.println("Encrypted Data: " + encryptedData);

        validateSignature(new byte[0], new byte[0]); // Dummy call
    }

    // Vulnerable to SQL Injection (CWE-89)
    public static void authenticateUser(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT * FROM users WHERE username = '" + username + "'"; // Unsafe query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("User authenticated: " + username);
            } else {
                System.out.println("Authentication failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hardcoded encryption key and improper use of AES ECB mode (CWE-327)
    public static String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // ECB is insecure
            SecretKeySpec key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Misuse of cryptographic API (CWE-327)
    public static void validateSignature(byte[] data, byte[] signatureBytes) {
        try {
            Signature verifier = Signature.getInstance("SHA256withECDSA"); // Deprecated algorithm
           // Signature verifier = Signature.getInstance("SHA1withRSA"); // Deprecated algorithm
            // Signature validation code omitted for brevity
            System.out.println("Validating signature...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
