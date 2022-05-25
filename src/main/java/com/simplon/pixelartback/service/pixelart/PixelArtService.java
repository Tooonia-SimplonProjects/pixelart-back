package com.simplon.pixelartback.service.pixelart;

import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.storage.dto.PixelArtSimpleDto;

import java.util.List;

public interface PixelArtService {

    // TODO: all those methods "throws" exception (ProjectService does, UserService does not!)?
    List<PixelArtDto> getAllPixelArt();

    List<PixelArtSimpleDto> getAllSimplePixelArt();

//    PixelArtDto getPixelArtByUuid(UUID uuid);
    PixelArtDto getPixelArtById(Long id);

    PixelArtSimpleDto getSimplePixelArtById(Long id);

    List<PixelArtDto> getAllPixelArtByUser(Long id);

    PixelArtDto createPixelArt(PixelArtDto pixelArtDto);
    PixelArtSimpleDto updatePixelArt(PixelArtSimpleDto pixelArtDto);
//    void deletePixelArt(UUID uuid);
    void deletePixelArt(Long id);

}
