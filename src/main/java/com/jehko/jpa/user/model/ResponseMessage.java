package com.jehko.jpa.user.model;

import com.jehko.jpa.notice.model.ResponseError;
import com.jehko.jpa.user.entity.User;
import com.sun.net.httpserver.HttpsServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private ResponseMessageHeader header;
    private Object body;

    public static ResponseMessage fail(String message, Object object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .resultCode("")
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build())
                .body(object)
                .build();
    }

    public static ResponseMessage fail(String message) {
        return fail(message, null);
    }

    public static ResponseMessage success(Object object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value())
                        .build())
                .body(object)
                .build();
    }

    public static ResponseMessage success() {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value())
                        .build())
                .body(null)
                .build();
    }
}
