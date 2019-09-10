package az.sufilter.bpm.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_operation")
@Getter
@Setter
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "admin_sequence")
    @SequenceGenerator(sequenceName = "aa_admin_sequence", allocationSize = 1, name = "admin_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "path")
    private String path;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_dictionary_icon_id")
    private Dictionary icon;

    @OneToMany(mappedBy = "operation")
    private List<ModuleOperation> moduleOperations;

    public Operation(String name, String path, Dictionary icon, List<ModuleOperation> moduleOperations) {
        this.name = name;
        this.path = path;
        this.icon = icon;
        this.moduleOperations = moduleOperations;
    }
}
