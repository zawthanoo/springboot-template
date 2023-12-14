package org.mutu.example.api;

import java.util.List;

import javax.validation.Valid;

import org.mutu.example.dto.UserDto;
import org.mutu.example.zconfig.oath2.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List listUser() {
		return userService.findAll();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void create(@Valid @RequestBody UserDto userDto) {
		userService.create(userDto);
	}
}
