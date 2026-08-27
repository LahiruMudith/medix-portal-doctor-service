package lk.ijse.cms.doctorservice.repository;

import lk.ijse.cms.doctorservice.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
}
