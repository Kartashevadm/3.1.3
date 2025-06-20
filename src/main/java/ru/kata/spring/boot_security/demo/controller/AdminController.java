package ru.kata.spring.boot_security.demo.controller;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;;

	@Lazy
	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping
	public String showAdminPanel(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("availableRoles", roleService.getAllRoles());
		return "admin";
	}

//	@GetMapping()
//	public String getAllUsers(Model model) {
//		model.addAttribute("users", userService.getAllUsers());
//		return "admin";
//	}

	@GetMapping("/new")
	public String getNewUserForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("availableRoles", roleService.getAllRoles());
		return "new11";
	}

	@PostMapping("/new")
	public String createUser(@ModelAttribute("user") User user) {
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new RuntimeException("Username cannot be null!");
		}
		userService.createUser(user);
		return "redirect:/admin";
	}

	@GetMapping("/edit")
	public String getEditUserForm(@RequestParam("id") Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("availableRoles", roleService.getAllRoles());
		return "edit11";
	}

	@PostMapping("/update")
	public String updateUser(@ModelAttribute("user") User user) {
		userService.updateUser(user.getId(), user);
		return "redirect:/admin";

	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}
}