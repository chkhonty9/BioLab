using materiels_service.entity;
using Microsoft.EntityFrameworkCore;
namespace materiels_service.data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) 
        : base(options)
    { }

    public DbSet<Materiel> Materiels { get; set; }
    
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // Optional: If you want to specify table name explicitly, here is how:
        modelBuilder.Entity<Materiel>().ToTable("materiel"); // Maps the entity to the "materiel" table
    }
}