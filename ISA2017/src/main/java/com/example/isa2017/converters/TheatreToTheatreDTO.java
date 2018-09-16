package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Theatre;
import com.example.isa2017.modelDTO.TheatreDTO;

@Component
public class TheatreToTheatreDTO implements Converter<Theatre, TheatreDTO>{

	@Override
	public TheatreDTO convert(Theatre source) {

		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		TheatreDTO theatreDTO = modelMapper.map(source, TheatreDTO.class);
		return theatreDTO;
	}
	
	public List<TheatreDTO> convert(List<Theatre> source){
		
		List<TheatreDTO> theatresDTO = new ArrayList<TheatreDTO>();
		for (Theatre theatre : source) {
			theatresDTO.add(convert(theatre));
		}
		
		return theatresDTO;
	}

}
