package com.onlinecontacttracing.messaging;

/*
* State and national coronavirus help links.
*/
public enum LocalityResource implements HasEnglishTranslation, HasSpanishTranslation{
  US("https://www.cdc.gov/coronavirus/2019-ncov/index.html"),
  ALABAMA("http://www.alabamapublichealth.gov/covid19/index.html"),
  ALASKA("https://covid19.alaska.gov/"),
  ARIZONA("https://www.azdhs.gov/preparedness/epidemiology-disease-control/infectious-disease-epidemiology/index.php#novel-coronavirus-home"),
  ARKANSAS("https://www.healthy.arkansas.gov/programs-services/topics/novel-coronavirus"),
  CALIFORNIA("https://covid19.ca.gov/"),
  COLORADO("https://covid19.colorado.gov/covid-19-in-colorado/about-covid-19/testing-for-covid-19"),
  CONNECTICUT("https://portal.ct.gov/Coronavirus"),
  DELAWARE("https://coronavirus.delaware.gov/"),
  FLORIDA("https://floridahealthcovid19.gov/"),
  GEORGIA("https://dph.georgia.gov/health-topics/coronavirus-covid-19"),
  HAWAII("https://health.hawaii.gov/coronavirusdisease2019/"),
  IDAHO("https://coronavirus.idaho.gov/"),
  ILLINOIS("https://coronavirus.illinois.gov/s/"),
  INDIANA("https://www.coronavirus.in.gov/"),
  IOWA("https://coronavirus.iowa.gov/"),
  KANSAS("https://www.coronavirus.kdheks.gov/"),
  KENTUCKY("https://govstatus.egov.com/kycovid19"),
  LOUISIANA("http://ldh.la.gov/Coronavirus/"),
  MAINE("https://www.maine.gov/covid19/"),
  MARYLAND("https://coronavirus.maryland.gov/"),
  MASSACHUSETTS("https://www.mass.gov/info-details/covid-19-updates-and-information"),
  MICHIGAN("https://www.michigan.gov/coronavirus/0,9753,7-406-98163_98173---,00.html"),
  MINNESOTA("https://www.health.state.mn.us/diseases/coronavirus/"),
  MISSISSIPPI("https://msdh.ms.gov/msdhsite/_static/14,0,420.html"),
  MISSOURI("https://health.mo.gov/living/healthcondiseases/communicable/novel-coronavirus/"),
  MONTANA("https://dphhs.mt.gov/publichealth/cdepi/diseases/coronavirusmt"),
  NEBRASKA("http://dhhs.ne.gov/Pages/Coronavirus.aspx"),
  NEVADA("https://nvhealthresponse.nv.gov/"),
  NEW_HAMPSHIRE("https://www.nh.gov/covid19/"),
  NEW_JERSEY("https://covid19.nj.gov/"),
  NEW_MEXICO("https://cv.nmhealth.org/"),
  NEW_YORK("https://www1.nyc.gov/site/coronavirus/index.page"),
  NORTH_CAROLINA("https://covid19.ncdhhs.gov/dashboard"),
  NORTH_DAKOTA("https://www.health.nd.gov/diseases-conditions/coronavirus"),
  OHIO("https://coronavirus.ohio.gov/wps/portal/gov/covid-19/home"),
  OKLAHOMA("https://coronavirus.health.ok.gov/"),
  OREGON("https://govstatus.egov.com/OR-OHA-COVID-19"),
  PENNSYLVANIA("https://www.health.pa.gov/topics/disease/coronavirus/Pages/Coronavirus.aspx"),
  RHODE_ISLAND("https://health.ri.gov/covid/"),
  SOUTH_CAROLINA("https://www.scdhec.gov/infectious-diseases/viruses/coronavirus-disease-2019-covid-19"),
  SOUTH_DAKOTA("https://doh.sd.gov/news/Coronavirus.aspx"),
  TENNESSEE("https://www.tn.gov/governor/covid-19.html"),
  TEXAS("https://www.dshs.texas.gov/coronavirus/"),
  UTAH("https://coronavirus.utah.gov/"),
  VERMONT("https://www.healthvermont.gov/response/coronavirus-covid-19"),
  VIRGINIA("https://www.vdh.virginia.gov/coronavirus/"),
  WASHINGTON("https://www.doh.wa.gov/Emergencies/Coronavirus"),
  WEST_VIRGINIA("https://dhhr.wv.gov/COVID-19/Pages/default.aspx"),
  WISCONSIN("https://www.dhs.wisconsin.gov/covid-19/index.htm"),
  WYOMING("https://health.wyo.gov/publichealth/infectious-disease-epidemiology-unit/disease/novel-coronavirus/");
  
  private String helpLink;
  private final String englishTranslation = "Here's your state's help link: ";
  private final String spanishTranslation = "Aquí está el enlace de ayuda de su estado: ";

  LocalityResource(String helpLink) {
    this.helpLink = helpLink;
  }

  public String getEnglishTranslation() {
    if (this == LocalityResource.US) {
        return "Here's the national help link: ".concat(this.helpLink);
    }
    return this.englishTranslation.concat(this.helpLink);
  }
  
  public String getSpanishTranslation() {
    if (this == LocalityResource.US) {
        return "Aquí está el enlace de ayuda nacional: ".concat(this.helpLink);
    }
    return this.spanishTranslation.concat(this.helpLink);
  }

  /*
  * Returns a Locality Resource from a name. Returns the national Locality Resource as default.
  */
  public static LocalityResource getLocalityResourceFromString (String localityResourceName) {
    for (LocalityResource localityResource : LocalityResource.values()) { 
      if (localityResource.name().equals(localityResourceName)) {
        return localityResource;
      }
    }
    return LocalityResource.US;
  }
}
