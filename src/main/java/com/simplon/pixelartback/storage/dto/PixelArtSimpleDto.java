package com.simplon.pixelartback.storage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PixelArtSimpleDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("width")
    private String width;

    @JsonProperty("height")
    private String height;

    @JsonProperty("canvas")
//    TODO: see format: Blob, BlobType, Image // private Base64 productImage;
    private Long[] canvas;
}
