package com.openerp.repository;

import com.openerp.entity.Illness;
import com.openerp.entity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IllnessRepository extends JpaRepository<Illness, Integer> {
    List<Illness> getIllnessesByActiveTrue();
    Illness getIllnessById(int id);
}