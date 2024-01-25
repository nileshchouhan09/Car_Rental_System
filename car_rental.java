import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay)   {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable=true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name= name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
class rental{
    private Car car;
    private Customer customer;
    private int days;


    public rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;

    }
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

}
class carRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<rental> rentals;

    public carRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();

    }
        public void addCar(Car car){
            cars.add(car);
    }
        public void addCustomer(Customer customer){
        customers.add(customer);
        }

       public void rentCar(Car car , Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new rental(car, customer, days))  ;

        }
        else{
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car){
        rental rentalToRemove = null;
        for(rental rental : rentals){
            if(rental.getCar()== car){
                rentalToRemove = rental;
                car.returnCar();
                break;
            }
        }
        if(rentalToRemove!= null){
            rentals.remove(rentalToRemove);
        }
        else {
            System.out.println("car was not rented");
        }
    }

    public  void menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("===== Car Rental System =====");
            System.out.println("1.Rent a Car");
            System.out.println("2.Return c Car");
            System.out.println("3.Exit");
            System.out.print("Enter your choice: ");
            
            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("\n ==== Rent a Car ====\n");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\n Available Cars \n");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+ " - " + car.getBrand() + " - "+ car.getModel());
                    }
                }

                System.out.print("Enter the carId you want to rent ");
                String carId = sc.nextLine();

                System.out.println("Enter the number of days ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newcustomer = new Customer("Cus" + (customers.size()+1) ,customerName );
                addCustomer(newcustomer);

                Car selectedCar = null;
                for(Car car : cars ){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;                    }
                }
                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n == RENTAL INFORMATION == \n");
                    System.out.println("Customer Id "  + newcustomer.getCustomerId());
                    System.out.println("Customer Name " + newcustomer.getName());
                    System.out.println("Car " + selectedCar.getModel() + " " + selectedCar.getBrand());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.println("Total Price :$" + totalPrice);

                    System.out.println("Confirm Rental (Y/N):");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar , newcustomer, rentalDays );
                        System.out.println("Car rented successfully");
                    }
                    else{
                        System.out.println("Rental Cancelled");
                    }
                }   else{
                    System.out.println("Invalid car selection or car is not available for rent.");
                }
            }else if (choice == 2){
                System.out.println("== Return a Car");
                System.out.println("Enter the carId you want to return :");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId)&& !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn != null){
                    Customer customer = null;
                    for(rental rental : rentals){
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned Successfully By " + customer.getName());
                    }
                    else{
                        System.out.println("Car was not rented or rental information is missing");
                    }
                } else  {
                    System.out.println("Invalid car id or car is not rented");

                }

            } else if (choice == 3) {
                System.out.println("Thanks for using  CAR RENTAL SYSTEM!");
                break;

            }
            else {
                System.out.println("Invalid choice : Please select a valid option ");
            }
        }
    }

}
public class car_rental {
    public static void main(String[] args) {

            carRentalSystem rentalSystem = new carRentalSystem();
            Car car1 = new Car("C01", "Toyota", "Camry", 60);
            Car car2 = new Car("C02", "Mahindra ", "Thar", 150)   ;
            Car car3 = new Car("C03", "Ford", "Eco-sport", 100);
            rentalSystem.addCar(car1);
            rentalSystem.addCar(car2);
            rentalSystem.addCar(car3);

            rentalSystem.menu();

    }
}
