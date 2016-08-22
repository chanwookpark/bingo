package bingo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.script.ScriptTemplateConfigurer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;

/**
 * @author chanwook
 */
@Configuration
public class DustViewConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.scriptTemplate().prefix("/static/").suffix(".html");
    }

    @Bean
    public ScriptTemplateConfigurer scriptTemplateConfigurer() throws IOException {
        final ScriptTemplateConfigurer configurer = new ScriptTemplateConfigurer();
        configurer.setRenderFunction("render");
        configurer.setSharedEngine(true);
        configurer.setScripts("/static/dust.js", "/static/polyfill.js", "/META-INF/resources/webjars/dustjs-linkedin/2.7.2/dust-full.js");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        configurer.setEngine(engine);

        return configurer;
    }
}
