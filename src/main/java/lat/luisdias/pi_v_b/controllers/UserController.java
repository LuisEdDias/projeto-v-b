package lat.luisdias.pi_v_b.controllers;

import jakarta.validation.Valid;
import lat.luisdias.pi_v_b.dtos.CreateUserDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserEmailDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserPasswordDTO;
import lat.luisdias.pi_v_b.dtos.UpdateUserRoleDTO;
import lat.luisdias.pi_v_b.entities.UserRole;
import lat.luisdias.pi_v_b.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user")
public class UserController {
    // Declaração da dependência do serviço de usuários
    private final UserService userService;

    // Injeção de dependência via construtor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Retorna a view com a lista de usuários
    @GetMapping
    public String getAllView(Model model) {
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roles", UserRole.values());
        return "views/list-users";
    }

    // Retorna a view de cadastro de usuários
    @GetMapping("/new")
    public String newUserView(Model model) {
        model.addAttribute("createUserDTO", new CreateUserDTO());
        model.addAttribute("roles", UserRole.values());
        return "views/new-user";
    }

    // Recebe os dados para registro de usuários enviados do frontend
    @PostMapping
    public String create(
            @Valid @ModelAttribute CreateUserDTO userDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // Verifica se há erros de validação de dados
        if (result.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "views/new-user";
        }

        // Chama o serviço de usuário e lida com cenario de erro ou sucesso
        try {
            userService.create(userDTO);
            redirectAttributes.addFlashAttribute("success", "Usuário cadastrado com sucesso!");
            return "redirect:/user";
        } catch (RuntimeException ex) {
            model.addAttribute("roles", UserRole.values());
            model.addAttribute("error", ex.getMessage());
            return "views/new-user";
        }
    }

    // Lida com a atualização de email
    @PutMapping("/{id}/update-email")
    public String updateEmail(
            @PathVariable("id") Long id,
            @Valid UpdateUserEmailDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Verifica se há erros de validação de dados
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/user";
        }

        // Chama o serviço de usuário e lida com cenario de erro ou sucesso
        try {
            userService.updateEmail(id, userDTO);
            redirectAttributes.addFlashAttribute("success", "Email atualizado com sucesso!");
            return "redirect:/user";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/user";
        }
    }

    // Lida com a atualização de perfil
    @PutMapping("/{id}/update-role")
    public String updateRole(
            @PathVariable("id") Long id,
            @Valid UpdateUserRoleDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Verifica se há erros de validação de dados
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/user";
        }

        // Chama o serviço de usuário e lida com cenario de erro ou sucesso
        try {
            userService.updateRole(id, userDTO);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Perfil de usuário atualizado com sucesso!"
            );
            return "redirect:/user";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/user";
        }
    }

    // Lida com a atualização de senha
    @PutMapping("/{id}/update-password")
    public String updateRole(
            @PathVariable("id") Long id,
            @Valid UpdateUserPasswordDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Verifica se há erros de validação de dados
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/user";
        }

        // Chama o serviço de usuário e lida com cenario de erro ou sucesso
        try {
            userService.updatePassword(id, userDTO);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Senha atualizada com sucesso!"
            );
            return "redirect:/user";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/user";
        }
    }

    // Remoção de usuário
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Usuário removido com sucesso!"
            );
            return "redirect:/user";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/user";
        }
    }
}
