package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "events")
@NamedQuery(name = "UserEvent.findByUserAndEvent", query = "SELECT ue FROM UserEvent ue WHERE ue.user = :user AND ue.event = :event")
public class Event implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "event", orphanRemoval = true)
    @JsonIgnore
    private Set<UserEvent> userEvents = new HashSet<>();
    @Column(nullable = true)
    private String image;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private LocalTime timeBegin;
    @Column(nullable = false)
    private LocalTime timeEnd;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String info;
    @Column(nullable = false)
    private int size;



    public Event (String image, String name, String city, Date date, LocalTime timeBegin, LocalTime timeEnd, double price, String location, String info, int size) {
        this.image = image;
        this.name = name;
        this.city = city;
        this.date = date;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.price = price;
        this.location = location;
        this.info = info;
        this.size = size;
    }

    public Event() {

    }
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public Set<UserEvent> getUserEvents() {
        return userEvents;
    }

    public String getName() {
        return name;
    }


    public String getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }


    public LocalTime getTimeBegin() {
        return timeBegin;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }


    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }


    public String getInfo() {
        return info;
    }

    public int getSize() {
        return size;
    }
    /**
     * Generates a random sample Event for testing purposes.
     * The event includes random values for image, name, city, date, time, location, price, info, and size.
     *
     * @return A randomly generated Event.
     */
    public static Event createSampleEvent() {
        Random random = new Random();

        // Randomly select a city name from the available cities
        String cityName = getCities()[random.nextInt(getCities().length)];
        String location;

        // Create a list of locations that contain the selected city name
        List<String> matchingLocations = new ArrayList<>();
        for (String loc : getLocations()) {
            if (loc.toLowerCase().contains(cityName.toLowerCase())) {
                matchingLocations.add(loc);
            }
        }

        // Randomly select a location from matching locations, or choose a random location if none match
        if (!matchingLocations.isEmpty()) {
            location = matchingLocations.get(random.nextInt(matchingLocations.size()));
        } else {
            location = getLocations()[random.nextInt(getLocations().length)];
        }

        // Create and return a new Event with random attributes
        return new Event(
                getImages()[random.nextInt(getImages().length)],
                getNames()[random.nextInt(getNames().length)],
                cityName,
                getRandomDate(),
                getRandomTimeBegin(),
                getRandomTimeEnd(getRandomTimeBegin()),
                getRandomPrice(),
                location,
                getInfos()[random.nextInt(getInfos().length)],
                random.nextInt(31) + 10
        );
    }


    private static LocalTime getRandomTimeBegin() {
        int minHour = 11;
        int maxHour = 20;
        Random random = new Random();
        int randomHour = random.nextInt(maxHour - minHour + 1) + minHour;
        int randomMinute = random.nextInt(6) * 10;

        return LocalTime.of(randomHour, randomMinute);
    }

    private static LocalTime getRandomTimeEnd(LocalTime timeBegin) {
        Random random = new Random();
        int beginHour = timeBegin.getHour();
        int beginMinute = timeBegin.getMinute();

        int maxHour = Math.min(beginHour + 2, 22);
        int maxMinute = beginMinute;

        if (maxHour == beginHour) {
            maxMinute = beginMinute + 30; // Add 30 minutes if the event ends in the same hour
        }

        int randomHour = random.nextInt(maxHour - beginHour + 1) + beginHour;
        int randomMinute = random.nextInt(maxMinute - beginMinute + 1) + beginMinute;

        int roundedMinute = Math.round((float) randomMinute / 10) * 10;

        return LocalTime.of(randomHour, roundedMinute);
    }



    private static Date getRandomDate() {
        Random random = new Random();
        long currentTime = System.currentTimeMillis();
        long oneYearLater = currentTime + (365L * 24L * 60L * 60L * 1000L); // One year later

        long randomTimestamp = currentTime + (long) (random.nextDouble() * (oneYearLater - currentTime));
        return new Date(randomTimestamp);
    }


    private static double getRandomPrice() {
        int minPrice = 5;
        int maxPrice = 60;

        Random random = new Random();
        int randomValue = random.nextInt((maxPrice - minPrice + 1) * 2) + minPrice * 2;

        double randomPrice = randomValue / 2.0;

        int lastDigitBeforeDecimal = (int) (randomPrice * 10) % 10;
        int priceBeforeDecimals = (int) randomPrice;

        if (priceBeforeDecimals % 10 == 9 && priceBeforeDecimals >= 9) {
            randomPrice = Math.floor(randomPrice * 100) / 100.0 + 0.99;
        } else {
            double randomDecimal = random.nextDouble();
            if (randomDecimal < 0.5) {
                randomPrice = Math.floor(randomPrice * 100) / 100.0 + 0.00;
            } else {
                randomPrice = Math.floor(randomPrice * 100) / 100.0 + 0.50;
            }
        }

        return randomPrice;
    }





    private static String[] getImages() {
        return new String[]{
                "arnhem-event.jpg",
                "rotterdam-event.webp",
                "restaurant-nederland.png",
                "cafe-nederland.png"
        };
    }

    private static String[] getNames() {
        return new String[] {
                "Kroegpraat",
                "Cafépraat",
                "Lounge Borrel"
        };
    }

    private static String[] getCities() {
        return new String[] {
                "Amsterdam",
                "Rotterdam",
                "Arnhem",
                "Zaandam",
                "Utrecht",
                "Edam",
                "Purmerend",
                "Brabant",
                "Assen",
                "Den Haag",
                "Haarlem",
                "Hilversum"
        };
    }

    private static String[] getLocations() {
        return new String[] {
                "Amsterdam Dam Restaurant",
                "Rotterdam Ahoy Lounge",
                "Villa Sansbeek Bistro",
                "Zaandam Delightful Diner",
                "Utrecht Culinary Corner",
                "Edam Cheese Haven",
                "Purmerend Oasis Bar",
                "Brabant Gastropub",
                "Assen Retreat Cafe",
                "Den Haag Coastal Eatery",
                "Haarlem Vineyard Lounge",
                "Hilversum Skyline Bistro"
        };
    }

    private static String[] getInfos() {
        return new String[] {
                "Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution.",
                "Do you share a passion for both sustainability and a good time? If so, we invite you to the Eco-Friendly Hangout in Rotterdam! Join us alongside environmental enthusiasts, including local eco-leaders, the City of Amsterdam, and fellow businesses in the hospitality and events sectors, as we delve into the importance of sustainability in our endeavors. Together, we'll embark on a quest to find innovative solutions to meet the latest Single Use Plastics regulations that came into force this year.",
                "Do you consider sustainability as important as having a good time? Then join us at the Happy Hospitality Pub Talk in Arnhem! Together with Kees Bouwhof, owner of Villa Sonsbeek and the Palatijn, the Municipality of Arnhem, and several other entrepreneurs from the hospitality and events industry, we will discuss how sustainability is at the forefront of their concerns. We will also search for the holy grail of solutions to address the new Single Use Plastics legislation that came into effect earlier this year.",
                "Do you believe in the importance of sustainability while enjoying a great atmosphere? Then don't miss out on the Sustainable Social Gathering in Arnhem! Together with industry leaders like Emily Greenfield from Green Eats, the Rotterdam City Council, and several other hospitality and event professionals, we will discuss the impact of sustainability on our businesses. Additionally, we'll be on the hunt for the ultimate solution to comply with the new Single Use Plastics legislation that has been in effect since the beginning of the year.",
                "Are you a supporter of sustainability and a good time? Then we warmly invite you to the Green Gathering in Amsterdam! Alongside experts like Sarah Groenewoud from EcoEats, the Municipality of Utrecht, and various other businesses in the food sector and events industry, we will engage in discussions about how sustainability plays a pivotal role in their endeavors. Together, we will explore innovative solutions to comply with the latest environmental regulations and combat plastic pollution."
        };
    }
    /**
     * Copies the provided Event using a copy constructor.
     * @param event The Event to be copied.
     * @return A new Event object with the same attributes as the provided event.
     */
    public static Event copyConstructor (Event event) {
        if (event == null) return null;

        return new Event(
                event.image,
                event.name,
                event.city,
                event.date,
                event.timeBegin,
                event.timeEnd,
                event.price,
                event.location,
                event.info,
                event.size
        );
    }
}
