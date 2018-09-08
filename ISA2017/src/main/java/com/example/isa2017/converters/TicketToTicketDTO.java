package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Ticket;
import com.example.isa2017.modelDTO.TicketDTO;
import com.example.isa2017.service.ProjectionService;

@Component
public class TicketToTicketDTO implements Converter<Ticket, TicketDTO>{
	
	@Autowired
	private ProjectionService projService;
	
	@Autowired
	private DateConverter dateConverter;

	@Override
	public TicketDTO convert(Ticket source) {

		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		TicketDTO ticketDTO = modelMapper.map(source, TicketDTO.class);
		
		/*ticketDTO.setPrice(source.getProjection().getPrice());
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		ticketDTO.setTime(df.format(source.getProjection().getDate()));
		df = new SimpleDateFormat("DD/mm/yyyy");
		ticketDTO.setDate(df.format(source.getProjection().getDate()));*/
		
		
		//ticketDTO.setHall(source.getProjection().getHall().getName());
		
		ticketDTO.setDate(dateConverter.dateToString(source.getProjection().getDate()));
		ticketDTO.setTime(dateConverter.timeToString(source.getProjection().getDate()));
		
		
		return ticketDTO;
	}
	
	public List<TicketDTO> convert(List<Ticket> source){

		List<TicketDTO> ticketsDTO = new ArrayList<TicketDTO>();
		for (Ticket ticket : source) {
			ticketsDTO.add(convert(ticket));
		}
		
		return ticketsDTO;
	}

}
