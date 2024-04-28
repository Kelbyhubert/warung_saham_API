package com.warungsaham.warungsahamappapi.user.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleReq {
    
    List<Integer> roleIdList;
}
