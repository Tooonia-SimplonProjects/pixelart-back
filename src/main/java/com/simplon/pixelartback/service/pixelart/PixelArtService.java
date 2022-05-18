package com.simplon.pixelartback.service.pixelart;

import com.simplon.pixelartback.storage.dto.PixelArtDto;

import java.util.List;

public interface PixelArtService {

    // TODO: all those methods "throws" exception (ProjectService does, UserService does not!)?
    List<PixelArtDto> getAllPixelArt();
//    PixelArtDto getPixelArtByUuid(UUID uuid);
    PixelArtDto getPixelArtById(Long id);

//    List<PixelArtDto> getPixelArtByUser(UserDto userDto); //TODO: correct? or: (Long id); of "id"?
    PixelArtDto createPixelArt(PixelArtDto pixelArtDto);
    PixelArtDto updatePixelArt(PixelArtDto pixelArtDto);
//    void deletePixelArt(UUID uuid);
    void deletePixelArt(Long id);

}
