package com.Ironhack.FinalProject.usercontrollers.interfaces;

import com.Ironhack.FinalProject.DTOs.ThirdPartyDTO;
import com.Ironhack.FinalProject.accountmodels.Account;

import java.math.BigDecimal;

public interface ThirdPartyControllerInterface {

    Account thirdPartyAddBalance(ThirdPartyDTO thirdPartyDTO);
    Account thirdPartySubtractBalance(ThirdPartyDTO thirdPartyDTO);
}
