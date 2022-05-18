package com.simplon.pixelartback.storage.entity.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplon.pixelartback.storage.entity.language.LanguageEntity;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "alias", length = 30, nullable = false, unique = true)
    private String alias;

    @Column(name = "user_email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "user_password", length = 50, nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEntity role;

//    @Column(name = "language", nullable = false)
//    private LanguageEntity language;

//    FONTOS: valoszinuleg a "FetchType.EAGER" segitett!!!
//    https://www.baeldung.com/jpa-joincolumn-vs-mappedby :
//    TODO: see this again: "Defining here @OneToMany, we are making it bidirectional"
    @OneToMany(mappedBy="userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = PixelArtEntity.class)
//    @JoinColumn(name = "id") <<< when having "mappedBy" defined here, we can't add that @ : that would put this "id" as an extra column to PixelArt

//    This annotation is important to break the cycles that would print in the created pixelart
//    in the response recursively under "pixelArtEntityList" with a GET all for PixelArt:
    @JsonIgnoreProperties("userEntity")
    private List<PixelArtEntity> pixelArtEntityList;

    public UserEntity() {
        super();
    }

}
