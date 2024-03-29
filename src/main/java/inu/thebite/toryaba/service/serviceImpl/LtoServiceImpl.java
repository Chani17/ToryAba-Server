package inu.thebite.toryaba.service.serviceImpl;

import inu.thebite.toryaba.entity.Domain;
import inu.thebite.toryaba.entity.Lto;
import inu.thebite.toryaba.entity.Student;
import inu.thebite.toryaba.model.lto.*;
import inu.thebite.toryaba.model.sto.StoResponse;
import inu.thebite.toryaba.repository.DomainRepository;
import inu.thebite.toryaba.repository.LtoRepository;
import inu.thebite.toryaba.repository.StudentRepository;
import inu.thebite.toryaba.service.LtoService;
import inu.thebite.toryaba.service.PointService;
import inu.thebite.toryaba.service.StoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LtoServiceImpl implements LtoService {

    private final LtoRepository ltoRepository;
    private final DomainRepository domainRepository;
    private final StudentRepository studentRepository;
    private final StoService stoService;
    private final PointService pointService;

    @Override
    @Transactional
    public LtoResponse addLto(Long domainId, Long studentId, LtoRequest ltoRequest) {

        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new IllegalStateException("해당 영역이 존재하지 않습니다."));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("해당 학생은 존재하지 않습니다."));

        List<Lto> ltoList = ltoRepository.findAllByStudentIdAndDomainId(student.getId(), domain.getId());
        Lto lto = Lto.createLto(ltoList.size() + 1, ltoRequest.getName(), ltoRequest.getContents(), ltoRequest.getDevelopType(), domain, student);
        Lto saveLto = ltoRepository.save(lto);

        LtoResponse response = LtoResponse.createLtoResponse(saveLto.getId(), saveLto.getTemplateNum(), saveLto.getStatus(), saveLto.getName(),
                saveLto.getContents(), saveLto.getDevelopType(), saveLto.getAchieveDate(), saveLto.getRegisterDate(),
                saveLto.getDelYN(), saveLto.getDomain().getId(), saveLto.getStudent().getId());

        return response;
    }

    @Transactional
    @Override
    public LtoResponse updateLtoStatus(Long ltoId, UpdateLtoStatusRequest updateLtoStatusRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));

        lto.updateLtoStatus(updateLtoStatusRequest.getStatus());

        LtoResponse response = LtoResponse.createLtoResponse(lto.getId(), lto.getTemplateNum(), lto.getStatus(), lto.getName(),
                lto.getContents(), lto.getDevelopType(), lto.getAchieveDate(), lto.getRegisterDate(),
                lto.getDelYN(), lto.getDomain().getId(), lto.getStudent().getId());
        return response;
    }

    @Transactional
    @Override
    public LtoResponse updateLtoHitStatus(Long ltoId, UpdateLtoStatusRequest updateLtoStatusRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));
        lto.updateLtoHitStatus(updateLtoStatusRequest.getStatus());
        LtoResponse response = LtoResponse.createLtoResponse(lto.getId(), lto.getTemplateNum(), lto.getStatus(), lto.getName(),
                lto.getContents(), lto.getDevelopType(), lto.getAchieveDate(), lto.getRegisterDate(),
                lto.getDelYN(), lto.getDomain().getId(), lto.getStudent().getId());
        return response;
    }

    @Transactional
    @Override
    public LtoResponse updateLto(Long ltoId, LtoRequest ltoRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));
        lto.updateLTO(ltoRequest.getName(), ltoRequest.getContents());

        LtoResponse ltoResponse = LtoResponse.createLtoResponse(lto.getId(), lto.getTemplateNum(), lto.getStatus(), lto.getName(), lto.getContents(), lto.getDevelopType(), lto.getAchieveDate(), lto.getRegisterDate(), lto.getDelYN(), lto.getDomain().getId(), lto.getStudent().getId());
        return ltoResponse;
    }

    @Transactional
    @Override
    public DevelopTypeResponse updateDevelopType(Long ltoId, DevelopTypeRequest developTypeRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));

        lto.selectDevelopType(developTypeRequest.getContent());

        return DevelopTypeResponse.response(lto.getDevelopType());
    }

    @Transactional
    @Override
    public DevelopTypeResponse removeDevelopType(Long ltoId, DevelopTypeRequest developTypeRequest) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));

        lto.removeDevelopType(developTypeRequest.getContent());
        return DevelopTypeResponse.response(lto.getDevelopType());
    }

    @Override
    public List<LtoResponse> getLtoList(Long studentId) {
        List<Lto> LtoList = ltoRepository.findAllByStudentId(studentId);
        List<LtoResponse> response = new ArrayList<>();

        for(Lto lto : LtoList) {
            LtoResponse ltoResponse = LtoResponse.createLtoResponse(lto.getId(), lto.getTemplateNum(), lto.getStatus(), lto.getName(),
                    lto.getContents(), lto.getDevelopType(), lto.getAchieveDate(), lto.getRegisterDate(),
                    lto.getDelYN(), lto.getDomain().getId(), lto.getStudent().getId());

            response.add(ltoResponse);
        }
        return response;
    }

    @Override
    public List<LtoResponse> getLtoListByStudent(Long studentId, Long domainId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("해당 학생은 존재하지 않습니다."));

        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new IllegalStateException("해당 영역이 존재하지 않습니다."));

        List<Lto> ltoList = ltoRepository.findAllByStudentIdAndDomainId(student.getId(), domain.getId());
        List<LtoResponse> response = new ArrayList<>();

        for(Lto lto : ltoList) {
            LtoResponse ltoResponse = LtoResponse.createLtoResponse(lto.getId(), lto.getTemplateNum(), lto.getStatus(), lto.getName(),
                    lto.getContents(), lto.getDevelopType(), lto.getAchieveDate(), lto.getRegisterDate(),
                    lto.getDelYN(), lto.getDomain().getId(), lto.getStudent().getId());

            response.add(ltoResponse);
        }
        return response;
    }

    @Override
    public List<LtoGraphResponse> getLtoGraph(Long ltoId) {
        Lto lto = ltoRepository.findById(ltoId)
                .orElseThrow(() -> new IllegalStateException("해당 LTO가 존재하지 않습니다."));

        List<StoResponse> stoList = stoService.getStoListByLtoId(lto.getId());
        List<LtoGraphResponse> result = new ArrayList<>();

        for(StoResponse sto : stoList) {
            LtoGraphResponse response = pointService.getGraphValue(sto.getStoId());
            result.add(response);
        }
        return result;
    }

    @Transactional
    @Override
    public boolean deleteLto(Long ltoId) {
        if (ltoRepository.findById(ltoId).isPresent()) {
            ltoRepository.deleteById(ltoId);
        } else {
            throw new IllegalStateException("해당 LTO가 존재하지 않습니다.");
        }
        return true;
    }


}