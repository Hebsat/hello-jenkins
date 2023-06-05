package org.example.application.model.links;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees_2_projects")
public class Employee2Project {

    @EmbeddedId
    private ProjectAssigningKey projectAssigningKey;
    @Column(name = "employee_id", insertable = false, updatable = false)
    private int employeeId;
    @Column(name = "project_id", insertable = false, updatable = false)
    private int projectId;
}
