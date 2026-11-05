package br.unifil.projmongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Equivalente às rotas de página do app.py:
 *   "/"                -> index.html
 *   "/view/pessoa"     -> pessoa.html
 *   "/view/pet"        -> pet.html
 *   "/view/local"       -> local.html
 *   "/view/agendamento" -> agendamento.html
 */
@Configuration
public class ViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/view/pessoa").setViewName("forward:/views/pessoa.html");
        registry.addViewController("/view/pet").setViewName("forward:/views/pet.html");
        registry.addViewController("/view/local").setViewName("forward:/views/local.html");
        registry.addViewController("/view/agendamento").setViewName("forward:/views/agendamento.html");
    }
}
