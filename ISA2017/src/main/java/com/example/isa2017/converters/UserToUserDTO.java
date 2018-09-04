package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.User;
import com.example.isa2017.modelDTO.UserDTO;

@Component
public class UserToUserDTO implements Converter<User, UserDTO>{

	@Override
	public UserDTO convert(User arg0) {
		// TODO Auto-generated method stub
		if(arg0 == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDTO userDTO=modelMapper.map(arg0,UserDTO.class);
		return userDTO;
	}

public List<UserDTO> convert(List<User> source){
		
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (User user : source) {
			usersDTO.add(convert(user));
		}
		
		return usersDTO;
	}
	
	
}
