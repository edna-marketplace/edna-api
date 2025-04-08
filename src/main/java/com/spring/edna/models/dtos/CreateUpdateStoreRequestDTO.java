package com.spring.edna.models.dtos;

import com.spring.edna.models.entities.Address;
import com.spring.edna.models.entities.Store;
import com.spring.edna.models.entities.StoreDaySchedule;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateUpdateStoreRequestDTO {
    Store store;
    Address address;
    List<StoreDaySchedule> schedule;
}
