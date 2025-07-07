package lat.luisdias.pi_v_b.controllers;

import lat.luisdias.pi_v_b.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.Config.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class Config {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }

    @Test
    void deveRetornarViewListaDeUsuarios() throws Exception {
        when(userService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/list-users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void deveRetornarViewCadastro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/new-user"))
                .andExpect(model().attributeExists("createUserDTO"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void deveRedirecionarViewListaDeUsuariosParaCadastroValido() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user")
                .param("firstName", "Jose")
                .param("lastName", "Silva")
                .param("email", "jose@email.com")
                .param("birthDate", "2000-01-01")
                .param("password", "abc12345")
                .param("confirmPassword", "abc12345")
                .param("role", "ADMIN");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    void deveRetornarViewCadastroParaCadastroInvalido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .param("firstName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("views/new-user"))
                .andExpect(model().attributeHasFieldErrors("createUserDTO", "firstName"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void deveRetornarErroParaExcecaoNoServicoDeCadastro() throws Exception {
        doThrow(new RuntimeException("Erro no service")).when(userService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .param("firstName", "Joao")
                        .param("lastName", "Silva")
                        .param("email", "joao@email.com")
                        .param("birthDate", "2000-01-01")
                        .param("password", "abc12345")
                        .param("confirmPassword", "abc12345")
                        .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("views/new-user"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void deveRetornarSucessoParaDelecaoDeUsuarioValida() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    void deveRetornarErroParaDelecaoDeUsuarioInvalida() throws Exception {
        doThrow(new RuntimeException("Erro")).when(userService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    void deveAtualizarEmailComSucesso() throws Exception {
        mockMvc.perform(put("/user/1/update-email")
                        .param("email", "novo@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "Email atualizado com sucesso!"))
                .andExpect(redirectedUrl("/user"));

        verify(userService).updateEmail(eq(1L), any());
    }

    @Test
    void deveRetornarErroValidacaoParaEmailInvalido() throws Exception {
        mockMvc.perform(put("/user/1/update-email")
                        .param("email", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/user"));

        verify(userService, never()).updateEmail(anyLong(), any());
    }

    @Test
    void deveRetornarErroParaExcecaoNoServicoAtualizarEmail() throws Exception {
        doThrow(new RuntimeException("Erro ao atualizar email")).when(userService).updateEmail(anyLong(), any());

        mockMvc.perform(put("/user/1/update-email")
                        .param("email", "valido@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", "Erro ao atualizar email"))
                .andExpect(redirectedUrl("/user"));
    }

    @Test
    void deveAtualizarPerfilComSucesso() throws Exception {
        mockMvc.perform(put("/user/1/update-role")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "Perfil de usuário atualizado com sucesso!"))
                .andExpect(redirectedUrl("/user"));

        verify(userService).updateRole(eq(1L), any());
    }

    @Test
    void deveRetornarErroValidacaoAoAtulizarPerfilInvalido() throws Exception {
        mockMvc.perform(put("/user/1/update-role")
                        .param("role", "")) // valor inválido
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/user"));

        verify(userService, never()).updateRole(anyLong(), any());
    }

    @Test
    void deveRetornarErroParaExcecaoNoServicoAtualizarPerfil() throws Exception {
        doThrow(new RuntimeException("Erro ao atualizar perfil")).when(userService).updateRole(anyLong(), any());

        mockMvc.perform(put("/user/1/update-role")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", "Erro ao atualizar perfil"))
                .andExpect(redirectedUrl("/user"));
    }

    @Test
    void deveAtualizarSenhaComSucesso() throws Exception {
        mockMvc.perform(put("/user/1/update-password")
                        .param("password", "Senha123")
                        .param("confirmPassword", "Senha123")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("success", "Senha atualizada com sucesso!"))
                .andExpect(redirectedUrl("/user"));

        verify(userService).updatePassword(eq(1L), any());
    }

    @Test
    void deveRetornarErroValidacaoParaSenhaInvalida() throws Exception {
        mockMvc.perform(put("/user/1/update-password")
                        .param("password", "")) // senha vazia
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/user"));

        verify(userService, never()).updatePassword(anyLong(), any());
    }

    @Test
    void deveRetornarErroParaExcecaoNoServicoAtualizarSenha() throws Exception {
        doThrow(new RuntimeException("Erro ao atualizar senha")).when(userService).updatePassword(anyLong(), any());

        mockMvc.perform(put("/user/1/update-password")
                        .param("password", "Senha123")
                        .param("confirmPassword", "Senha123")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("error", "Erro ao atualizar senha"))
                .andExpect(redirectedUrl("/user"));
    }
}
