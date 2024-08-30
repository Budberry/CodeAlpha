import java.util.*;

class Stock {
    String symbol;
    double price;
    int quantity;
    List<Double> historicalPrices;

    public Stock(String symbol, double price, int quantity) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.historicalPrices = new ArrayList<>();
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
        this.historicalPrices.add(newPrice);
    }
}

class StockMarket {
    Map<String, Stock> stocks;

    public StockMarket() {
        this.stocks = new HashMap<>();
        // Initialize the market with some stocks
        stocks.put("AAPL", new Stock("AAPL", 180.0, 1000));
        stocks.put("GOOG", new Stock("GOOG", 2500.0, 500));
        stocks.put("AMZN", new Stock("AMZN", 3000.0, 200));
    }

    public void updateMarketData() {
        for (Stock stock : stocks.values()) {
            double randomChange = Math.random() * 0.05 - 0.025; // Random change between -2.5% and 2.5%
            stock.updatePrice(stock.price * (1 + randomChange));
        }
    }

    public void buyStock(String symbol, int quantity, Portfolio portfolio) {
        Stock stock = stocks.get(symbol);
        if (stock == null || quantity <= 0 || portfolio.getBalance() < stock.price * quantity) {
            System.out.println("Invalid purchase. Check stock availability, quantity, and funds.");
            return;
        }

        portfolio.buyStock(stock, quantity);
        stock.quantity -= quantity;
    }

    public void sellStock(String symbol, int quantity, Portfolio portfolio) {
        Stock stock = stocks.get(symbol);
        if (stock == null || quantity <= 0 || portfolio.getHoldings().getOrDefault(stock, 0) < quantity) {
            System.out.println("Invalid sale. Check stock availability and quantity.");
            return;
        }

        portfolio.sellStock(stock, quantity);
        stock.quantity += quantity;
    }
}

class Portfolio {
    Map<Stock, Integer> holdings;
    double balance;

    public Portfolio(double initialBalance) {
        this.holdings = new HashMap<>();
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public Map<Stock, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(Stock stock, int quantity) {
        holdings.put(stock, holdings.getOrDefault(stock, 0) + quantity);
        balance -= stock.price * quantity;
    }

    public void sellStock(Stock stock, int quantity) {
        holdings.put(stock, holdings.getOrDefault(stock, 0) - quantity);
        balance += stock.price * quantity;
    }

    public double getNetWorth() {
        double netWorth = balance;
        for (Map.Entry<Stock, Integer> entry : holdings.entrySet()) {
            netWorth += entry.getKey().price * entry.getValue();
        }
        return netWorth;
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        StockMarket market = new StockMarket();
        Portfolio userPortfolio = new Portfolio(10000.0);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. View market data");
            System.out.println("2. Buy stock");
            System.out.println("3. Sell stock");
            System.out.println("4. View portfolio");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    market.updateMarketData();
                    System.out.println("Market data:");
                    for (Stock stock : market.stocks.values()) {
                        System.out.println(stock.symbol + ": $" + stock.price);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    market.buyStock(buySymbol, buyQuantity, userPortfolio);
                    break;
                case 3:
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    market.sellStock(sellSymbol, sellQuantity, userPortfolio);
                    break;
                case 4:
                    System.out.println("Your portfolio:");
                    for (Map.Entry<Stock, Integer> entry : userPortfolio.getHoldings().entrySet()) {
                        System.out.println(entry.getKey().symbol + ": " + entry.getValue());
                    }
                    System.out.println("Net worth: $" + userPortfolio.getNetWorth());
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}