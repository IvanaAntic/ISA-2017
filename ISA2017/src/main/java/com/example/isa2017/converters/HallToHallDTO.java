package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Cinema;
import com.example.isa2017.model.Hall;
import com.example.isa2017.modelDTO.CinemaDTO;
import com.example.isa2017.modelDTO.HallDTO;

@Component
public class HallToHallDTO implements Converter<Hall, HallDTO>{

	@Override
	public HallDTO convert(Hall source) {
		if(source == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		HallDTO hallDTO = modelMapper.map(source, HallDTO.class);
		return hallDTO;

	}
	public List<HallDTO> convert(List<Hall> source){
		
		List<HallDTO> hallsDTO = new ArrayList<HallDTO>();
		for (Hall hall : source) {
			hallsDTO.add(convert(hall));
		}
		
		return hallsDTO;
	}

}
