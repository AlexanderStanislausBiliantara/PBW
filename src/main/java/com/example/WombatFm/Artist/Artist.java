package com.example.WombatFm.Artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    private int artistId;

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    private String photoUrl;
}
