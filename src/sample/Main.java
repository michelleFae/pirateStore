package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.BackgroundRepeat;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

/** Main - consists of GUI components and
 * their functionality. As a personal challenge,
 * I built the GUI from scratch (without using Java FML
 * and scene builder! :) )
 *  @author Michelle Fae D'Souza
 */
public class Main extends Application {

    /** Buttons for exiting the application and returning to homepage. */
    private Button _exitButton, _homeButton;

    /**Scenes for pay, shop, home, and item. */
    private Scene _payScene, _shopScene, _homeScene, _itemScene;

    /** Main stage for application. */
    private Stage _mainStage;

    /** Cart of customer's items. */
    private Cart _cart = new Cart();

    /** Hashmap of items to their labels. */
    private HashMap<Item, Label> _itemLabels = new HashMap<>();

    /** height of window. */
    private final int height = 750;

    /** width of window. */
    private final int width = 800;

    /** Labels containing total prices. */
    private Label _homePriceLabel, _shopPriceLabel, _payPriceLabel;

    /** Labels containing total quantities. */
    private Label _homeQuantLabel, _shopQuantLabel, _payQuantLabel;

    /** Labels containing post discount savings. */
    private Label _dvdDiscountLabel, _bluDiscountLabel, _bulkDiscountLabel;

    /** home layout. */
    private Group _homeLayout;

    /** Shop and pay page layouts. */
    private VBox _shopLayout, _payLayout;

    /** Combo box of items in shop. */
    private ComboBox<String> _itemsCombo;

    /** Spacing between components in the layout. */
    private static final int SPACING = 30;

    /** checks if items have not been put in cart yet. Ensures no
     * duplicate items are added in shopping cart when shopPage()
     * is called again.
     *
     * Java's combobox has a bug - an option can't be selected
     * twice in a row. In order to overcome this bug, I created
     * an entirely new combo box (with same values as the
     * previous one) after a comboBox option is selected.
     * This permits me to set a prompt again. _firstTime prevents
     * me from creating items again. New Items will only
     * be created when _firstTime is true.
     */
    private boolean _firstTime = true;

    @Override
    public void start(Stage primaryStage) {
        try {
            _mainStage = primaryStage;
            homePage();
            payPage();
            shopPage();
        } catch (Exception e) {

        }
    }

    /** Sets up shop scene. */
    private void shopPage() {
        final int bigFont = 18;
        final int smallFont = 15;
        final int spacing = 25;
        BorderPane pane = new BorderPane();
        BackgroundImage myBI = new BackgroundImage(new Image(
                "https://irp-cdn.multiscreensite.com/c3c96d3d/"
                + "dms3rep/multi/mobile/PirateShipOnSea-1200x1040.gif",
                width, height, false,
                true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));
        _shopLayout = new VBox(spacing);
        pane.setCenter(_shopLayout);
        Label shopLabel1 = new Label("Click on an item you"
                + " want to amend in your cart.");
        design(shopLabel1, 0, 0, Color.WHITESMOKE, bigFont);
        _shopPriceLabel = new Label("Total cost: $"
                + String.format("%.2f", _cart.getPrice()));
        design(_shopPriceLabel, 0, 0, Color.WHITESMOKE, smallFont);
        _shopQuantLabel = new Label("Total quantity: " + _cart.getQuantity());
        design(_shopQuantLabel, 0, 0, Color.WHITESMOKE, smallFont);
        _itemsCombo = new ComboBox<>();
        _itemsCombo.getItems().addAll(
                "Star Wars Episode IV DVD ($20)",
                "Star Wars Episode V DVD ($20)",
                "Star Wars Episode VI DVD ($20)",
                "Star Wars Episode IV Blu-Ray ($25)",
                "Star Wars Episode V Blu-Ray ($25)",
                "Star Wars Episode VI Blu-Ray ($25)"
        );
        _itemsCombo.setPromptText("Which item would you like to remove/add?");
        _itemsCombo.setOnAction(e -> itemPage(_itemsCombo.getValue()));
        if (_firstTime) {
            itemCreator();
            _firstTime = false;
        }

        _exitButton = buttonCreator("exit :) ");
        _exitButton.setOnAction(event -> {
                System.exit(0);
            });
        _homeButton = buttonCreator("Done :) - go back home");
        _homeButton.setOnAction(event -> {
                updateTotalLabels(_homePriceLabel, _homeQuantLabel);
                displayDiscounts(_homeLayout);
                _mainStage.setScene(_homeScene);
            });
        _shopLayout.getChildren().addAll(shopLabel1, _itemsCombo);
        displayItems(_shopLayout);
        _shopLayout.getChildren().addAll(_shopPriceLabel,
                _shopQuantLabel, _homeButton, _exitButton);
        _shopScene = new Scene(pane, width, height);
    }

