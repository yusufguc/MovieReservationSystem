package com.yusufguc.dto.response;

import com.yusufguc.model.enums.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {

    private String title;

    private String description;

    private Integer durationMinutes;

    private Genre genre;

}
