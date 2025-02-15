using System;
using _01_lab.Models;

namespace _01_lab;

class Program
{
    static void Main(string[] args)
    {
        Restaurant restaurant = CreateRestaurant();
        PrintRestaurantAvailability(restaurant);
    }

    private static void PrintRestaurantAvailability(Restaurant restaurant)
    {
        Console.WriteLine($"Printing restaurant \"{restaurant.Name}\" availablity:");
        foreach(Table table in restaurant.Tables)
        {
            Console.Write($"Table {table.Number}: ");
            if (table.IsAvailable) Console.WriteLine("available");
            else Console.WriteLine("not available");
        }
    }

    private static Restaurant CreateRestaurant() 
    {
        List<Table> tableList = new List<Table>()
        {
            new()
            {
                Number = 1,
                IsAvailable = true
            },
            new()
            {
                Number = 2,
                IsAvailable = true
            },
            new()
            {
                Number = 3,
                IsAvailable = true
            }
        };

        Restaurant restaurant = new Restaurant()
        {
            Name = "Gusteau's",
            Tables = tableList
        };

        return restaurant;
    }
}