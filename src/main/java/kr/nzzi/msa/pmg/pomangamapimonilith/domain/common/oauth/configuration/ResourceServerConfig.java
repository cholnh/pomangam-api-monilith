package kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.oauth.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableResourceServer
@AllArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private CharacterEncodingFilter characterEncodingFilter;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource_id").stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(characterEncodingFilter, CsrfFilter.class)
            .anonymous().disable()
            .authorizeRequests()
            .antMatchers("/**").authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
