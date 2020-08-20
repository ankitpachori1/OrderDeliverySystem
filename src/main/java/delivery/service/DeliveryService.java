package delivery.service;

import delivery.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DeliveryService {

    @Autowired
    private RestTemplate restTemplate;

    Map<String, DeliveryPerson> deliveryPersonList = new HashMap<>();

    Map<String, String> busyDrivers = new HashMap<>();

    @PostConstruct
    public void init(){
        for(int i = 0; i < 10; i++){
            String id = UUID.randomUUID().toString();
            String name = UUID.randomUUID().toString().substring(0, 6);
            deliveryPersonList.put(id, new DeliveryPerson(name, id, true));
        }
    }

    public DeliveryAssignStatus assignOrder(DeliveryRequest request){
        Boolean isOrderExists = true;
        if(isOrderExists){
            if(deliveryPersonList.containsKey(request.getDeliveryPersonId())){
                DeliveryPerson d = deliveryPersonList.get(request.getDeliveryPersonId());
                if(d.getIsAvailable() == true){
                    d.setIsAvailable(false);
                    deliveryPersonList.put(request.getDeliveryPersonId(), d);
                    busyDrivers.put(request.getOrderId(), request.getDeliveryPersonId());
                    return DeliveryAssignStatus.ACCEPTED;
                }
                else{
                    return DeliveryAssignStatus.REJECTED;
                }
            }
            else {
                return DeliveryAssignStatus.DELIVERY_PERSON_NOT_FOUND;
            }
        }
        else return DeliveryAssignStatus.ORDER_NOT_FOUND;
    }

    public List<DeliveryPerson> getAllAvailableDrivers(){
        return deliveryPersonList.values().stream().filter(d ->
                d.getIsAvailable()==true)
                .collect(Collectors.toList());
    }

    public DeliveryPersonStatus getDeLiveryPersonStatus(String id){
        if(deliveryPersonList.containsKey(id)){
            if(deliveryPersonList.get(id).getIsAvailable())
                return DeliveryPersonStatus.AVAILABLE;
            else return DeliveryPersonStatus.BUSY;
        }
        else return DeliveryPersonStatus.NOT_FOUND;
    }

    public DeliveryResponse updateDeliveryStatus(DeliveryRequest request){
        ResponseEntity<DeliveryResponse> res = restTemplate.postForEntity(
                "http://localhost:8088/delivery/status", request, DeliveryResponse.class);
        if(busyDrivers.containsKey(request.getOrderId())){
            String driverId = busyDrivers.get(request.getOrderId());
            DeliveryPerson deliveryPerson = deliveryPersonList.get(driverId);
            deliveryPerson.setIsAvailable(true);
            deliveryPersonList.put(driverId, deliveryPerson);
        }
        return res.getBody();
    }
}
