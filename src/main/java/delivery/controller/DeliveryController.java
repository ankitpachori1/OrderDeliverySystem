package delivery.controller;

import delivery.models.*;
import delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/delivery/assign")
    public DeliveryAssignStatus assignOrder(@RequestBody DeliveryRequest request){
        return deliveryService.assignOrder(request);
    }

    @GetMapping("/driver/list")
    public List<DeliveryPerson> getAllAvailableDrivers(){
        return deliveryService.getAllAvailableDrivers();
    }

    @GetMapping("/driver/{id}")
    public @ResponseBody Boolean getDeliveryPersonStatus(
            @PathVariable("id") String id) throws Exception{
        return deliveryService.getDeLiveryPersonStatus(id);
    }

    @PostMapping(value = "/delivery/status", consumes = "application/json", produces = "application/json")
    public DeliveryResponse updateDeliveryStatus(@RequestBody DeliveryRequest request){
        return deliveryService.updateDeliveryStatus(request);
    }

}
