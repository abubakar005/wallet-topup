package wallet.topup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wallet.topup.dto.CustomerDTO;
import wallet.topup.entity.CustomerEntity;
import wallet.topup.entity.WalletEntity;
import wallet.topup.enums.Error;
import wallet.topup.exception.NotFoundException;
import wallet.topup.repository.CustomerRepository;
import wallet.topup.repository.WalletRepository;
import wallet.topup.util.Constant;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletRepository walletRepository;

    public void validateCustomer(CustomerDTO customer) {

        if(customer == null)
            throw new NotFoundException(Error.CUSTOMER_NOT_FOUND.getCode(), Error.CUSTOMER_NOT_FOUND.getMsg());

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customer.getId());

        if(!customerEntityOptional.isPresent())
            throw new NotFoundException(Error.CUSTOMER_NOT_EXISTS.getCode(), String.format(Error.CUSTOMER_NOT_EXISTS.getMsg(), customer.getId()));

        CustomerEntity customerEntity = customerEntityOptional.get();

        if(!customerEntity.isActive())
            throw new NotFoundException(Error.CUSTOMER_NOT_ACTIVE.getCode(), Error.CUSTOMER_NOT_ACTIVE.getMsg());

        if(customer.getWallet_id() == null || customer.getWallet_id().isEmpty())
            throw new NotFoundException(Error.WALLET_NOT_FOUND.getCode(), Error.WALLET_NOT_FOUND.getMsg());

        WalletEntity wallet = walletRepository.findByActiveAndCustomerIdAndId(Constant.STATUS_ACTIVE, customerEntity.getId(), customer.getWallet_id());

        if(wallet == null)
            throw new NotFoundException(Error.INVALID_WALLET_ID.getCode(), Error.INVALID_WALLET_ID.getMsg());
    }
}
