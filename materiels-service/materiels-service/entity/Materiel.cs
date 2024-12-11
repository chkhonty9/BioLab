namespace materiels_service.entity;

public class Materiel
{
    public int Id { get; set; }
    public string Description { get; set; }
    public DateTime Date { get; set; }
    public bool Available { get; set; }
    public string Serie { get; set; }
}