package com.example.empuser.security;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String SECRET;
  @Value("${jwt.expiration-ms:86400000}") 
  private long EXPIRATION_MS;
  public String generateToken(String username, String role)
  { return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_MS)).signWith(SignatureAlgorithm.HS256, SECRET).compact(); }
  public String extractUsername(String token)
  { return extractClaim(token, Claims::getSubject);
  }
  public <T> T extractClaim(String token, Function<Claims,T> resolver)
  { final Claims claims = Jwts.parser().setSigningKey(SECRET).build().parseClaimsJws(token).getBody(); return resolver.apply(claims);
  }
  public boolean validateToken(String token, String username)
  { final String u=extractUsername(token); 
  return (u.equals(username) && !isTokenExpired(token)); }
  private boolean isTokenExpired(String token)
  { final Date exp = extractClaim(token, Claims::getExpiration);
  return exp.before(new Date()); }
}