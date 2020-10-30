package service;

import model.entity.Restaurant;
import model.entity.User;
import model.repository.ConnectionRepository;
import model.repository.ManagerRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ManagerService {
//parisa
//parisa
    private Connection connection = ConnectionRepository.connectionRepository.getConnection();

    public boolean checkUsername(String username){
        if(username.equals("01Manager01")){
            return true;
        }
        return false;
    }
    public void reportUsersWithMonthRegisterationAndSumOfOrderPrice(){
        ManagerRepository managerRepository = new ManagerRepository();
        for (int i = 1; i < 13; i++){
            ArrayList<User> users = managerRepository.
                    getUsersWithMonthRegisterationAndSumOfOrderPrice(connection);
            printUserReport(users, i, 0, 100001);
            printUserReport(users, i, 100000, 500001);
            printUserReport(users, i, 500000, 1500001);
        }
    }

    public void printUserReport(ArrayList<User> users, int month, int minSum, int maxSum){
        System.out.println("Month: " + month);
        System.out.println("Sum of Orders Prices between " + minSum +" and " + maxSum);
        users.stream().filter(a->a.getOrdersSumPrice()>minSum).filter(a->a.getOrdersSumPrice()<maxSum)
                .filter(a->a.getRegMonth()==month).forEach(System.out::println);
    }

    public void reportRestaurantPeykIncomeAndFoodSoldNumber(){
        ManagerRepository managerRepository = new ManagerRepository();
        List<Restaurant> restaurants = managerRepository.
                getRestaurantsWithPeykIncomeAndFoodSold(connection);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 0,
                10001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 10000,
                20001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 4, 20000,
                100001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 0,
                10001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 10000,
                20001);
        printRestaurantPeykIncomeAndFoodReport(restaurants, 5, 20000,
                100001);
    }

    public static void printRestaurantPeykIncomeAndFoodReport(List<Restaurant> restaurants,
                                                              int region, int minPeykIncome,
                                                              int maxPeykIncome){
        /*restaurants.stream().filter(a->a.getRegion()==region).
                filter(a->a.getOrdersNumber()*a.getShipmentPrice()>minPeykIncome)
                .filter(a->a.getOrdersNumber()*a.getShipmentPrice()<maxPeykIncome).peek(a->System.out.
                println("region: "+ a.getRegion() +" name: "+ a.getName()))
                .map(a->a.getFoodAmountSold()).map(a->a.entrySet()).
                forEach(System.out::println);
        /*
        restaurants.stream().filter(a->a.getRegion()==region).
                filter(a->a.getOrdersNumber()*a.getShipmentPrice()>minPeykIncome)
                .filter(a->a.getOrdersNumber()*a.getShipmentPrice()<maxPeykIncome).peek(a->System.out.
                println("region: "+ a.getRegion() +" name: "+ a.getName()))
                .map(a->a.getFoodAmountSold()).map(a->a.entrySet()).max((entry1, entry2) ->
                entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();*/
        restaurants.stream().filter(a->a.getRegion()==region).
                filter(a->a.getOrdersNumber()*a.getShipmentPrice()>minPeykIncome)
                .filter(a->a.getOrdersNumber()*a.getShipmentPrice()<maxPeykIncome).peek(a->System.out.
                println("region: "+ a.getRegion() +" name: "+ a.getName())).forEach(a->
                System.out.println(a.getFoodAmountSold().entrySet().stream()
                        .max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey().getName()));
    }
}
