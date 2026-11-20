using System.Text.RegularExpressions;
using System.Web.Services;

namespace Segip
{
    [WebService(Namespace = "http://segip.local/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class VerificarCI : WebService
    {
        [WebMethod]
        public bool verificarCI(string ci) =>
            !string.IsNullOrWhiteSpace(ci) && Regex.IsMatch(ci, @"^\d{5,12}$");
    }
}
