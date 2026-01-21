package com.gsstock.backend.domain.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_settings")
public class CompanySettings {

        @Id
        private Long id = 1L;

        private String companyName;
        private String ice;
        private String address;
        private String phone;
        private String email;

        @Lob
        @Column(columnDefinition = "LONGBLOB")
        private byte[] logo;

        private String logoContentType;
}
