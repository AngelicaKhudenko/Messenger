package by.angelica.pet_projects.mail_service.controller.filter;

import by.angelica.pet_projects.mail_service.controller.token.UserDetailsExpanded;
import by.angelica.pet_projects.mail_service.controller.utils.JwtTokenHandler;
import by.angelica.pet_projects.mail_service.service.feign.IUserServiceFeignClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtHandler;

    private final IUserServiceFeignClient userFeign;

    public JwtFilter(JwtTokenHandler jwtHandler,
                     IUserServiceFeignClient userFeign) {

        this.jwtHandler = jwtHandler;
        this.userFeign = userFeign;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {

            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();

        if (!jwtHandler.validate(token)) {

            chain.doFilter(request, response);
            return;
        }

        UserDetailsExpanded userDetails = this.userFeign.getUserDetails("Bearer " + token);

        if (userDetails == null) {

            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        authentication.setDetails(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}