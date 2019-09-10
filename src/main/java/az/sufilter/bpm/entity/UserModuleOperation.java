package az.sufilter.bpm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "admin_user_module_operation")
@Getter
@Setter
@NoArgsConstructor
public class UserModuleOperation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_module_operation_id")
    private ModuleOperation moduleOperation;

    public UserModuleOperation(User user, ModuleOperation moduleOperation) {
        this.user = user;
        this.moduleOperation = moduleOperation;
    }
}