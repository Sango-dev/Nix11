package ua.com.alevel.commands;
import ua.com.alevel.service.ShopService;

public class CreateOrders implements Command {

    @Override
    public void execute() {
        double limit = UtilUser.inputLimit();
        ShopService.createOrders(limit);
    }
}
