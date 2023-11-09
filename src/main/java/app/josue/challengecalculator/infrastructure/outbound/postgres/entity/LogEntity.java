package app.josue.challengecalculator.infrastructure.outbound.postgres.entity;

import app.josue.challengecalculator.domain.model.LogModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "logs")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String httpMethod;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String path;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Column(columnDefinition = "TIMESTAMP", name = "created_at")
    private OffsetDateTime createdAt;

    public LogEntity(LogModel logModel) {
        this.id = logModel.id();
        this.httpMethod = logModel.httpMethod();
        this.path = logModel.path();
        this.requestBody = logModel.requestBody();
        this.responseBody = logModel.responseBody();
        this.createdAt = logModel.createdAt();
    }

    public LogEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LogModel toModel() {
        return new LogModel(
                getId(),
                getHttpMethod(),
                getPath(),
                getRequestBody(),
                getResponseBody(),
                getCreatedAt()
        );
    }
}
