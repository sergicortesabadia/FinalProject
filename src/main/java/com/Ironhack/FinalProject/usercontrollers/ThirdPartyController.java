package com.Ironhack.FinalProject.usercontrollers;

import com.Ironhack.FinalProject.DTOs.ThirdPartyDTO;
import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.usercontrollers.interfaces.ThirdPartyControllerInterface;
import com.Ironhack.FinalProject.userservices.interfaces.ThirdPartyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThirdPartyController implements ThirdPartyControllerInterface {
    @Autowired
    ThirdPartyServiceInterface thirdPartyServiceInterface;

    @PatchMapping("/third_party/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account thirdPartyAddBalance(@RequestBody ThirdPartyDTO thirdPartyDTO, @RequestHeader String hashedKey){
      return thirdPartyServiceInterface.thirdPartyAddBalance(thirdPartyDTO.getAmount(), thirdPartyDTO.getAccountId(), thirdPartyDTO.getThirdPartyId(), hashedKey);
    }
    @PatchMapping("/third_party/decrease")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account thirdPartySubtractBalance(@RequestBody ThirdPartyDTO thirdPartyDTO, @RequestHeader String hashedKey){
        return thirdPartyServiceInterface.thirdPartySubtractBalance(thirdPartyDTO.getAmount(), thirdPartyDTO.getAccountId(), thirdPartyDTO.getThirdPartyId(), hashedKey);
    }

}
