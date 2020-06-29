package com.openerp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "migration_user_module_operation")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MigrationUserModuleOperation {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "migration_user_module_operation_seq")
    @SequenceGenerator(sequenceName = "migration_user_module_operation_seq", allocationSize = 1, name = "migration_user_module_operation_seq")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "module")
    private String module;

    @Column(name = "module_path")
    private String modulePath;

    @Column(name = "operation")
    private String operation;
}