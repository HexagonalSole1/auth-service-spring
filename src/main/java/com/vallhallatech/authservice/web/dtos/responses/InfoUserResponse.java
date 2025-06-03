package com.vallhallatech.authservice.web.dtos.responses;


import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoUserResponse {


    private Long id;
    private String username;

    private String name;



    private String email;


}
