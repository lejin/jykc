package org.jesusyouth.jykc.jykcadmin.repository;

import org.jesusyouth.jykc.jykcadmin.dto.PaymentDTO;
import org.jesusyouth.jykc.jykcadmin.model.Payment;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepo extends CrudRepository<Payment,Integer> {

    @Query(value = " select zone.name as zone,sum(`payment`.`payment_amount`) as amount,count(*) as groups from zone inner join payment on `payment`.`payment_zone`=zone.`id`  group by `payment`.`payment_zone`",nativeQuery = true)
    List<PaymentDTO> getPaidDetails();

    @Query(value = "select zone.name as zone,sum(`group_info`.`group_fee`) as amount,count(*) as groups from zone inner join `group_info` on `group_info`.`group_zone`=zone.`id` where `group_info`.`status`!='Payment received' group by `group_info`.`group_zone`",nativeQuery = true)
    List<PaymentDTO> getNotPaidDetails();
}
