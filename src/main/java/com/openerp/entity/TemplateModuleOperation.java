package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "template_module_operation")
@Getter
@Setter
@NoArgsConstructor
public class TemplateModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "template_module_operation_seq")
    @SequenceGenerator(sequenceName = "template_module_operation_seq", allocationSize = 1, name = "template_module_operation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dictionary_template_id")
    private Dictionary template;

    @ManyToOne(fetch = FetchType.EAGER)
    private ModuleOperation moduleOperation;

    @OneToMany(fetch=FetchType.LAZY )
    @JoinColumn(name = "module_operation_id")
    private List<ModuleOperation> moduleOperations;

    public TemplateModuleOperation(Dictionary template, ModuleOperation moduleOperation) {
        this.template = template;
        this.moduleOperation = moduleOperation;
    }
}
