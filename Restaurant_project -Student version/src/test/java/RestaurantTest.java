import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setupRestaurantObject(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime mockedTime = LocalTime.parse("10:31:00");
        assertTrue(checkRestaurantOperationTime(mockedTime));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime mockedTime = LocalTime.parse("22:00:00");
        assertFalse(checkRestaurantOperationTime(mockedTime));
    }

    public boolean checkRestaurantOperationTime(LocalTime mockedTime){
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(mockedTime);
        return restaurantSpy.isRestaurantOpen();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<Order Value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //it should accept list of strings
    //method should return order value.
    //if list is empty return 0


    @Test
    public void if_list_is_empty_oder_value_returned_zero(){
        List itemsSelected = new ArrayList();
        double ordervalue = 0.0;

        ordervalue = restaurant.calculateOrderValue(itemsSelected);
        assertThat(ordervalue,equalTo(0.0));
    }

    @Test
    public void after_finding_item_in_menu_order_value_should_be_returned(){
        List itemsSelected = new ArrayList();
        itemsSelected.add("Sweet corn soup");
        itemsSelected.add("Vegetable lasagne");
        itemsSelected.add("Sweet corn soup");

        double ordervalue = 0.0;

        ordervalue = restaurant.calculateOrderValue(itemsSelected);
        assertThat(ordervalue,greaterThan(0.0));
    }

    @Test
    public void if_item_exists_in_menu_return_item(){
        Item item = restaurant.findItemByName("Sweet corn soup");
        assertNotNull(item);
    }

    @Test
    public void if_item_do_not_exists_return_null(){
        Item item = restaurant.findItemByName("Corn soup");
        assertNull(item);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<Order Value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}