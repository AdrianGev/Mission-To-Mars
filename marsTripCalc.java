import java.util.Scanner;
import java.util.InputMismatchException;

public class marsTripCalc {

    // Assume an average caloric density of food (calories per gram)
    private static final double CALORIC_DENSITY = 1.5; // This value can be adjusted if needed

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean roundTrip = false;

        // Trip Type Selection
        while (true) {
            try {
                System.out.println("Are you planning on taking a one way trip or a round trip? (Say one way or round trip)");
                String tripType = scanner.nextLine().trim().toLowerCase();

                if (tripType.equals("one way")) {
                    System.out.println("You have selected one way.");
                    roundTrip = false;
                    break;
                } else if (tripType.equals("round trip")) {
                    System.out.println("You have selected round trip.");
                    roundTrip = true; 
                    break;
                } else {
                    System.out.println("Invalid input. Please respond with either 'one way' or 'round trip'.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please respond with either 'one way' or 'round trip'.");
                scanner.next();
            }
        }

        // Input: Gender
        System.out.print("Enter your gender (M/F): ");
        String gender = scanner.nextLine().trim().toUpperCase();

        // Input: Age
        int age = 0;
        while (true) {
            try {
                System.out.print("Enter your age: ");
                age = scanner.nextInt();
                if (age > 0) {
                    break;
                } else {
                    System.out.println("Age must be greater than 0!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid age.");
                scanner.next();
            }
        }

        // Input: Birth Month
        int birthMonth = 0;
        while (true) {
            try {
                System.out.print("Enter your birth month (1-12): ");
                birthMonth = scanner.nextInt();
                if (birthMonth >= 1 && birthMonth <= 12) {
                    break;
                } else {
                    System.out.println("Month must be between 1 and 12!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid month.");
                scanner.next();
            }
        }

        // Input: Mars Days
        int marsDaysDecided = 0;
        while (true) {
            try {
                System.out.print("How many days do you plan to spend on Mars? ");
                marsDaysDecided = scanner.nextInt();
                if (marsDaysDecided > 0) {
                    break;  // Exit the loop if a valid number of days is entered
                } else {
                    System.out.println("Please enter a valid amount of days to spend on Mars.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number of days.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Input: Weight, Height, and Activity Level
        System.out.print("Enter your weight in pounds: ");
        double weightPounds = scanner.nextDouble();

        System.out.print("Enter your height in inches: ");
        double heightInches = scanner.nextDouble();

        System.out.print("Enter your activity level (low/average/high): ");
        String activityLevel = scanner.next().toLowerCase();

        // Convert weight and height to metric units
        double weightKg = weightPounds / 2.2;
        double heightCm = heightInches * 2.54;

        // Calculate BEE based on gender
        double bee;
        if (gender.equals("M")) {
            bee = 66.5 + 13.8 * weightKg + 5.0 * heightCm - 6.8 * age;
        } else if (gender.equals("F")) {
            bee = 655.1 + 9.6 * weightKg + 1.9 * heightCm - 4.7 * age;
        } else {
            System.out.println("Invalid gender input.");
            scanner.close();
            return;
        }

        // Determine activity factor based on activity level
        double activityFactor;
        switch (activityLevel) {
            case "low":
                activityFactor = 1.0;
                break;
            case "average":
                activityFactor = 1.25;
                break;
            case "high":
                activityFactor = 1.5;
                break;
            default:
                System.out.println("Invalid activity level input.");
                scanner.close();
                return;
        }

        // Calculate total caloric needs
        double totalCaloriesPerDay = bee * activityFactor;

        // Convert caloric density from calories per gram to calories per pound
        double caloriesPerPound = CALORIC_DENSITY * 454; // 1 pound = 454 grams

        // Calculate pounds of food needed per day
        double poundsNeededPerDay = totalCaloriesPerDay / caloriesPerPound;

        // Determine space travel days based on trip type
        int spaceTravel;
        if (roundTrip) {
            spaceTravel = 18 * 30;
        } else {
            spaceTravel = 9 * 30;
        }

        // Calculate total calories and water needed for the trip
        double totalCalories = poundsNeededPerDay * caloriesPerPound * (spaceTravel + marsDaysDecided);
        double totalWaterOunces = weightPounds * 0.5;

        int totalDaysOnMars = marsDaysDecided + spaceTravel;
        double waterPerDay = totalWaterOunces * 0.0625;

        // Rocket and fuel selection
        String[] rocketTypes = {"Light Rocket", "Medium Rocket", "Heavy Rocket"};
        double[] rocketBaseCosts = {100e9, 200e9, 300e9}; // Rocket base costs in dollars

        String[] fuelTypes = {"Low Efficiency", "Medium Efficiency", "High Efficiency"};
        double[] fuelEfficiency = {0.8, 1.0, 1.2}; // km per unit of fuel (hypothetical values)
        double[] fuelCosts = {100, 200, 300}; // per unit cost of fuel in dollars

        // Assume delta-v (required velocity change) and specific impulse for simplicity
        double deltaV = 9400; // in m/s, hypothetical value
        double specificImpulse = 300; // in seconds, typical for chemical rockets

        System.out.println("Choose your rocket:");
        for (int i = 0; i < rocketTypes.length; i++) {
            System.out.println((i + 1) + ": " + rocketTypes[i] + " (Base Cost: $" + rocketBaseCosts[i] + ")");
        }
        int rocketChoice = scanner.nextInt();
        double chosenRocketCost = rocketBaseCosts[rocketChoice - 1];

        System.out.println("Choose your fuel type:");
        for (int i = 0; i < fuelTypes.length; i++) {
            System.out.println((i + 1) + ": " + fuelTypes[i] + " (Efficiency: " + fuelEfficiency[i] + " km/unit, Cost: $" + fuelCosts[i] + "/unit)");
        }
        int fuelChoice = scanner.nextInt();
        double chosenFuelEfficiency = fuelEfficiency[fuelChoice - 1];
        double chosenFuelCost = fuelCosts[fuelChoice - 1];

        // Simplified fuel calculation
        double requiredFuel = deltaV * 1e6 / chosenFuelEfficiency; // Extremely simplified for demo
        double totalFuelCost = requiredFuel * chosenFuelCost;

        double totalRocketCost = chosenRocketCost + totalFuelCost;


        // Rocket weight parameters
double rocketWeightEmpty = 20000; // Example weight of the empty rocket in kg
double rocketWeightFull = rocketWeightEmpty + 100000; // Example weight with full fuel (100,000 kg of fuel)

// Adjust deltaV and specificImpulse based on fuel consumption
double initialRocketWeight = rocketWeightFull; // Initial total weight (rocket + full fuel)

// Iterative approach to calculate required fuel based on weight changes
double totalFuelRequired = 0;
double currentRocketWeight = initialRocketWeight;
double fuelConsumptionRate = 0.05; // Hypothetical rate of fuel consumption per kg of rocket weight per km

// Approximate distance of one-way trip in km (adjust based on actual mission requirements)
double distanceOneWay = 225000000; // Distance to Mars in km

// Simulate fuel consumption over the distance
for (double distance = 0; distance < distanceOneWay; distance += 1000) { // Increment distance in 1 km steps
    double fuelRequired = deltaV / specificImpulse * currentRocketWeight * fuelConsumptionRate;
    totalFuelRequired += fuelRequired;
    currentRocketWeight -= fuelRequired; // Reduce the rocket weight by the fuel consumed
}

// Calculate the total cost for the required fuel
double totalFuelCostFinal = totalFuelRequired * chosenFuelCost;
double totalRocketshipCostFinal = totalRocketCost + totalFuelCostFinal;
double foodAndWaterInTotalFinal = totalDaysOnMars * (waterPerDay + poundsNeededPerDay);
foodAndWaterInTotalFinal *= 1000000;
double finalBossOfAllNumbersWahahah = totalRocketshipCostFinal + foodAndWaterInTotalFinal;
        //                                  ------   FINAL STATS   ------
        System.out.println("- Mission Stats Overview -");
        System.out.println("");
        System.out.printf("Total calories needed for the trip: %.2f%n", totalCalories);
        System.out.printf("You need approximately %.2f pounds of food per day.%n", poundsNeededPerDay);
        System.out.println("Which equates to " + totalDaysOnMars * (waterPerDay + poundsNeededPerDay) + " pounds of food and water in total");
        System.out.println("And since your total rocket cost amounts to $" + totalRocketshipCostFinal);
        System.out.println("");
        System.out.println("Meaning that the final cost to shatter ANY bank account is... $" + finalBossOfAllNumbersWahahah);
        scanner.close();
    }
}
