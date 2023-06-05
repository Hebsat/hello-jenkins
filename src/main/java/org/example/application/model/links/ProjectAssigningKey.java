package org.example.application.model.links;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ProjectAssigningKey implements Serializable {

    private static final long serialVersionUID = 4014176379219712769L;

    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "project_id")
    private int projectId;
}
