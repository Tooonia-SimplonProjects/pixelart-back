package com.simplon.pixelartback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PixelartBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixelartBackApplication.class, args);
	}
//	@Bean
//	public PixelArtMapper pixelArtMapper() {
//		return new PixelArtMapper() {
//			@Override
//			public PixelArtEntity dtoToEntity(PixelArtDto dto) {
//				return null;
//			}
//
//			@Override
//			public void dtoToEntity(PixelArtDto dto, PixelArtEntity entity) {
//
//			}
//
//			@Override
//			public List<PixelArtEntity> dtosToEntities(List<PixelArtDto> dtos) {
//				return null;
//			}
//
//			@Override
//			public PixelArtDto entityToDto(PixelArtEntity entity) {
//				return null;
//			}
//
//			@Override
//			public List<PixelArtDto> entitiesToDtos(Collection<PixelArtEntity> entities) {
//				return null;
//			}
//		};
//	}


}
