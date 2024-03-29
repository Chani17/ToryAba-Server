package inu.thebite.toryaba.controller;

import inu.thebite.toryaba.config.LoginMember;
import inu.thebite.toryaba.model.sto.LooseCannonRequest;
import inu.thebite.toryaba.model.sto.*;
import inu.thebite.toryaba.service.StoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoController {

    private final StoService stoService;

    // add STO
    @PostMapping("/{ltoId}/stos")
    public StoResponse addSto(@PathVariable Long ltoId, @RequestBody AddStoRequest addStoRequest) {
        StoResponse sto = stoService.addSto(ltoId, addStoRequest);
        return sto;
    }

    // update STO status when STO status is stop or in progress
    @PatchMapping("/stos/{stoId}/status")
    public StoResponse updateStoStatus(@PathVariable Long stoId,
                               @RequestBody UpdateStoStatusRequest updateStoStatusRequest) {
        StoResponse sto = stoService.updateStoStatus(stoId, updateStoStatusRequest.getStatus());
        return sto;
    }

    // update STO status when STO status is git
    @PatchMapping("/stos/{stoId}/hit/status")
    public StoResponse updateStoHitStatus(@PathVariable Long stoId,
                                  @RequestBody UpdateStoStatusRequest updateStoStatusRequest) {
        StoResponse sto = stoService.updateStoHitStatus(stoId, updateStoStatusRequest.getStatus());
        return sto;
    }

    // update STO contents
    @PatchMapping("/stos/{stoId}")
    public StoResponse updateSto(@PathVariable Long stoId, @RequestBody UpdateStoRequest updateStoRequest) {
        StoResponse sto = stoService.updateSto(stoId, updateStoRequest);
        return sto;
    }

    // update image list(image url)
    @PatchMapping("/stos/{stoId}/images")
    public StoResponse updateImageList(@PathVariable Long stoId, @RequestBody UpdateImageList updateImageList) {
        StoResponse imageList = stoService.updateImageList(stoId, updateImageList);
        return imageList;
    }

    // update STO round
    @PatchMapping("/stos/{stoId}/round")
    public StoResponse updateStoRound(@PathVariable Long stoId, @RequestBody UpdateStoRoundRequest updateStoRoundRequest) {
        StoResponse sto = stoService.updateStoRound(stoId, updateStoRoundRequest);
        return sto;
    }

    @PatchMapping("/stos/{stoId}/hit/round")
    public StoResponse updateStoHitRound(@PathVariable Long stoId, @RequestBody UpdateStoRoundRequest updateStoRoundRequest) {
        StoResponse sto = stoService.updateStoHitRound(stoId, updateStoRoundRequest);
        return sto;
    }

    // update stress status in STO
    @PatchMapping("/stos/{stoId}/stress")
    public StoResponse updateStressStatus(@PathVariable Long stoId, @RequestBody LooseCannonRequest looseCannonRequest) {
        return stoService.updateStressStatus(stoId, looseCannonRequest);
    }

    // update concentration in STO
    @PatchMapping("/stos/{stoId}/concentration")
    public StoResponse updateConcentration(@PathVariable Long stoId, @RequestBody LooseCannonRequest looseCannonRequest) {
        return stoService.updateConcentration(stoId, looseCannonRequest);
    }

    // update significant content
    @PatchMapping("/stos/{stoId}/significant")
    public StoResponse updateSignificant(@PathVariable Long stoId, @RequestBody LooseCannonRequest looseCannonRequest) {
        return stoService.updateSignificant(stoId, looseCannonRequest);
    }

    // select loose cannon status
    @PatchMapping("/stos/{stoId}/selection/lc")
    public StoResponse updateLooseCannons(@PathVariable Long stoId, @RequestBody LooseCannonRequest looseCannonRequest) {
        return stoService.selectLooseCannon(stoId, looseCannonRequest);
    }

    // remove loose cannon status
    @PatchMapping("/stos/{stoId}/removal/lc")
    public StoResponse removeLooseCannon(@PathVariable Long stoId, @RequestBody LooseCannonRequest looseCannonRequest) {
        return stoService.removeLooseCannon(stoId, looseCannonRequest);
    }

    // get loose cannon status list
//    @GetMapping("/stos/{stoId}/lc")
//    public List<String> getLooseCannonListBySto(@PathVariable Long stoId) {
//        List<String> response = stoService.getLooseCannonListBySto(stoId);
//        return response;
//    }

    // get STO list by studentId
    @GetMapping("/{studentId}/stos")
    public List<StoResponse> getStoList(@PathVariable Long studentId) {
        List<StoResponse> stoList = stoService.getStoList(studentId);
        return stoList;
    }

    @GetMapping("/ltos/{ltoId}/stos")
    public List<StoResponse> getStoListByLto(@PathVariable Long ltoId) {
        List<StoResponse> stoList = stoService.getStoListByLtoId(ltoId);
        return stoList;
    }

    // delete STO
    @DeleteMapping("/stos/{stoId}")
    public boolean deleteSto(@PathVariable Long stoId) {
        boolean result = stoService.deleteSto(stoId);
        return result;
    }
}