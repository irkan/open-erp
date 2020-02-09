package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sale_service_regulator_task")
@Getter
@Setter
@NoArgsConstructor
public class ServiceRegulatorTask {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "sale_service_regulator_task_sequence")
    @SequenceGenerator(sequenceName = "aa_sale_service_regulator_task_sequence", initialValue = 1, allocationSize = 1, name = "sale_service_regulator_task_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_service_task_id")
    private ServiceTask serviceTask;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sale_service_regulator_id", nullable = false)
    private ServiceRegulator serviceRegulator;

    public ServiceRegulatorTask(ServiceTask serviceTask, ServiceRegulator serviceRegulator) {
        this.serviceTask = serviceTask;
        this.serviceRegulator = serviceRegulator;
    }
}
