package com.openerp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "module_operation")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "module_operation_seq")
    @SequenceGenerator(sequenceName = "module_operation_seq", allocationSize = 1, name = "module_operation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    @JsonIgnore
    @OneToMany(mappedBy = "moduleOperation")
    private List<TemplateModuleOperation> templateModuleOperations;

    @JsonIgnore
    @OneToMany(mappedBy = "moduleOperation")
    private List<UserModuleOperation> moduleOperations;

    public ModuleOperation(Module module, Operation operation, List<TemplateModuleOperation> templateModuleOperations) {
        this.module = module;
        this.operation = operation;
        this.templateModuleOperations = templateModuleOperations;
    }
}
