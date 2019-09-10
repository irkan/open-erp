package az.sufilter.bpm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_module_operation")
@Getter
@Setter
@NoArgsConstructor
public class ModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_operation_id")
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
