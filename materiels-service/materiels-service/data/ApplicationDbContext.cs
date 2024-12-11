using materiels_service.entity;
using Microsoft.EntityFrameworkCore;
namespace materiels_service.data;

public class ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : DbContext(options)
{
    public DbSet<Materiel> Materiels { get; set; }
}