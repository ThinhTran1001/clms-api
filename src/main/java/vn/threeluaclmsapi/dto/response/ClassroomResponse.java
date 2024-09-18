package vn.threeluaclmsapi.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ClassroomResponse implements Serializable {

    private String classroomName;

    private int capacity;

}