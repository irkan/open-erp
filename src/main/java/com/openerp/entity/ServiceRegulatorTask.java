package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "service_regulator_task")
@Getter
@Setter
@NoArgsConstructor
public class ServiceRegulatorTask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sales_service_regulator_task_seq")
    @SequenceGenerator(sequenceName = "sales_service_regulator_task_seq", initialValue = 1, allocationSize = 1, name = "sales_service_regulator_task_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_task_id")
    private ServiceTask serviceTask;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_regulator_id", nullable = false)
    private ServiceRegulator serviceRegulator;

    public ServiceRegulatorTask(ServiceTask serviceTask, ServiceRegulator serviceRegulator) {
        this.serviceTask = serviceTask;
        this.serviceRegulator = serviceRegulator;
    }
}
