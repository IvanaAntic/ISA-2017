package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Projection;
import com.example.isa2017.modelDTO.ProjectionDTO;
import com.example.isa2017.service.HallService;
import com.example.isa2017.service.MovieService;

@Component
public class ProjectionToProjectionDTO implements Converter<Projection, ProjectionDTO> {
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private DateConverter dateConverter;

	@Override
	public ProjectionDTO convert(Projection source) {
		if(source == null) {
			System.out.println("\n\n\nSOURCE JE NULL\n\n\n");
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		ProjectionDTO projectionDTO = modelMapper.map(source, ProjectionDTO.class);
		
		projectionDTO.setMovieName(movieService.findOne(projectionDTO.getMovieId()).getMovieName());
		projectionDTO.setHallName(hallService.findOne(projectionDTO.getHallId()).getHallName());
		projectionDTO.setDate(dateConverter.dateToString(source.getDate()));
		projectionDTO.setTime(dateConverter.timeToString(source.getDate()));
		
		return projectionDTO;
	}
	public List<ProjectionDTO> convert(List<Projection> source){
		
		List<ProjectionDTO> projectionsDTO = new ArrayList<ProjectionDTO>();
		for (Projection proj : source) {
			projectionsDTO.add(convert(proj));
		}
		
		return projectionsDTO;
	}
}
