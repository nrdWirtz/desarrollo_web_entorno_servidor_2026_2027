package dwes.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class BibliotecaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void laRaizRedirigeAlListado() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listado"));
    }

    @Test
    void elListadoSinFiltroMuestraTodosLosLibrosYLosFormulariosPost() throws Exception {
        mockMvc.perform(get("/listado"))
                .andExpect(status().isOk())
                .andExpect(view().name("listado"))
                .andExpect(model().attribute("libros", hasSize(16)))
                .andExpect(content().string(containsString("El principito")))
                .andExpect(content().string(containsString("/libro/toggle/1")))
                .andExpect(content().string(containsString("method=\"post\"")));
    }

    @Test
    void laOpcionTodosEnviaUnParametroVacio() throws Exception {
        mockMvc.perform(get("/listado").param("prestado", ""))
                .andExpect(status().isOk())
                .andExpect(model().attribute("libros", hasSize(16)));
    }

    @Test
    void elFiltroSoloMuestraLibrosPrestados() throws Exception {
        mockMvc.perform(get("/listado").param("prestado", "true"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("libros", hasSize(4)))
                .andExpect(model().attribute("libros", everyItem(hasProperty("prestado", is(true)))))
                .andExpect(model().attribute("prestado", true));
    }

    @Test
    void elFiltroSoloMuestraLibrosDisponibles() throws Exception {
        mockMvc.perform(get("/listado").param("prestado", "false"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("libros", hasSize(12)))
                .andExpect(model().attribute("libros", everyItem(hasProperty("prestado", is(false)))))
                .andExpect(model().attribute("prestado", false));
    }

    @Test
    void elDetalleBuscaPorIdentificadorYRenderizaTodosLosDatos() throws Exception {
        // El id 16 existe, aunque el último índice de la lista sea 15.
        mockMvc.perform(get("/libro/16"))
                .andExpect(status().isOk())
                .andExpect(view().name("detalle"))
                .andExpect(model().attribute("libro", hasProperty("id", is(16L))))
                .andExpect(content().string(containsString("La casa de los espíritus")))
                .andExpect(content().string(containsString("Isabel Allende")))
                .andExpect(content().string(containsString("480")))
                .andExpect(content().string(containsString("Disponible")));
    }

    @Test
    void prestarYDevolverModificaElLibroYRedirige() throws Exception {
        mockMvc.perform(post("/libro/toggle/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listado"));
        try {
            mockMvc.perform(get("/libro/1"))
                    .andExpect(model().attribute("libro", hasProperty("prestado", is(true))))
                    .andExpect(content().string(containsString("Prestado")));
            // Un GET adicional no vuelve a cambiar el estado.
            mockMvc.perform(get("/listado"))
                    .andExpect(model().attribute("libros", hasSize(16)));
        } finally {
            mockMvc.perform(post("/libro/toggle/1"))
                    .andExpect(redirectedUrl("/listado"));
        }
        mockMvc.perform(get("/libro/1"))
                .andExpect(model().attribute("libro", hasProperty("prestado", is(false))));
    }

    @Test
    void alCambiarElEstadoSeConservaElFiltroYElLibroSaleDeEseListado() throws Exception {
        mockMvc.perform(post("/libro/toggle/2").param("prestado", "false"))
                .andExpect(redirectedUrl("/listado?prestado=false"));
        try {
            mockMvc.perform(get("/listado").param("prestado", "false"))
                    .andExpect(model().attribute("libros", hasSize(11)));
        } finally {
            mockMvc.perform(post("/libro/toggle/2").param("prestado", "true"))
                    .andExpect(redirectedUrl("/listado?prestado=true"));
        }
    }

    @Test
    void unLibroInexistenteDevuelve404EnElDetalleYEnElPost() throws Exception {
        mockMvc.perform(get("/libro/999"))
                .andExpect(status().isNotFound());
        mockMvc.perform(post("/libro/toggle/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void noSePuedeCambiarElEstadoConGet() throws Exception {
        mockMvc.perform(get("/libro/toggle/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void parametrosInvalidosDevuelven400() throws Exception {
        mockMvc.perform(get("/listado").param("prestado", "incorrecto"))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/libro/no-es-un-numero"))
                .andExpect(status().isBadRequest());
    }
}
