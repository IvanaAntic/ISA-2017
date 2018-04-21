package com.example.isa2017.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.isa2017.model.Hall;
import com.example.isa2017.model.Projection;
import com.example.isa2017.modelDTO.HallDTO;
import com.example.isa2017.modelDTO.ProjectionDTO;

@Component
public class ProjectionToProjectionDTO implements Converter<Projection, ProjectionDTO> {

	@Override
	public ProjectionDTO convert(Projection source) {
		if(source == null) {
			return null;
		}
		ModelMapper modelMapper = new ModelMapper();
		ProjectionDTO projectionDTO = modelMapper.map(source, ProjectionDTO.class);
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
