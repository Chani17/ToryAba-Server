package inu.thebite.toryaba.service.serviceImpl;

import inu.thebite.toryaba.entity.Lto;
import inu.thebite.toryaba.entity.Point;
import inu.thebite.toryaba.entity.Sto;
import inu.thebite.toryaba.model.sto.LooseCannonRequest;
import inu.thebite.toryaba.model.lto.LtoResponse;
import inu.thebite.toryaba.model.sto.*;
import inu.thebite.toryaba.repository.*;
import inu.thebite.toryaba.service.PointService;
import inu.thebite.toryaba.service.StoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StoServiceImpl implements StoService {

    private final StoRepository stoRepository;
    private final LtoRepository ltoRepository;
    private final PointService pointService;
    private final PointRepository pointRepository;
    private final TodoRepository todoRepository;
    private final DetailRepository detailRepository;


    @Transactional
    @Override
    public StoResponse addSto(Long ltoId, AddStoRequest addStoRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 LTO가 존재하지 않습니다."));

        List<Sto> stoList = stoRepository.findByLtoIdWithStoResponse(lto.getId());

        Sto sto = Sto.createSto(stoList.size() + 1, addStoRequest.getName(), addStoRequest.getContents(),
                addStoRequest.getCount(), addStoRequest.getGoal(), addStoRequest.getGoalType(), addStoRequest.getGoalAmount(),
                addStoRequest.getUrgeContent(), addStoRequest.getEnforceContent(), addStoRequest.getMemo(), lto);


        // when STO is made, point is made together.
        Point point = Point.createPoint(addStoRequest.getRegistrant(), sto);
        pointRepository.save(point);
        stoRepository.save(sto);

        StoResponse stoResponse = StoResponse.stoResponse(sto.getId(), stoList.size() + 1, sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(), sto.getGoal(),
                sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(), sto.getRound(), sto.getHitGoalDate(),
                sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(), sto.getStressStatus(),
                sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());

        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateStoStatus(Long stoId, String status) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateStoStatus(status);

        StoResponse stoResponse = StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(), sto.getGoal(),
                sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(), sto.getRound(), sto.getHitGoalDate(),
                sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(), sto.getStressStatus(),
                sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());

        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateStoHitStatus(Long stoId, String status) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateStoHitStatus(status);
        return getStoResponse(sto);
    }

    private StoResponse getStoResponse(Sto sto) {
        StoResponse stoResponse = StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(), sto.getGoal(),
                sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(), sto.getRound(), sto.getHitGoalDate(),
                sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(), sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(),
                sto.getLooseCannonList(), sto.getLto().getId());

        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateSto(Long stoId, UpdateStoRequest updateStoRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateSto(updateStoRequest.getName(), updateStoRequest.getContents(), updateStoRequest.getCount(),
                updateStoRequest.getGoal(), updateStoRequest.getGoalType(), updateStoRequest.getGoalAmount(), updateStoRequest.getUrgeContent(),
                updateStoRequest.getEnforceContent(), updateStoRequest.getMemo());

        return getStoResponse(sto);
    }

    @Transactional
    @Override
    public StoResponse updateImageList(Long stoId, UpdateImageList updateImageList) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateImageList(updateImageList.getImageList().stream().toList());

        StoResponse stoResponse = StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateStoRound(Long stoId, UpdateStoRoundRequest updateStoRoundRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        StoResponse stoResponse = updateStoStatus(stoId, updateStoRoundRequest.getStatus());
        pointService.insertValue(stoId, updateStoRoundRequest.getPlusRate(), updateStoRoundRequest.getMinusRate());
        sto.updateStoRound(stoResponse.getRound());

        stoResponse.setRound(sto.getRound());

        // when STO's round update, point is made together.
        addNewPointList(sto, updateStoRoundRequest);
        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateStoHitRound(Long stoId, UpdateStoRoundRequest updateStoRoundRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        StoResponse stoResponse = updateStoHitStatus(stoId, updateStoRoundRequest.getStatus());
        pointService.insertValue(stoId, updateStoRoundRequest.getPlusRate(), updateStoRoundRequest.getMinusRate());
        sto.updateStoRound(stoResponse.getRound());

        stoResponse.setRound(sto.getRound());

        // when STO's round update, point is made together.
        addNewPointList(sto, updateStoRoundRequest);
        return stoResponse;
    }

    @Transactional
    @Override
    public StoResponse updateStressStatus(Long stoId, LooseCannonRequest looseCannonRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateStressStatus(looseCannonRequest.getContent());

        return StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
    }

    @Transactional
    @Override
    public StoResponse updateConcentration(Long stoId, LooseCannonRequest looseCannonRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateConcentration(looseCannonRequest.getContent());

        return StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
    }

    @Transactional
    @Override
    public StoResponse updateSignificant(Long stoId, LooseCannonRequest looseCannonRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.updateSignificant(looseCannonRequest.getContent());

        return StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
    }

    @Transactional
    @Override
    public StoResponse selectLooseCannon(Long stoId, LooseCannonRequest looseCannonRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        sto.selectLooseCannon(looseCannonRequest.getContent());

        return StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
    }

    @Transactional
    @Override
    public StoResponse removeLooseCannon(Long stoId, LooseCannonRequest looseCannonRequest) {
        Sto sto = stoRepository.findById(stoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));

        String target = "";
        for (String action : sto.getLooseCannonList()) {
            if (action.equals(looseCannonRequest.getContent())) {
                target = action;
            }
        }
        sto.updateLooseCannon(target);

        return StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());
    }

