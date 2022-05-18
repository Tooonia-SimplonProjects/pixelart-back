package com.simplon.pixelartback.storage.entity.pixelart;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "pixel_art")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonSerialize(using = SerializableSerializer.class)
//public class PixelArtEntity extends JsonSerializable.Base {
public class PixelArtEntity implements Serializable {

//    private static final long serialVersionUID = 6858481209380769717L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @Column(name = "id_pixel_art")
    private Long id;
//    private Long idPixelArt;

//    @Column(name = "uuid", nullable = false, unique = true)
//    private UUID uuid;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

//    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image // private Base64 productImage;

//    Cascading expresses dependency between 2 entities: if User does not exist, PixelArt has no meaning on its own.
//    PixelArt depends on the existence of a User.
//    @ManyToOne(cascade= CascadeType.ALL)

//    https://www.baeldung.com/jpa-joincolumn-vs-mappedby :
//    In a One-to-Many/Many-to-One relationship, the owning side is usually defined on the ‘many' side of the relationship.
//    It's usually the side which owns the foreign key
//    The @JoinColumn annotation defines that actual physical mapping on the owning side:
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UserEntity.class)
    @JoinColumn(name = "id_user_fk", referencedColumnName = "id", nullable = false) // It is a fk!
//    This annotation is important to break the cycles that would print the created pixelart recursively
//    in the response of a GET all for PixelArt under "pixelArtEntityList [] " :
    @JsonIgnoreProperties("pixelArtEntityList")
    private UserEntity userEntity;

//    @Column(name = "product_price", nullable = false)
//    private Integer productPrice;

//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    @JsonCreator
//    public PixelArtEntity(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("id_user_fk") UserEntity userEntity) {
//        this.id = id;
//        this.name = name;
//        this.userEntity = userEntity;
//    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PixelArtEntity)) {
            return false;
        }
        return super.equals(obj);
    }

//    @Override
//    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//        gen.writeFieldName("name");
//        gen.writeString(this.name);
//        gen.writeFieldName("userEntity");
//        gen.writeNumber(this.userEntity.getId());
//        gen.writeEndObject();
//    }
//
//    @Override
//    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
//        throw new UnsupportedOperationException("Not supported.");
//    }
}
