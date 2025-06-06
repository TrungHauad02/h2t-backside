package com.englishweb.h2t_backside.dto.response;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String message;     // Tiêu đề ngắn gọn về lỗi
    private int status;         // Mã trạng thái HTTP
    private String detail;      // Mô tả chi tiết về lỗi
    private String instance;    // URI của yêu cầu gây ra lỗi
    private LocalDateTime timestamp; // Thời gian xảy ra lỗi
    private String errorCode;   // Mã lỗi tùy chỉnh
    private Object data;        // Dữ liệu liên quan (nếu có)
    private SeverityEnum severity;  // Muc do nghiem trong
}