//    @Override
//    public List<String> getLooseCannonListBySto(Long stoId) {
//        Sto sto = stoRepository.findById(stoId)
//                .orElseThrow(() -> new IllegalStateException("해당하는 STO가 존재하지 않습니다."));
//
//        List<String> response = stoRepository.findLooseCannonById(sto.getId());
//        return response;
//    }

    @Override
    public List<StoResponse> getStoList(Long studentId) {

        List<StoResponse> stoList = new ArrayList<>();
        List<Lto> ltoList = ltoRepository.findAllByStudentId(studentId);

        for (Lto lto: ltoList) {
            List<Sto> stoResponse = stoRepository.findByLtoIdWithStoResponse(lto.getId());

            for(Sto s : stoResponse) {
                Sto sto = stoRepository.findById(s.getId()).orElseThrow(() -> new IllegalStateException("해당 STO를 찾을 수 없습니다."));
                StoResponse result = StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                        sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                        sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                        sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());

                result.setImageList(sto.getImageList());
                result.setPointList(sto.getPointList());
                stoList.add(result);
            }
        }
        return stoList;
    }


    @Override
    public List<StoResponse> getStoListByLtoId(Long ltoId) {

        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당하는 LTO가 존재하지 않습니다."));

        List<Sto> stoList = stoRepository.findByLtoIdWithStoResponse(lto.getId());
        List<StoResponse> result = new ArrayList<>();
        for(Sto stoResponse : stoList) {
            Sto sto = stoRepository.findById(stoResponse.getId()).orElseThrow(() -> new IllegalStateException("해당 STO를 찾을 수 없습니다."));

            StoResponse response = StoResponse.stoResponse(sto.getId(), sto.getTemplateNum(), sto.getStatus(), sto.getName(), sto.getContents(), sto.getCount(),
                    sto.getGoal(), sto.getGoalType(), sto.getGoalAmount(), sto.getUrgeContent(), sto.getEnforceContent(), sto.getMemo(),
                    sto.getRound(), sto.getHitGoalDate(), sto.getRegisterDate(), sto.getDelYN(), sto.getImageList(), sto.getPointList(),
                    sto.getStressStatus(), sto.getConcentration(), sto.getSignificant(), sto.getLooseCannonList(), sto.getLto().getId());

            response.setImageList(sto.getImageList());
            response.setPointList(sto.getPointList());
            result.add(response);
        }
        return result;
    }

    @Transactional
    @Override
    public boolean deleteSto(Long stoId) {
        if(stoRepository.findById(stoId).isPresent()) {
            stoRepository.deleteById(stoId);
            todoRepository.deleteByStoId(stoId);
            detailRepository.deleteByStoId(stoId);
        } else {
            throw new IllegalStateException("해당하는 STO가 존재하지 않습니다.");
        }
        return true;
    }

    public void addNewPointList(Sto sto, UpdateStoRoundRequest updateStoRoundRequest) {
        Point point = Point.createPoint(updateStoRoundRequest.getRegistrant(), sto);
        point.updateRound(sto.getRound(), point.getPoints());
        pointRepository.save(point);
    }

}
