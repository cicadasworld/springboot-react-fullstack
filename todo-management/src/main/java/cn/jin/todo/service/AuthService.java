package cn.jin.todo.service;

import cn.jin.todo.dto.LoginDto;
import cn.jin.todo.dto.RegisterDto;

public interface AuthService {

	String register(RegisterDto registerDto);

	String login(LoginDto loginDto);

}