    /** Creates items to sell in store.
     * Please update this if you want to sell more/less items. */
    private void itemCreator() {
        final int dvdCost = 20;
        final int bluCost = 25;

        Item item1 = new Item("Star Wars Episode IV DVD ($20)", dvdCost, "DVD");
        Label itemLabel1 = new Label("Quantity of "
                + item1.getName() + " : " + _cart.getQuantity(item1));
        _itemLabels.put(item1, itemLabel1);

        Item item2 = new Item("Star Wars Episode V DVD ($20)", dvdCost, "DVD");
        Label itemLabel2 = new Label("Quantity of "
                + item2.getName() + " : " + _cart.getQuantity(item2));
        _itemLabels.put(item2, itemLabel2);

        Item item3 = new Item("Star Wars Episode VI DVD ($20)", dvdCost, "DVD");
        Label itemLabel3 = new Label("Quantity  of "
                + item3.getName() + " : " + _cart.getQuantity(item3));
        _itemLabels.put(item3, itemLabel3);

        Item item4 = new Item("Star Wars Episode IV Blu-Ray ($25)",
                bluCost, "Blu-Ray");
        Label itemLabel4 = new Label("Quantity  of "
                + item4.getName() + " : " + _cart.getQuantity(item4));
        _itemLabels.put(item4, itemLabel4);

        Item item5 = new Item("Star Wars Episode V Blu-Ray ($25)",
                bluCost, "Blu-Ray");
        Label itemLabel5 = new Label("Quantity  of "
                + item5.getName() + " : " + _cart.getQuantity(item5));
        _itemLabels.put(item5, itemLabel5);

        Item item6 = new Item("Star Wars Episode VI Blu-Ray ($25)",
                bluCost, "Blu-Ray");
        Label itemLabel6 = new Label("Quantity  of "
                + item6.getName() + " : " + _cart.getQuantity(item6));
        _itemLabels.put(item6, itemLabel6);
    }

    /** @param layout is the VBox to which the labels are added.
     *                 displays items only if quantity in cart > 0. */
    private void displayItems(VBox layout) {
        for (Item i : _itemLabels.keySet()) {
            layout.getChildren().remove(_itemLabels.get(i));
            if (_cart.getQuantity(i) != 0) {
                Label label = _itemLabels.get(i);
                design(label, 0, 0, Color.GOLD, 16);
                label.setStyle("-fx-font: 15 monaco;");
                layout.getChildren().add(_itemLabels.get(i));
            }

        }
    }

    /** Sets up pay scene. */
    private void payPage() {
        final int smallFont = 18;
        final int bigFont = 24;
        BorderPane pane = new BorderPane();
        BackgroundImage myBI = new BackgroundImage(new Image(
               "https://steamusercontent-a.akamaihd.net/"
               + "ugc/901148685424957154/47A7A30B2657CE35D7E30"
                       + "41D689E55BB1332212A/", width,
               height, false, true),
               BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
               BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));

        _payLayout = new VBox(SPACING);
        pane.setCenter(_payLayout);

        Label payLabel1 = new Label("Thank you for shopping at"
                + " our Pirate(d) store!");
        design(payLabel1, 0, 0, Color.WHITESMOKE, bigFont);
        payLabel1.setStyle("-fx-background-color:rgba(85, 0, 68,0.7)");

        _payPriceLabel = new Label("Total cost: $"
                + Double.toString(_cart.getPrice()));
        design(_payPriceLabel, 0, 0, Color.WHITESMOKE, bigFont);
        _payPriceLabel.setStyle("-fx-background-color:rgba(0, 3, 68,0.7)");

