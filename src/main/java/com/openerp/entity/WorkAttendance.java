package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hr_work_attendance")
@Getter
@Setter
@NoArgsConstructor
public class WorkAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "hr_work_attendance_sequence")
    @SequenceGenerator(sequenceName = "aa_hr_work_attendance_sequence", allocationSize = 1, name = "hr_work_attendance_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


}
