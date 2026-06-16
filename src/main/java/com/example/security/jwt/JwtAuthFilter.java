package com.example.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService; // इसे हम Step 3 में security पैकेज में कॉन्फ़िगर करेंगे

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. HTTP Request के Header से "Authorization" की वैल्यू निकालें
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. चेक करें कि हेडर खाली तो नहीं है और क्या वह "Bearer " से शुरू हो रहा है?
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // अगर टोकन नहीं है, तो रिक्वेस्ट को आगे बढ़ा दें (जैसे /login के लिए)
            return;
        }

        // 3. "Bearer " हटाकर असली JWT टोकन अलग करें (index 7 से शुरू होता है)
        jwt = authHeader.substring(7);

        // 4. JwtService की मदद से टोकन के अंदर से यूज़रनेम निकालें
        username = jwtService.extractUsername(jwt);

        // 5. अगर यूज़रनेम मिल गया है और वह अभी तक SecurityContext में ऑथेंटिकेटेड नहीं है
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // डेटाबेस से यूज़र की डिटेल्स लोड करें
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. चेक करें कि क्या टोकन पूरी तरह वैलिड और सही यूज़र का है
            if (jwtService.validateToken(jwt, userDetails)) {

                // अगर सही है, तो Spring Security के लिए एक ऑथेंटिकेशन ऑब्जेक्ट बनाएं
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. SecurityContextHolder में यूज़र को सेट कर दें (अब Spring मान लेगा कि यूज़र लॉग इन है)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. रिक्वेस्ट को अगले फ़िल्टर या कंट्रोलर के पास भेजें
        filterChain.doFilter(request, response);
    }
}
