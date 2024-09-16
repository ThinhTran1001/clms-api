package vn.threeluaclmsapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseData<T> {

    private String statusCode;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // GET
    public ResponseData(String statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // POST, PUT, DELETE
    public ResponseData(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
