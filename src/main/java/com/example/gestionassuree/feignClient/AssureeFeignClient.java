package com.example.gestionassuree.feignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("NOTIFICATION-SERVICE")
public interface AssureeFeignClient {
    @GetMapping("/mail/{mail}/{password}/{username}")
     void sendMailToAssuree(@PathVariable String mail,@PathVariable String password, @PathVariable String username);
}
