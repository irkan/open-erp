package com.openerp.repository;

import com.openerp.entity.Employee;
import com.openerp.entity.IllnessDetail;
import com.openerp.entity.VacationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface IllnessDetailRepository extends JpaRepository<IllnessDetail, Integer> {
    List<IllnessDetail> getIllnessDetailsByIllness_Id(int illnessId);
    IllnessDetail getIllnessDetailByEmployeeAndIllnessDateAndIllness_Active(Employee employee, Date illnessDate, boolean active);
}