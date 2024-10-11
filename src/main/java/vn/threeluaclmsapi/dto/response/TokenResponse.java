package vn.threeluaclmsapi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class TokenResponse implements Serializable {

    private String accessToken;

    private String refreshToken;

    private String userId;

}
