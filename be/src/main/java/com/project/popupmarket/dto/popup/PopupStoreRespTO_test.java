package com.project.popupmarket.dto.popup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopupStoreRespTO_test {
    private PopupStoreTO popupStoreTO;
    private String thumbnail;
    private List<String> images;
}
