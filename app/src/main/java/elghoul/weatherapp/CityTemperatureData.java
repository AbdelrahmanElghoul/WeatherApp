package elghoul.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityTemperatureData implements Parcelable {


    /**
     * location : {"name":"Tanda","region":"Al Gharbiyah","country":"Egypt","lat":30.79,"lon":31,"tz_id":"Africa/Cairo","localtime_epoch":1530198171,"localtime":"2018-06-28 17:02"}
     * current : {"last_updated_epoch":1530198007,"last_updated":"2018-06-28 17:00","temp_c":33,"temp_f":91.4,"is_day":1,"condition":{"text":"Partly cloudy","icon":"//cdn.apixu.com/weather/64x64/day/116.png","code":1003},"wind_mph":12.5,"wind_kph":20.2,"wind_degree":350,"wind_dir":"N","pressure_mb":1009,"pressure_in":30.3,"precip_mm":0,"precip_in":0,"humidity":39,"cloud":50,"feelslike_c":34.3,"feelslike_f":93.7,"vis_km":10,"vis_miles":6}
     * forecast : {"forecastday":[{"date":"2018-06-28","date_epoch":1530144000,"day":{"maxtemp_c":36,"maxtemp_f":96.8,"mintemp_c":21.5,"mintemp_f":70.7,"avgtemp_c":29.3,"avgtemp_f":84.7,"maxwind_mph":17.2,"maxwind_kph":27.7,"totalprecip_mm":0,"totalprecip_in":0,"avgvis_km":18.8,"avgvis_miles":11,"avghumidity":52,"condition":{"text":"Partly cloudy","icon":"//cdn.apixu.com/weather/64x64/day/116.png","code":1003},"uv":10.2},"astro":{"sunrise":"12:00 AM","sunset":"04:55 AM","moonrise":"12:00 AM","moonset":"07:16 PM"}}]}
     */

    private Location location;
    private Current current;
    private Forecast forecast;

    private CityTemperatureData(Parcel in) {
    }

    public static final Creator<CityTemperatureData> CREATOR = new Creator<CityTemperatureData>() {
        @Override
        public CityTemperatureData createFromParcel(Parcel in) {
            return new CityTemperatureData( in );
        }

        @Override
        public CityTemperatureData[] newArray(int size) {
            return new CityTemperatureData[size];
        }
    };

    CityTemperatureData() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class Location implements Parcelable {
        /**
         * name : Tanda
         * region : Al Gharbiyah
         * country : Egypt
         * lat : 30.79
         * lon : 31.0
         * tz_id : Africa/Cairo
         * localtime_epoch : 1530198171
         * localtime : 2018-06-28 17:02
         */

        private String name;
        private String region;
        private String country;
        private double lat;
        private double lon;
        private String tz_id;
        private int localtime_epoch;
        private String localtime;

        public Location(String name, String region, String country, double lat, double lon, String tz_id, int localtime_epoch, String localtime) {
            this.name = name;
            this.region = region;
            this.country = country;
            this.lat = lat;
            this.lon = lon;
            this.tz_id = tz_id;
            this.localtime_epoch = localtime_epoch;
            this.localtime = localtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getTz_id() {
            return tz_id;
        }

        public void setTz_id(String tz_id) {
            this.tz_id = tz_id;
        }

        public int getLocaltime_epoch() {
            return localtime_epoch;
        }

        public void setLocaltime_epoch(int localtime_epoch) {
            this.localtime_epoch = localtime_epoch;
        }

        public String getLocaltime() {
            return localtime;
        }

        public void setLocaltime(String localtime) {
            this.localtime = localtime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( this.name );
            dest.writeString( this.region );
            dest.writeString( this.country );
            dest.writeDouble( this.lat );
            dest.writeDouble( this.lon );
            dest.writeString( this.tz_id );
            dest.writeInt( this.localtime_epoch );
            dest.writeString( this.localtime );
        }

        public Location() {
        }

        protected Location(Parcel in) {
            this.name = in.readString();
            this.region = in.readString();
            this.country = in.readString();
            this.lat = in.readDouble();
            this.lon = in.readDouble();
            this.tz_id = in.readString();
            this.localtime_epoch = in.readInt();
            this.localtime = in.readString();
        }

        public static final Creator<Location> CREATOR = new Creator<Location>() {
            @Override
            public Location createFromParcel(Parcel source) {
                return new Location( source );
            }

            @Override
            public Location[] newArray(int size) {
                return new Location[size];
            }
        };
    }

    public static class Current implements Parcelable {
        /**
         * last_updated_epoch : 1530198007
         * last_updated : 2018-06-28 17:00
         * temp_c : 33.0
         * temp_f : 91.4
         * is_day : 1
         * condition : {"text":"Partly cloudy","icon":"//cdn.apixu.com/weather/64x64/day/116.png","code":1003}
         * wind_mph : 12.5
         * wind_kph : 20.2
         * wind_degree : 350
         * wind_dir : N
         * pressure_mb : 1009.0
         * pressure_in : 30.3
         * precip_mm : 0.0
         * precip_in : 0.0
         * humidity : 39
         * cloud : 50
         * feelslike_c : 34.3
         * feelslike_f : 93.7
         * vis_km : 10.0
         * vis_miles : 6.0
         */

        private int last_updated_epoch;
        private String last_updated;
        private double temp_c;
        private double temp_f;
        private int is_day;
        private Condition condition;
        private double wind_mph;
        private double wind_kph;
        private int wind_degree;
        private String wind_dir;
        private double pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double feelslike_f;


        public Current(int last_updated_epoch, String last_updated, double temp_c, double temp_f, int is_day
                , String Condition_text, String Condition_icon, int Condition_code, double wind_mph, double wind_kph
                , int wind_degree, String wind_dir, double pressure_mb, double pressure_in, double precip_mm, double precip_in
                , int humidity, int cloud, double feelslike_c, double feelslike_f) {
            this.last_updated_epoch = last_updated_epoch;
            this.last_updated = last_updated;
            this.temp_c = temp_c;
            this.temp_f = temp_f;
            this.is_day = is_day;
            this.condition = new Condition(Condition_text,Condition_icon,Condition_code);
            this.wind_mph = wind_mph;
            this.wind_kph = wind_kph;
            this.wind_degree = wind_degree;
            this.wind_dir = wind_dir;
            this.pressure_mb = pressure_mb;
            this.pressure_in = pressure_in;
            this.precip_mm = precip_mm;
            this.precip_in = precip_in;
            this.humidity = humidity;
            this.cloud = cloud;
            this.feelslike_c = feelslike_c;
            this.feelslike_f = feelslike_f;

        }

        public int getLast_updated_epoch() {
            return last_updated_epoch;
        }

        public void setLast_updated_epoch(int last_updated_epoch) {
            this.last_updated_epoch = last_updated_epoch;
        }

        public String getLast_updated() {
            return last_updated;
        }

        public void setLast_updated(String last_updated) {
            this.last_updated = last_updated;
        }

        public double getTemp_c() {
            return temp_c;
        }

        public void setTemp_c(double temp_c) {
            this.temp_c = temp_c;
        }

        public double getTemp_f() {
            return temp_f;
        }

        public void setTemp_f(double temp_f) {
            this.temp_f = temp_f;
        }

        public int getIs_day() {
            return is_day;
        }

        public void setIs_day(int is_day) {
            this.is_day = is_day;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }

        public double getWind_mph() {
            return wind_mph;
        }

        public void setWind_mph(double wind_mph) {
            this.wind_mph = wind_mph;
        }

        public double getWind_kph() {
            return wind_kph;
        }

        public void setWind_kph(double wind_kph) {
            this.wind_kph = wind_kph;
        }

        public int getWind_degree() {
            return wind_degree;
        }

        public void setWind_degree(int wind_degree) {
            this.wind_degree = wind_degree;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public double getPressure_mb() {
            return pressure_mb;
        }

        public void setPressure_mb(double pressure_mb) {
            this.pressure_mb = pressure_mb;
        }

        public double getPressure_in() {
            return pressure_in;
        }

        public void setPressure_in(double pressure_in) {
            this.pressure_in = pressure_in;
        }

        public double getPrecip_mm() {
            return precip_mm;
        }

        public void setPrecip_mm(double precip_mm) {
            this.precip_mm = precip_mm;
        }

        public double getPrecip_in() {
            return precip_in;
        }

        public void setPrecip_in(double precip_in) {
            this.precip_in = precip_in;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getCloud() {
            return cloud;
        }

        public void setCloud(int cloud) {
            this.cloud = cloud;
        }

        public double getFeelslike_c() {
            return feelslike_c;
        }

        public void setFeelslike_c(double feelslike_c) {
            this.feelslike_c = feelslike_c;
        }

        public double getFeelslike_f() {
            return feelslike_f;
        }

        public void setFeelslike_f(double feelslike_f) {
            this.feelslike_f = feelslike_f;
        }



        public static class Condition {
            /**
             * text : Partly cloudy
             * icon : //cdn.apixu.com/weather/64x64/day/116.png
             * code : 1003
             */

            private String text;
            private String icon;
            private int code;

            public Condition(String text, String icon, int code) {
                this.text = text;
                this.icon = icon;
                this.code = code;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt( this.last_updated_epoch );
            dest.writeString( this.last_updated );
            dest.writeDouble( this.temp_c );
            dest.writeDouble( this.temp_f );
            dest.writeInt( this.is_day );
            dest.writeParcelable( (Parcelable) this.condition, flags );
            dest.writeDouble( this.wind_mph );
            dest.writeDouble( this.wind_kph );
            dest.writeInt( this.wind_degree );
            dest.writeString( this.wind_dir );
            dest.writeDouble( this.pressure_mb );
            dest.writeDouble( this.pressure_in );
            dest.writeDouble( this.precip_mm );
            dest.writeDouble( this.precip_in );
            dest.writeInt( this.humidity );
            dest.writeInt( this.cloud );
            dest.writeDouble( this.feelslike_c );
            dest.writeDouble( this.feelslike_f );

        }

        public Current() {
        }

        protected Current(Parcel in) {
            this.last_updated_epoch = in.readInt();
            this.last_updated = in.readString();
            this.temp_c = in.readDouble();
            this.temp_f = in.readDouble();
            this.is_day = in.readInt();
            this.condition = in.readParcelable( Condition.class.getClassLoader() );
            this.wind_mph = in.readDouble();
            this.wind_kph = in.readDouble();
            this.wind_degree = in.readInt();
            this.wind_dir = in.readString();
            this.pressure_mb = in.readDouble();
            this.pressure_in = in.readDouble();
            this.precip_mm = in.readDouble();
            this.precip_in = in.readDouble();
            this.humidity = in.readInt();
            this.cloud = in.readInt();
            this.feelslike_c = in.readDouble();
            this.feelslike_f = in.readDouble();

        }

        public static final Creator<Current> CREATOR = new Creator<Current>() {
            @Override
            public Current createFromParcel(Parcel source) {
                return new Current( source );
            }

            @Override
            public Current[] newArray(int size) {
                return new Current[size];
            }
        };
    }

    public static class Forecast implements Parcelable {

        private List<Forecastday> forecastday;

        public Forecast(List<Forecastday> forecastday) {
            this.forecastday = forecastday;
        }

        public List<Forecastday> getForecastday() {
            return forecastday;
        }

        public void setForecastday(List<Forecastday> forecastday) {
            this.forecastday = forecastday;
        }

        public static class Forecastday {
            /**
             * date : 2018-06-28
             * date_epoch : 1530144000
             * day : {"maxtemp_c":36,"maxtemp_f":96.8,"mintemp_c":21.5,"mintemp_f":70.7,"avgtemp_c":29.3,"avgtemp_f":84.7,"maxwind_mph":17.2,"maxwind_kph":27.7,"totalprecip_mm":0,"totalprecip_in":0,"avgvis_km":18.8,"avgvis_miles":11,"avghumidity":52,"condition":{"text":"Partly cloudy","icon":"//cdn.apixu.com/weather/64x64/day/116.png","code":1003},"uv":10.2}
             * astro : {"sunrise":"12:00 AM","sunset":"04:55 AM","moonrise":"12:00 AM","moonset":"07:16 PM"}
             */

            private String date;
            private int date_epoch;
            private Day day;
            private Astro astro;

            public Forecastday(String date, int date_epoch,double maxtemp_c, double maxtemp_f, double mintemp_c, double mintemp_f, double avgtemp_c, double avgtemp_f, double maxwind_mph, double maxwind_kph, double totalprecip_mm, double totalprecip_in, double avghumidity,String text, String icon, int code,String sunrise, String sunset, String moonrise, String moonset) {
                this.date = date;
                this.date_epoch = date_epoch;
                this.day=new Day(  maxtemp_c,  maxtemp_f,  mintemp_c,  mintemp_f,  avgtemp_c,  avgtemp_f,  maxwind_mph,  maxwind_kph,  totalprecip_mm,  totalprecip_in,avghumidity, text,  icon,  code );
                this.astro=new Astro(  sunrise,  sunset,  moonrise,  moonset) ;
            }


            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getDate_epoch() {
                return date_epoch;
            }

            public void setDate_epoch(int date_epoch) {
                this.date_epoch = date_epoch;
            }

            public Day getDay() {
                return day;
            }

            public void setDay(Day day) {
                this.day = day;
            }

            public Astro getAstro() {
                return astro;
            }

            public void setAstro(Astro astro) {
                this.astro = astro;
            }

            public static class Day {
                /**
                 * maxtemp_c : 36.0
                 * maxtemp_f : 96.8
                 * mintemp_c : 21.5
                 * mintemp_f : 70.7
                 * avgtemp_c : 29.3
                 * avgtemp_f : 84.7
                 * maxwind_mph : 17.2
                 * maxwind_kph : 27.7
                 * totalprecip_mm : 0.0
                 * totalprecip_in : 0.0
                 * avgvis_km : 18.8
                 * avgvis_miles : 11.0
                 * avghumidity : 52.0
                 * condition : {"text":"Partly cloudy","icon":"//cdn.apixu.com/weather/64x64/day/116.png","code":1003}
                 * uv : 10.2
                 */

                private double maxtemp_c;
                private double maxtemp_f;

                private double mintemp_c;
                private double mintemp_f;

                private double avgtemp_c;
                private double avgtemp_f;

                private double maxwind_mph;
                private double maxwind_kph;

                private double totalprecip_mm;
                private double totalprecip_in;

                private double avghumidity;
                private ConditionX condition;


                public Day(double maxtemp_c, double maxtemp_f, double mintemp_c, double mintemp_f, double avgtemp_c, double avgtemp_f, double maxwind_mph, double maxwind_kph
                        , double totalprecip_mm, double totalprecip_in,  double avghumidity,String text, String icon, int code) {
                    this.maxtemp_c = maxtemp_c;
                    this.maxtemp_f = maxtemp_f;
                    this.mintemp_c = mintemp_c;
                    this.mintemp_f = mintemp_f;
                    this.avgtemp_c = avgtemp_c;
                    this.avgtemp_f = avgtemp_f;
                    this.maxwind_mph = maxwind_mph;
                    this.maxwind_kph = maxwind_kph;
                    this.totalprecip_mm = totalprecip_mm;
                    this.totalprecip_in = totalprecip_in;

                    this.avghumidity = avghumidity;
                    this.condition = new ConditionX( text, icon,  code);

                }

                public double getMaxtemp_c() {
                    return maxtemp_c;
                }

                public void setMaxtemp_c(double maxtemp_c) {
                    this.maxtemp_c = maxtemp_c;
                }

                public double getMaxtemp_f() {
                    return maxtemp_f;
                }

                public void setMaxtemp_f(double maxtemp_f) {
                    this.maxtemp_f = maxtemp_f;
                }

                public double getMintemp_c() {
                    return mintemp_c;
                }

                public void setMintemp_c(double mintemp_c) {
                    this.mintemp_c = mintemp_c;
                }

                public double getMintemp_f() {
                    return mintemp_f;
                }

                public void setMintemp_f(double mintemp_f) {
                    this.mintemp_f = mintemp_f;
                }

                public double getAvgtemp_c() {
                    return avgtemp_c;
                }

                public void setAvgtemp_c(double avgtemp_c) {
                    this.avgtemp_c = avgtemp_c;
                }

                public double getAvgtemp_f() {
                    return avgtemp_f;
                }

                public void setAvgtemp_f(double avgtemp_f) {
                    this.avgtemp_f = avgtemp_f;
                }

                public double getMaxwind_mph() {
                    return maxwind_mph;
                }

                public void setMaxwind_mph(double maxwind_mph) {
                    this.maxwind_mph = maxwind_mph;
                }

                public double getMaxwind_kph() {
                    return maxwind_kph;
                }

                public void setMaxwind_kph(double maxwind_kph) {
                    this.maxwind_kph = maxwind_kph;
                }

                public double getTotalprecip_mm() {
                    return totalprecip_mm;
                }

                public void setTotalprecip_mm(double totalprecip_mm) {
                    this.totalprecip_mm = totalprecip_mm;
                }

                public double getTotalprecip_in() {
                    return totalprecip_in;
                }

                public void setTotalprecip_in(double totalprecip_in) {
                    this.totalprecip_in = totalprecip_in;
                }


                public double getAvghumidity() {
                    return avghumidity;
                }

                public void setAvghumidity(double avghumidity) {
                    this.avghumidity = avghumidity;
                }

                public ConditionX getCondition() {
                    return condition;
                }

                public void setCondition(ConditionX condition) {
                    this.condition = condition;
                }


                public static class ConditionX {
                    /**
                     * text : Partly cloudy
                     * icon : //cdn.apixu.com/weather/64x64/day/116.png
                     * code : 1003
                     */

                    private String text;
                    private String icon;
                    private int code;

                    public ConditionX(String text, String icon, int code) {
                        this.text = text;
                        this.icon = icon;
                        this.code = code;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }
                }
            }

            public static class Astro {
                /**
                 * sunrise : 12:00 AM
                 * sunset : 04:55 AM
                 * moonrise : 12:00 AM
                 * moonset : 07:16 PM
                 */

                private String sunrise;
                private String sunset;
                private String moonrise;
                private String moonset;

                    public Astro(String sunrise, String sunset, String moonrise, String moonset) {
                    this.sunrise = sunrise;
                    this.sunset = sunset;
                    this.moonrise = moonrise;
                    this.moonset = moonset;
                }

                public String getSunrise() {
                    return sunrise;
                }

                public void setSunrise(String sunrise) {
                    this.sunrise = sunrise;
                }

                public String getSunset() {
                    return sunset;
                }

                public void setSunset(String sunset) {
                    this.sunset = sunset;
                }

                public String getMoonrise() {
                    return moonrise;
                }

                public void setMoonrise(String moonrise) {
                    this.moonrise = moonrise;
                }

                public String getMoonset() {
                    return moonset;
                }

                public void setMoonset(String moonset) {
                    this.moonset = moonset;
                }
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList( this.forecastday );
        }

        public Forecast() {
        }

        protected Forecast(Parcel in) {
            this.forecastday = new ArrayList<Forecastday>();
            in.readList( this.forecastday, Forecastday.class.getClassLoader() );
        }

        public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
            @Override
            public Forecast createFromParcel(Parcel source) {
                return new Forecast( source );
            }

            @Override
            public Forecast[] newArray(int size) {
                return new Forecast[size];
            }
        };
    }
}