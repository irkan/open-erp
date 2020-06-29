package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_module_operation")
@Getter
@Setter
@NoArgsConstructor
public class UserModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "user_module_operation_seq")
    @SequenceGenerator(sequenceName = "user_module_operation_seq", allocationSize = 1, name = "user_module_operation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private ModuleOperation moduleOperation;

    @OneToMany(fetch=FetchType.LAZY )
    @JoinColumn(name = "module_operation_id")
    private List<ModuleOperation> moduleOperations;

    public UserModuleOperation(User user, ModuleOperation moduleOperation) {
        this.user = user;
        this.moduleOperation = moduleOperation;
    }
}