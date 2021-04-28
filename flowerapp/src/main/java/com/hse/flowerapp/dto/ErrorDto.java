package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto {
    String errorOperation;
    String errorMessage;
    Boolean isError;

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public ErrorDto(Boolean isError, String errorOperation, String errorMessage) {
        this.errorOperation = errorOperation;
        this.errorMessage = errorMessage;
        this.isError = isError;
    }

    public String getErrorOperation() {
        return errorOperation;
    }

    public void setErrorOperation(String errorOperation) {
        this.errorOperation = errorOperation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
