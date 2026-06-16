package com.example.security;


import com.example.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // Constructor Injection के द्वारा JwtAuthFilter को यहाँ ला रहे हैं
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // 1. SecurityFilterChain: यह तय करता है कि कौन सी रिक्वेस्ट ब्लॉक होगी और कौन सी चालू
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF को डिसेबल करें (क्योंकि हम JWT इस्तेमाल कर रहे हैं)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Login/Register APIs के लिए किसी टोकन की ज़रूरत नहीं है
                        .anyRequest().authenticated() // बाकी सभी APIs के लिए JWT टोकन ज़रूरी है
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // सर्वर पर कोई सेशन सेव नहीं होगा (Stateless)
                )
                .authenticationProvider(authenticationProvider()) // डेटाबेस से यूज़र चेक करने के लिए प्रदाता (Provider)
                // 2. अपने JWT फ़िल्टर को Spring के डिफ़ॉल्ट फ़िल्टर से पहले जोड़ें
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 3. UserDetailsService: अभी टेस्टिंग के लिए हम मेमोरी में एक डमी यूज़र बना रहे हैं
    // (अगले स्टेप में हम इसे असली MySQL/H2 डेटाबेस से कनेक्ट करेंगे)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("kamal")
                .password(passwordEncoder().encode("password123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    // 4. PasswordEncoder: पासवर्ड को सुरक्षित तरीके से हैश (Encrypt) करने के लिए
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 5. AuthenticationProvider: यह यूज़र डिटेल्स और पासवर्ड एनकोडर को आपस में जोड़ता है
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 6. AuthenticationManager: लॉगिन प्रोसेस को हैंडल करने का मुख्य मैनेजर
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
