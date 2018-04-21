package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Seat;
import com.example.isa2017.modelDTO.HallDTO;
import com.example.isa2017.modelDTO.SeatDTO;

@Component
public class SeatToSeatDTO implements Converter<Seat, SeatDTO> {

	@Override
	public SeatDTO convert(Seat source) {
		if(source == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		SeatDTO seatDTO = modelMapper.map(source, SeatDTO.class);
		return seatDTO;
	}
	public List<SeatDTO> convert(List<Seat> source){
		
		List<SeatDTO> seatsDTO = new ArrayList<SeatDTO>();
		for (Seat seat : source) {
			seatsDTO.add(convert(seat));
		}
		
		return seatsDTO;
	}

	
}
