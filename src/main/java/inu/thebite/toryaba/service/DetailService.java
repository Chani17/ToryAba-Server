package inu.thebite.toryaba.service;

import inu.thebite.toryaba.model.notice.*;

import java.util.List;

public interface DetailService {
    void addDetail(Long studentId, String year, int month, String date, Long stoId);

    DetailResponse updateComment(Long studentId, String year, int month, String date, Long stoId, AddCommentRequest addCommentRequest);

    List<DetailObjectResponse> getDetailList(Long studentId, String year, int month, String date);

    AutoCommentResponse getDetailAutoComment(Long studentId, Long ltoId, String year, int month, String date);
}
