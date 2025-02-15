using System;

namespace _01_lab.Models;

public class Restaurant
{
    public required string Name { get; init; }
    public List<Table> Tables { get; init; } = new();
}