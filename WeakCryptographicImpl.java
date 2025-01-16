package random;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class WeakCryptographicImpl {
    // Weakness: No validation of the algorithm's usage context or key management
    private static final String SIGNING_ALGORITHM = "SHA256withECDSA";

    public static void main(String[] args) {
        try {
            // Generate an ECDSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1")); // Using standard curve
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Sample data to sign
            String data = "Sensitive data to be signed";

            // Generate a signature
            byte[] signature = generateSignature(data, keyPair.getPrivate());
            System.out.println("Generated Signature: " + bytesToHex(signature));

            // Verify the signature
            boolean isVerified = verifySignature(data, signature, keyPair.getPublic());
            System.out.println("Signature Verified: " + isVerified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateSignature(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);

        // Potential Issue: Missing secure random or other customizations
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    public static boolean verifySignature(String data, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature verifier = Signature.getInstance(SIGNING_ALGORITHM);

        // Verification of algorithm left to default implementation
        verifier.initVerify(publicKey);
        verifier.update(data.getBytes());
        return verifier.verify(signatureBytes);
    }

    // Utility function to convert bytes to hex (for printing)
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
