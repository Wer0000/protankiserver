package ua.lann.protankiserver.security;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHasher {
    public static String hash(String input) {
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(input, salt);
    }

    public static boolean verify(String hash, String input) {
        return BCrypt.checkpw(input, hash);
    }
}
