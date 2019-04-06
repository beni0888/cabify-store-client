package com.jbenitoc.cabifystoreclient.infrastructure.shell;

import com.jbenitoc.cabifystoreclient.application.add.AddItemToCart;
import com.jbenitoc.cabifystoreclient.application.add.AddItemToCartCommand;
import com.jbenitoc.cabifystoreclient.application.create.CreateCart;
import com.jbenitoc.cabifystoreclient.application.delete.DeleteCart;
import com.jbenitoc.cabifystoreclient.application.delete.DeleteCartQuery;
import com.jbenitoc.cabifystoreclient.application.total.GetCartTotalAmount;
import com.jbenitoc.cabifystoreclient.application.total.GetCartTotalAmountQuery;
import com.jbenitoc.cabifystoreclient.domain.model.Cart;
import com.jbenitoc.cabifystoreclient.domain.model.Price;
import com.jbenitoc.cabifystoreclient.infrastructure.command.CommandExecutor;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static com.jbenitoc.cabifystoreclient.infrastructure.util.ShellFormatter.errorMessage;
import static com.jbenitoc.cabifystoreclient.infrastructure.util.ShellFormatter.successMessage;
import static java.util.Arrays.stream;

@ShellComponent
@AllArgsConstructor
public class CheckoutShell {

    private static final String LINE_BREAK = System.lineSeparator();

    private CommandExecutor commandExecutor;
    private CreateCart createCart;
    private AddItemToCart addItemToCart;
    private GetCartTotalAmount getCartTotalAmount;
    private DeleteCart deleteCart;

    @ShellMethod(value = "Create a new shopping cart", key = {"create-cart", "create"})
    public String createCart() {
        return commandExecutor.execute(() -> {
            Cart cart = createCart.execute();
            return successMessage(String.format("Cart created: { cartId: %s }", cart.getId()));
        });
    }

    @ShellMethod(value = "Add an item to an existent cart", key = {"add-item", "add"})
    public String addItemToCart(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart to add the item to")
            String cartId,
            @ShellOption(value = {"-item", "-itemCode", "-code"}, help = "Code of the item to be added to the cart (VOUCHER|TSHIRT|MUG)")
            String itemCode,
            @ShellOption(value = {"-quantity"}, defaultValue = "1", help = "Id of the cart to add the item to")
            int quantity) {

        return commandExecutor.execute(() -> {
            addItemToCart.execute(new AddItemToCartCommand(cartId, itemCode, quantity));
            return successMessage("Item added to cart");
        });
    }

    @ShellMethod(value = "Add several items to an existent cart (just one unit per item)", key = {"add-several-items", "add-bulk"})
    public String addSeveralItemsToCart(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart to add the items to")
                    String cartId,
            @ShellOption(value = {"-items", "-itemCodes", "-codes"}, help = "Comma-separated list of item-codes to be added to the cart (VOUCHER,TSHIRT,MUG,TSHIRT)")
                    String itemCodes) {

        return commandExecutor.execute(() -> {
            int quantityPerItem = 1;
            StringBuilder outputBuffer = new StringBuilder();
            final String itemSplitter = ",";
            stream(itemCodes.split(itemSplitter)).forEach(code -> {
                try {
                    addItemToCart.execute(new AddItemToCartCommand(cartId, code, quantityPerItem));
                    outputBuffer.append(successMessage(String.format("Item added: %s", code))).append(LINE_BREAK);
                } catch (Exception e) {
                    String message = String.format("Error adding item: %s - Error: %s", code, e.getMessage());
                    outputBuffer.append(errorMessage(message)).append(LINE_BREAK);
                }
            });

            return outputBuffer.toString();
        });
    }

    @ShellMethod(value = "Return the total amount for the given cart", key = {"get-cart-total", "total"})
    public String getCartTotalAmount(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart")
            String cartId) {

        return commandExecutor.execute(() -> {
            Price total = getCartTotalAmount.execute(new GetCartTotalAmountQuery(cartId));
            return successMessage(String.format("Total amount: %s €", total));
        });
    }

    @ShellMethod(value = "Delete the given cart", key = {"delete-cart", "delete"})
    public String deleteCart(
            @ShellOption(value = {"-cart", "-cartId"}, help = "Id of the cart to be deleted")
            String cartId) {

        return commandExecutor.execute(() -> {
            deleteCart.execute(new DeleteCartQuery(cartId));
            return successMessage(String.format("Cart deleted: { cartId: %s }", cartId));
        });
    }

}
