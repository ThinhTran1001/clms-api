package vn.threeluaclmsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.threeluaclmsapi.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
