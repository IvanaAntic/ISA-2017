package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Play;
import com.example.isa2017.modelDTO.PlayDTO;

@Component
public class PlayToPlayDTO implements Converter<Play, PlayDTO>{

	@Override
	public PlayDTO convert(Play source) {
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		PlayDTO playDTO = modelMapper.map(source, PlayDTO.class);
		return playDTO;
	}
	
	
	public List<PlayDTO> convert(List<Play> source){
		
		List<PlayDTO> playsDTO = new ArrayList<PlayDTO>();
		for (Play play : source) {
			playsDTO.add(convert(play));
		}
		
		return playsDTO;
	}

}
