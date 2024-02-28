package com.example.quickstart.service;

import Currencyconverter.CurrencyGrpc;
import Currencyconverter.Request;
import Currencyconverter.Response;
import com.example.quickstart.models.CurrencyType;
import com.example.quickstart.models.Money;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class CurrencyConverter {

//    @Value("${converter.grpc.service.host}")
    private static final String host = "localhost";

//    @Value("${converter.grpc.service.port}")
    private static final int port = 9090;




    public Money convertToINR(Money money){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
        CurrencyGrpc.CurrencyBlockingStub stub = CurrencyGrpc.newBlockingStub(channel);
        double value = money.getValue();
        Request request = Request.newBuilder().setCurrency(money.getCurrencyType().toString()).setValue((float)value).build();
        Response response = stub.convertToINR(request);
        double base_to_final = Double.parseDouble(String.format("%.2f", response.getValue()));
        money.setValue(base_to_final);
        money.setCurrencyType(CurrencyType.INR);
        return money;
    }

    public Money convertFromINR(Money money,CurrencyType targetCurrency){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host,port).usePlaintext().build();
        CurrencyGrpc.CurrencyBlockingStub stub = CurrencyGrpc.newBlockingStub(channel);
        double value = money.getValue();
        Request request = Request.newBuilder().setCurrency(money.getCurrencyType().toString()).setValue((float)value).setTargetCurrency(targetCurrency.toString()).build();
        Response response = stub.convertFromINR(request);
        double base_to_final = Double.parseDouble(String.format("%.2f", response.getValue()));
        money.setValue(base_to_final);
        money.setCurrencyType(targetCurrency);
        return money;
    }



}
