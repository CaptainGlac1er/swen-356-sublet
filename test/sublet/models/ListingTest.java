package sublet.models;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Random;

public class ListingTest {
    public Listing listing;
    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
        listing = new Listing((new Random()).nextLong(), Users.newUser(1234, "Test", "Name", "TU", "qwerty", "td@rit.edu", LocalDate.now(), LocalDate.now()), "hi", "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, false);
    }

    @Test
    void SetListingDesc() {
        String desc = "new text";
        listing.setDesc(desc);
        assert (listing.getDesc().equals(desc));
    }
    @Test
    void SetListingPayment(){
        Listing.PaymentFrequencyOptions test = Listing.PaymentFrequencyOptions.SEMESTERLY;
        listing.setPayment(test);
        assert (listing.getPayment_frequency() == test);
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
