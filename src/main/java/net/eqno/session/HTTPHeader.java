package net.eqno.session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 *
 */
public class HTTPHeader {
    /**
     *
     * @param host The host header e.g. "localhost:8080"
     * @param connection The connection HTTP header value
     * @param pragma The pragma HTTP header value
     * @param cacheControl The cache control HTTP header value
     * @param userAgent The user agent HTTP header value
     * @param accept The accept HTTP header value
     * @param acceptEncoding The accept encoding HTTP header value
     * @param acceptLanguage The accept language HTTP header value
     * @return The MD5 hash of the provided HTTP headers
     */
    public static String generateHash(
            String host,
            String connection,
            String pragma,
            String cacheControl,
            String userAgent,
            String accept,
            String acceptEncoding,
            String acceptLanguage
    ) throws NoSuchAlgorithmException {
        String input = host +
                connection +
                pragma +
                cacheControl +
                userAgent +
                accept +
                acceptEncoding +
                acceptLanguage;

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(input.getBytes());
        return HexFormat.of().formatHex(messageDigest.digest());
    }
}
