package com.spring.edna.services.presenters;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class VerifyDuplicateStorePresenter {
    private boolean isDuplicateName;
    private boolean isDuplicateEmail;
    private boolean isDuplicateCnpj;
    private boolean isDuplicatePhone;
    private HttpStatus status;
}
