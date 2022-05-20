
package portfolios.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtProvider {
    
            @Value("${jwt.secret}")
            private String secret;
            
            @Value("${jwt.expiration}")
            private int expiration; 
            
            private static final String ISSUER = "Portfolios Argentina Programa";
            private static final String BEARER = "Bearer ";
            private static final String ROLES = "ROLES";
            
            public String generateToken(Authentication authentication, int id){
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    
                    List<String> authorities = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
                    
                    return JWT.create()
                                        .withKeyId(Integer.toString(id))
                                        .withSubject(userDetails.getUsername())
                                        .withArrayClaim(ROLES, authorities.toArray(String[]::new))
                                        .withIssuer(ISSUER)
                                        .withExpiresAt(new Date(System.currentTimeMillis() + expiration * 100 ))
                                        .withIssuedAt(new Date())
                                        .sign(Algorithm.HMAC256(secret));
            }
            
            public boolean isBearer(String authorization){
            
                return authorization != null && authorization.startsWith("Bearer ") && authorization.split("\\.").length == 3;
            
            }
            
            public String getUsernameFromToken(String auth) throws JWTDecodeException {
            
                    return  this.validateToken(auth).getSubject();
                            
                            }
            
            public int getIdFromToken(String auth) throws JWTDecodeException{
            
                var result = this.validateToken(auth).getKeyId();
                
                return Integer.parseInt(result);
            
            }
            
            public List<String> getRolesFromToken(String authorization) throws JWTDecodeException {
            
                return Arrays.asList(this.validateToken(authorization).getClaim(ROLES).asArray(String.class));
            }
            
            public DecodedJWT validateToken(String authorization) throws JWTVerificationException, JWTDecodeException {
            
                    if(!this.isBearer(authorization)){
                    
                        throw new JWTVerificationException("It is not Bearer");
                    
                    }
                    
                    try{
                    
                            return JWT.require(Algorithm.HMAC256(secret))
                                                        .withIssuer(ISSUER).build()
                                                        .verify(authorization.substring(BEARER.length()));
                                                        
                    
                    } catch (JWTDecodeException e){
                    
                            throw new JWTDecodeException("JWT is wrong. " + e.getMessage());
                            
                    }
   
            }
   
}