        _payQuantLabel = new Label("Total quantity: " + _cart.getQuantity());
        design(_payQuantLabel, 0, 0, Color.WHITESMOKE, bigFont);
        _payQuantLabel.setStyle("-fx-background-color:rgba(0, 69, 68,0.7)");

        Label payLabel2 = new Label("** We are not to be held"
                + " accountable in the likely");
        design(payLabel2, 0, 0, Color.WHITESMOKE, smallFont);
        payLabel2.setStyle("-fx-background-color:rgba(65, 225, 68,0.7)");

        Label payLabel3 = new Label(" event that the Government"
                + " arrests you for buying pirated items.");
        design(payLabel3, 0, 0, Color.WHITESMOKE, smallFont);
        payLabel3.setStyle("-fx-background-color:rgba(65, 225, 68,0.7)");

        displayDiscounts(_payLayout);

        _payLayout.getChildren().addAll(payLabel1, _payPriceLabel,
                _payQuantLabel, payLabel2, payLabel3);

        _payScene = new Scene(pane, width, height);

    }

    /** @param layout is the Group onto which we put our discount labels.
     *  Displays any savings from discounts. - for groups */
    private void displayDiscounts(Group layout) {
        if (layout.getChildren().contains(_dvdDiscountLabel)) {
            layout.getChildren().remove(_dvdDiscountLabel);
        }

        if (layout.getChildren().contains(_bluDiscountLabel)) {
            layout.getChildren().remove(_bluDiscountLabel);
        }

        if (layout.getChildren().contains(_bulkDiscountLabel)) {
            layout.getChildren().remove(_bulkDiscountLabel);
        }

        final int yPos0 = 260;
        final int changeYPos = 40;
        final int fontSize = 20;
        if (_cart.getDVDDiscount() > 0.0) {
            _dvdDiscountLabel = new Label("Money saved by buying"
                    + " all types of DVD's: " + _cart.getDVDDiscount());
            design(_dvdDiscountLabel, 0, yPos0, Color.RED, fontSize);
            _dvdDiscountLabel.setStyle("-fx-background-color:rgba"
                    + "(85, 0, 68,0.7)");

            layout.getChildren().add(_dvdDiscountLabel);
        }

        if (_cart.getBluDiscount() > 0.0) {
            _bluDiscountLabel = new Label("Money saved by buying"
                    + " all types of Blu-Ray's: " + _cart.getBluDiscount());
            design(_bluDiscountLabel, 0, changeYPos + yPos0,
                    Color.RED, fontSize);
            _bluDiscountLabel.setStyle("-fx-background-color:rgba"
                    + "(85, 0, 68,0.7)");
            layout.getChildren().add(_bluDiscountLabel);
        }

        if (_cart.getBulkDiscount() > 0.0) {
            _bulkDiscountLabel = new Label("Money saved by buying 100+ items: "
                    + String.format("%.2f", _cart.getBulkDiscount()));
            design(_bulkDiscountLabel, 0, changeYPos * 2
                    + yPos0, Color.BLACK, fontSize);
            _bulkDiscountLabel.setStyle("-fx-background-color:rgba"
                    + "(240,128,128,0.7)");
            layout.getChildren().add(_bulkDiscountLabel);
        }
    }

    /** @param layout is the VBox onto which we put our discount labels.
     *  displays any savings from discounts. - vbox layout */
    private void displayDiscounts(VBox layout) {
        if (layout.getChildren().contains(_dvdDiscountLabel)) {
            layout.getChildren().remove(_dvdDiscountLabel);
        }

        if (layout.getChildren().contains(_bluDiscountLabel)) {
            layout.getChildren().remove(_bluDiscountLabel);
        }

        if (layout.getChildren().contains(_bulkDiscountLabel)) {
            layout.getChildren().remove(_bulkDiscountLabel);
        }

        final int fontSize = 20;
        if (_cart.getDVDDiscount() > 0.0) {
            _dvdDiscountLabel = new Label("Savings by buying all"
                    + " types of DVD's: " + _cart.getDVDDiscount());
            design(_dvdDiscountLabel, 0, 0, Color.RED, fontSize);
            _dvdDiscountLabel.setStyle("-fx-background-color:rgba"
                    + "(85, 0, 68,0.7)");
            layout.getChildren().add(_dvdDiscountLabel);
        }

        if (_cart.getBluDiscount() > 0.0) {
            _bluDiscountLabel = new Label("Savings by buying all types "
                    + " of Blu-Ray's: " + _cart.getBluDiscount());
            design(_bluDiscountLabel, 0, 0, Color.RED,
                    fontSize);
            _bluDiscountLabel.setStyle("-fx-background-color:rgba"
                    + "(85, 0, 68,0.7)");
            layout.getChildren().add(_bluDiscountLabel);
        }

        if (_cart.getBulkDiscount() > 0.0) {
            _bulkDiscountLabel = new Label("Discount for buying 100+ items: "
                    + String.format("%.2f", _cart.getBulkDiscount()));
            design(_bulkDiscountLabel, 0, 0, Color.BLACK, fontSize);
            _bulkDiscountLabel.setStyle("-fx-background-color:"
                    + "rgba(240,128,128,0.7)");
            layout.getChildren().add(_bulkDiscountLabel);
        }
    }

    /** @param item is a thing for sale.
     *  Update labels for each item in shop scene. */
    private void updateLabel(Item item) {
        if (_itemLabels.containsKey(item)) {
            _itemLabels.get(item).setText("Quantity of " + item.getName()
                    + " in cart: " + _cart.getQuantity(item));
        }
    }

    /** @param labelP is a total price label.
     * @param labelQ is a total quantity label. */
    private void updateTotalLabels(Label labelP, Label labelQ) {
        labelP.setText("Total cost: $" + String.format("%.2f",
                _cart.getPrice()));
        labelQ.setText("Total quantity: " + _cart.getQuantity());
    }

    /** home page setup. */
    private void homePage() {
        final int bigFont = 30;
        final int smallfont = 14;
        final int xPos0 = 35;
        final int yPos0 = 10;
        final int xPos1 = 5;
        final int yPos1 = 50;
        final int yPosChange = 30;
        final int xPos5 = 20;
        final int xPos6 = 260;
        final int xPos7 = 410;
        _mainStage.setTitle("Pirated Shop");
        Label homeLabel1 = new Label("Welcome to the Pirate(d) Shop!");
        design(homeLabel1, xPos0, yPos0, Color.DEEPSKYBLUE, bigFont);
        Label discountLabel1 = new Label("You earn a 10% discount"
                + " on each DVD if you order all types of DVDs!");
        design(discountLabel1, xPos1, yPos1, Color.LIGHTBLUE, smallfont);
        Label discountLabel2 = new Label("You earn a 15% discount"
                + " on each Blu-Ray if you order all types of Blu-Rays!");
        design(discountLabel2, xPos1, yPos1 + yPosChange,
                Color.LIGHTBLUE, smallfont);
        Label discountLabel3 = new Label("You earn a 5% overall"
                + " discount if you order 100 or more items!");
        design(discountLabel3, xPos1, yPos1
                        + (yPosChange * 2), Color.LIGHTBLUE, smallfont);
        _homePriceLabel = new Label("Total cost: $" + _cart.getPrice());
        design(_homePriceLabel, xPos1, yPos1 + (yPosChange * 3),
                Color.LIGHTBLUE, smallfont);
        _homeQuantLabel = new Label("Total quantity: " + _cart.getQuantity());
        design(_homeQuantLabel, xPos1, yPos1 + (yPosChange * 4),
                Color.LIGHTBLUE, smallfont);
        Button shopButton = buttonCreator("Shop add/remove items");
        shopButton.setOnAction(event -> _mainStage.setScene(_shopScene));
        design(shopButton, xPos5, yPos1 + (yPosChange * 5), Color.DEEPSKYBLUE,
                smallfont, "-fx-background-color: #000042;");
        Button payButton = buttonCreator("Checkout");
        design(payButton, xPos6, yPos1 + (yPosChange * 5),
                Color.DEEPSKYBLUE, smallfont, "-fx-background-color: #000042;");
        payButton.setOnAction(event -> {
                updateTotalLabels(_payPriceLabel, _payQuantLabel);
                displayDiscounts(_payLayout);
                _mainStage.setScene(_payScene);
            });
        _exitButton = buttonCreator("exit :( ");
        design(_exitButton, xPos7, yPos1 + (yPosChange * 5), Color.DEEPSKYBLUE,
                smallfont, "-fx-background-color: #000042;");
        _exitButton.setOnAction(event -> {
                System.exit(0);
            });
        _homeLayout = new Group();
        _homeLayout.getChildren().addAll(homeLabel1, discountLabel1,
                discountLabel2, discountLabel3, _homePriceLabel,
                _homeQuantLabel, shopButton, payButton, _exitButton);
        _homeScene = new Scene(_homeLayout, width, height,
                Color.BLACK);
        _mainStage.setScene(_homeScene);
        _mainStage.show();
    }

    /** @param elem is the button being designed.
     *  @param xPos is the x coordinate position of the button.
     *  @param yPos is the y coordinate position of the button.
     *  @param color is the color of the font in the button.
     *  @param fontSize is the font size of button test.
     *                          sets up design for labels. */
    private void design(Label elem, int xPos, int yPos, Color color,
                        int fontSize) {
        elem.setLayoutY(yPos);
        elem.setLayoutX(xPos);
        elem.setTextFill(color);
        elem.setFont(Font.font(fontSize));

    }

    /** @param elem is the button being designed.
     *  @param xPos is the x coordinate position of the button.
     *  @param yPos is the y coordinate position of the button.
     *  @param fontColor is the color of the font in the button.
     *  @param fontSize is the font size of button test.
     *  @param background is the button background color.
     *                          sets up design for buttons. */
    private void design(Button elem, int xPos, int yPos,
                        Color fontColor, int fontSize, String background) {
        elem.setLayoutY(yPos);
        elem.setLayoutX(xPos);
        elem.setTextFill(fontColor);
        elem.setStyle(background);
        elem.setFont(Font.font(fontSize));
        final int shadowRadius = 20;
        elem.setEffect(new DropShadow(shadowRadius, Color.LIGHTBLUE));
    }


    /** @param itemName if used to find item, set up item
     *  quantity selection page and add/remove page. */
    private void itemPage(String itemName) {
        final int fontSize = 14;

        BorderPane pane = new BorderPane();
        BackgroundImage myBI = new BackgroundImage(new Image(
                "https://steamusercontent-a.akamaihd.net/ugc/9"
                + "51835098531958792/33B0576EF127E1A08676B79A78BA72DB2B6C136D/",
                width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));

        Item item = Item.getItemFromName(itemName);
        Label itemLabel1 = new Label("Please specify quantity of");
        design(itemLabel1, 0, 0, Color.WHITESMOKE, fontSize);
        Label itemLabel2 =  new Label(item.getName()
                + " that you would like to buy.");
        design(itemLabel2, 0, 0, Color.WHITESMOKE, fontSize);
        Label itemLabel3 = new Label("To remove an item, set quantity to"
                + " zero.");
        design(itemLabel3, 0, 0, Color.WHITESMOKE, fontSize);
        itemLabel3.setStyle("-fx-background-color:rgba(3,1,30,0.5)");
        TextField numberField = new TextField();
        numberField.setText(_cart.getQuantity(item) + "");
        numberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        Button doneButton = buttonCreator("Done!");
        doneButton.setOnAction(event -> {
                String quantity = numberField.getText();
                if (quantity.compareTo("") == 0) {
                    quantity = "0";
                }
                _cart.changeQuantity(item, Integer.parseInt(quantity));
                updateLabel(item);
                displayItems(_shopLayout);
                updateTotalLabels(_shopPriceLabel, _shopQuantLabel);

                _itemsCombo.setPromptText("Please select item to remove/add");
                shopPage();
                displayDiscounts(_shopLayout);
                _mainStage.setScene(_shopScene);
            });
        VBox itemLayout = new VBox(SPACING);
        itemLayout.getChildren().addAll(itemLabel1, itemLabel2,
                itemLabel3, numberField, doneButton, _exitButton);
        pane.setCenter(itemLayout);
        _itemScene = new Scene(pane, width, height);
        _mainStage.setScene(_itemScene);
    }

    /** @param text is text displayed on the button
     *               which is created and returned.
     *  @return temp. */
    private Button buttonCreator(String text) {
        Button temp = new Button();
        temp.setText(text);
        return temp;
    }

    /** @param args for main. */
    public static void main(String[] args) {
        launch(args);
    }

}
