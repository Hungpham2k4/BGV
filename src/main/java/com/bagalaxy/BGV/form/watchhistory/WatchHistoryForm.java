package com.bagalaxy.BGV.form.watchhistory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WatchHistoryForm {

    @NotNull
    private Long movieId;

    @Min(0)
    private int lastPosition; // đơn vị: giây
}

