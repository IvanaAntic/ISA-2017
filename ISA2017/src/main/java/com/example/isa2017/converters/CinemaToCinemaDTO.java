package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Cinema;
import com.example.isa2017.modelDTO.CinemaDTO;

@Component
public class CinemaToCinemaDTO implements Converter<Cinema, CinemaDTO>{

	@Override
	public CinemaDTO convert(Cinema source) {

		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		CinemaDTO cinemaDTO = modelMapper.map(source, CinemaDTO.class);
		return cinemaDTO;
	}
	
	public List<CinemaDTO> convert(List<Cinema> source){
		
		List<CinemaDTO> cinemasDTO = new ArrayList<CinemaDTO>();
		for (Cinema cinema : source) {
			cinemasDTO.add(convert(cinema));
		}
		
		return cinemasDTO;
	}

}
