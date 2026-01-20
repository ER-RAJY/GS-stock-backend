package com.gsstock.backend.domain.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_settings")
public class CompanySettings {

    @Id
    private Long id = 1L; // singleton row

    private String companyName;
    private String email;
    private String phone;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] logo;

    private String logoContentType;

    // getters & setters
}
