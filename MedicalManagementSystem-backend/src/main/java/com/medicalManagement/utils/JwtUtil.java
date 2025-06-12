package com.medicalManagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
// SignatureAlgorithm is technically still available for legacy purposes but avoid direct use for key generation
// import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets; // Needed for byte conversion
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JSON Web Token (JWT) operations.
 * Handles creation and validation of JWTs.
 *
 * NOTE: For production, the SECRET_STRING MUST be securely loaded (e.g., from an environment variable),
 * do NOT hardcode it. It must be at least 256 bits (32 bytes) for HS256, so a very long, random string is needed.
 */
public class JwtUtil {

    // A strong, secret string used to derive the signing key.
    // For PRODUCTION, this MUST be a truly random, long string loaded from a secure source
    // like an environment variable or a secret management service.
    // Ensure its byte representation is at least 32 bytes (256 bits) for HS256.
    private static final String SECRET_STRING = "YourSuperSecureAndRandomSecretKeyForJWTsThatIsAtLeast32BytesLongForHS256Algorithm"; // <<< CHANGE THIS IN PRODUCTION
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    // Token validity duration (e.g., 24 hours in milliseconds)
    private static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 24 hours

    /**
     * Extracts the username (subject) from a JWT.
     * @param token The JWT string.
     * @return The subject (username/email) as a String.
     */
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT.
     * @param token The JWT string.
     * @return The expiration Date object.
     */
    
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT.
     * @param token The JWT string.
     * @param claimsResolver A function to resolve the desired claim from the Claims.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves all claims from the JWT.
     * @param token The JWT string.
     * @return The Claims object containing all claims.
     */
    
    private Claims getAllClaimsFromToken(String token) {
        // Correct usage for parsing in JJWT 0.10.0+
        return Jwts.parser()
        		.verifyWith(SECRET_KEY) // Set the signing key
                .build()
                .parseSignedClaims(token) // Parse the token
                .getPayload(); // Get the claims body 
    }

    /**
     * Checks if the token has expired.
     * @param token The JWT string.
     * @return true if the token is expired, false otherwise.
     */
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generates a JWT for a given staff member's email and role.
     * @param email The staff member's email (will be the subject of the token).
     * @param role The staff member's role (e.g., "Doctor", "Receptionist").
     * @param staffId The staff member's ID.
     * @return The generated JWT string.
     */
    
    public String generateToken(String email, String role, int staffId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Custom claim for role
        claims.put("staffId", staffId); // Custom claim for staff ID
        return doGenerateToken(claims, email);
    }

    /**
     * Internal method to build the JWT string.
     * @param claims Custom claims to include in the token.
     * @param subject The subject of the token (typically username/email).
     * @return The JWT string.
     */
    
    @SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
        // Correct usage for building in JJWT 0.10.0+ with Jwts.SIG
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Set the subject (username/email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set issued at time
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Set expiration time
                .signWith(SECRET_KEY, Jwts.SIG.HS256) // Use Jwts.SIG for algorithm specification (modern approach)
                .compact(); // Compact the token into a string
    }

    /**
     * Validates a JWT.
     * @param token The JWT string.
     * @param email The expected email (subject) of the token.
     * @return true if the token is valid for the given email and not expired, false otherwise.
     */
    public Boolean validateToken(String token, String email) {
        final String username = getUsernameFromToken(token);
        return (username.equals(email) && !isTokenExpired(token));
    }

    /**
     * Validates a JWT without a specific user.
     * Useful for checking if token is well-formed and not expired.
     * @param token The JWT string.
     * @return true if the token is valid and not expired, false otherwise.
     */
    public Boolean validateToken(String token) {
        try {
            // Attempt to parse to check signature and structure
            // This will throw an exception if the token is invalid, expired, or malformed
            getAllClaimsFromToken(token);
            return !isTokenExpired(token); // Check expiration separately after successful parsing
        } catch (Exception e) {
            // Log or handle specific exceptions (io.jsonwebtoken.security.SignatureException,
            // io.jsonwebtoken.ExpiredJwtException, io.jsonwebtoken.MalformedJwtException, etc.)
            System.err.println("JWT Validation Error: " + e.getMessage());
            return false;
        }
    }
}
