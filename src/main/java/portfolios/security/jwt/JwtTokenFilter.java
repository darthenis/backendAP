
package portfolios.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import portfolios.security.service.UserDetailsServiceImpl;

@Slf4j
public class JwtTokenFilter  extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired 
    UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         try{

                    String authHeader = request.getHeader(AUTHORIZATION);
                            
                    if(jwtProvider.isBearer(authHeader)){
                    
                            String username = jwtProvider.getUsernameFromToken(authHeader);
                            
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          
                            UsernamePasswordAuthenticationToken authToken = 
                                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                                     
                    }
                    
            } catch (JWTDecodeException | UsernameNotFoundException e){
            
                logger.error("fail en el metodo filter");
                    
            }
                    

        filterChain.doFilter(request, response);
        
    }
    
    

    
}
