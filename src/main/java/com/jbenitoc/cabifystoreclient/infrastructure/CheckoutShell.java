package com.jbenitoc.cabifystoreclient.infrastructure;

import com.jbenitoc.cabifystoreclient.application.add.AddItemToCart;
import com.jbenitoc.cabifystoreclient.application.add.AddItemToCartCommand;
import com.jbenitoc.cabifystoreclient.application.create.CreateCart;
import com.jbenitoc.cabifystoreclient.application.delete.DeleteCart;
import com.jbenitoc.cabifystoreclient.application.delete.DeleteCartQuery;
import com.jbenitoc.cabifystoreclient.application.total.GetCartTotalAmount;
import com.jbenitoc.cabifystoreclient.application.total.GetCartTotalAmountQuery;
import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@AllArgsConstructor
public class CheckoutShell {

    private CreateCart createCart;
    private AddItemToCart addItemToCart;
    private GetCartTotalAmount getCartTotalAmount;
    private DeleteCart deleteCart;

    @ShellMethod(value = "Create a new shopping cart", key = {"create-cart", "create"})
    public String createCart() {
        Cart cart = createCart.execute();
        return String.format("Cart created: { cartId: %s }", cart.getId());
    }

    @ShellMethod(value = "Add an item to an existent cart", key = {"add-item", "add"})
    public String addItemToCart(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart to add the item to")
            String cartId,
            @ShellOption(value = {"-item", "-itemCode", "-code"}, help = "Code of the item to be added to the cart")
            String itemCode,
            @ShellOption(value = {"-quantity"}, defaultValue = "1", help = "Id of the cart to add the item to")
            int quantity) {

        addItemToCart.execute(new AddItemToCartCommand(cartId, itemCode, quantity));
        return "Item added to cart";
    }

    @ShellMethod(value = "Return the total amount for the given cart", key = {"get-cart-total", "total"})
    public String getCartTotalAmount(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart")
            String cartId) {

        Price total = getCartTotalAmount.execute(new GetCartTotalAmountQuery(cartId));
        return String.format("Total amount: %s €", total);
    }

    @ShellMethod(value = "Delete the given cart", key = {"delete-cart", "delete"})
    public String deleteCart(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart to be deleted")
            String cartId) {

        deleteCart.execute(new DeleteCartQuery(cartId));
        return String.format("Cart deleted: { cartId: %s }", cartId);
    }


}
