package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "work_attendance")
@Getter
@Setter
@NoArgsConstructor
public class WorkAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "work_attendance_seq")
    @SequenceGenerator(sequenceName = "work_attendance_seq", allocationSize = 1, name = "work_attendance_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;


}
