package com.project.popupmarket.dto.recommendation;

import com.project.popupmarket.dto.land.RentalLandRespTO;
import com.project.popupmarket.dto.popup.PopupStoreTO;
import com.project.popupmarket.dto.land.RentalLandTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecommendItemTO {
    private List<PopupStoreTO> popupStore;
    private List<RentalLandRespTO> rentalPlace;
}
