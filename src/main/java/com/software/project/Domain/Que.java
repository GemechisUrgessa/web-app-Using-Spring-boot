package com.software.project.Domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Que{

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private int id;
        private String title;//the name of the History
        @Lob
        private String contentIntro;
        @Lob
        private String contentData;
        private  String location;
        @Lob
        private byte[] imgData;
        private String yearElapsed;
        private String contentType;
        private float rateing;
        private long viewCount;
        private long commentCount;
        
        private int approvalCount;
        private int declineCount;
        private String adminOne;
        private String adminTwo;
        private String adminThree;
        private String adminFour;
        private String isUpdate;
        private int oldId;

}
    