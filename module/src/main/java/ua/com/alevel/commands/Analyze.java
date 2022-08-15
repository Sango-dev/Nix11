package ua.com.alevel.commands;

import ua.com.alevel.service.StreamService;

public class Analyze implements Command {

    @Override
    public void execute() {
        StreamService.printNumbersOfSoldTelephoneProducts();
        StreamService.printNumbersOfSoldTelevisionProducts();
        StreamService.printMinSumAndInfoAboutCustomer();
        StreamService.printWholeSum();
        StreamService.printAmountOfRetailCheck();
        StreamService.printCheckConsistOfOneTypeOfProduct();
        StreamService.printThreeFistChecks();
        StreamService.printInfoAboutChecksWhereCustomersNotAdult();
        StreamService.sortStream();
    }
}
