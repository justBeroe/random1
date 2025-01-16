//package random;
//
//import lombok.SneakyThrows;
////import org.apache.commons.codec.binary.Base64;
////import org.apache.commons.lang3.StringUtils;
////import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
////import org.bouncycastle.jce.provider.BouncyCastleProvider;
////import org.bouncycastle.openssl.PEMParser;
////import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//
//import java.io.StringReader;
//import java.nio.charset.StandardCharsets;
//import java.security.PublicKey;
//import java.security.Security;
//import java.security.Signature;
//import java.util.List;
//import java.util.function.Predicate;
//
//public class SignatureValidator1 {
//    private static final String PEM_HEADER = "-----BEGIN PUBLIC KEY-----\n";
//    private static final String PEM_FOOTER = "\n-----END PUBLIC KEY-----";
//    private static final String SIGNING_ALGORITHM = "SHA256withECDSA";
//    private static final Predicate<SignedPublicKey> IS_PIN_KEY = key -> StringUtils.equals("PIN", key.getKeyType());
//
//    public SignatureValidator1() {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    public void validateSignatures(String encodedAnonymousKey, List<SignedPublicKey> publicKeys) {
//        var pinKey = getPinKey(publicKeys);
//        assertKeySignature(pinKey.getEncoded(), pinKey.getSignature(), parseEncodedPublicKey(encodedAnonymousKey));
//        assertChildKeyProperties(publicKeys, pinKey);
//    }
//
//    private SignedPublicKey getPinKey(List<SignedPublicKey> publicKeys) {
//        return publicKeys.stream()
//                .filter(IS_PIN_KEY)
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No PIN key found"));
//    }
//
//    @SneakyThrows
//    private void assertKeySignature(String signedValue, String signature, PublicKey publicKey) {
//        var verifier = Signature.getInstance(SIGNING_ALGORITHM);
//        verifier.initVerify(publicKey);
//        verifier.update(signedValue.getBytes(StandardCharsets.UTF_8));
//        var signatureIsValid = verifier.verify(Base64.decodeBase64(signature));
//        AssertUtils.assertTrue(signatureIsValid, InvalidSignatureException::new);
//    }
//
//    private void assertChildKeyProperties(List<SignedPublicKey> publicKeys, SignedPublicKey pinKey) {
//        var pinPublicKey = parsePublicKey(pinKey.getEncoded());
//        publicKeys.stream()
//                .filter(key -> !IS_PIN_KEY.test(key))
//                .forEach(key -> assertKeySignature(key.getEncoded(), key.getSignature(), pinPublicKey));
//    }
//
//    @SneakyThrows
//    private PublicKey parseEncodedPublicKey(String encodedKey) {
//        var strippedPublicKey = new String(Base64.decodeBase64(encodedKey), StandardCharsets.UTF_8);
//        return parsePublicKey(PEM_HEADER + strippedPublicKey + PEM_FOOTER);
//    }
//
//    @SneakyThrows
//    private PublicKey parsePublicKey(String publicKey) {
//        try (var pemParser = new PEMParser(new StringReader(publicKey))) {
//            var key = pemParser.readObject();
//            return new JcaPEMKeyConverter().getPublicKey((SubjectPublicKeyInfo) key);
//        }
//    }
//}
