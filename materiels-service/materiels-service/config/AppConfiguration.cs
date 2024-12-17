namespace materiels_service.config;

public class AppConfiguration
{
    public AppConfiguration ApplicationConfiguration { get; set; }
    
    public string[] configs { get; set; }
    
    public string url { get; set; }
}

public class AppConfigurationInformation
{
    public string name { get; set; }
    public int version { get; set; }
}