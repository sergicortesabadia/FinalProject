package com.Ironhack.FinalProject.userservices.interfaces;

import com.Ironhack.FinalProject.accountmodels.Account;
import com.Ironhack.FinalProject.usermodels.ThirdParty;

import java.math.BigDecimal;

public interface ThirdPartyServiceInterface {
    Account thirdPartyAddBalance(BigDecimal amount, Long accountId);
    Account thirdPartySubtractBalance(BigDecimal amount, Long accountId);

}
