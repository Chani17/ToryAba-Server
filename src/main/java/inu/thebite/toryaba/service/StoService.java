package inu.thebite.toryaba.service;

import inu.thebite.toryaba.model.sto.LooseCannonRequest;
import inu.thebite.toryaba.model.sto.*;

import java.util.List;

public interface StoService {
    StoResponse addSto(Long ltoId, AddStoRequest addStoRequest);

    StoResponse updateStoStatus(Long stoId, String status);

    StoResponse updateStoHitStatus(Long stoId, String status);

    List<StoResponse> getStoList(Long studentId);

    List<StoResponse> getStoListByLtoId(Long ltoId);

    boolean deleteSto(Long stoId);

    StoResponse updateImageList(Long stoId, UpdateImageList updateImageList);

    StoResponse updateSto(Long stoId, UpdateStoRequest updateStoRequest);

    StoResponse updateStoRound(Long stoId, UpdateStoRoundRequest updateStoRoundRequest);

    StoResponse updateStoHitRound(Long stoId, UpdateStoRoundRequest updateStoRoundRequest);

    StoResponse updateStressStatus(Long stoId, LooseCannonRequest looseCannonRequest);

    StoResponse updateConcentration(Long stoId, LooseCannonRequest looseCannonRequest);

    StoResponse selectLooseCannon(Long stoId, LooseCannonRequest looseCannonRequest);

    StoResponse updateSignificant(Long stoId, LooseCannonRequest looseCannonRequest);

    StoResponse removeLooseCannon(Long stoId, LooseCannonRequest looseCannonRequest);

//    List<String> getLooseCannonListBySto(Long stoId);
}
