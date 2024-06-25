using System.Net.Http.Headers;
namespace IonosApiTests.API
{
    
    public static class ApiHelper
    {
        // static because we want to open the ApiClient once for the whole Application
        // won't be a problem because it's designed to be used that way i.e. threadsave and build for constant concurrent calls
        public static HttpClient ApiClient { get; set; }

        public static void InitializeClient()
        {
            ApiClient = new HttpClient();
            ApiClient.DefaultRequestHeaders.Accept.Clear();
            // using System.Net.Http.Headers;
            ApiClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue(("application/json")));
        }
        
        
    }
}
