package web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WebScraping {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scan = new Scanner(System.in);
        List<Product> produts = new ArrayList<Product>();

        System.out.print("Digite o produto: ");
        String product = scan.nextLine();

        System.out.print("Digite o preço minimo: ");
        double priceMin = scan.nextDouble();

        final String url =
                "https://www.kabum.com.br/";

        String search = url + "busca?query=" + URLEncoder.encode(product, StandardCharsets.UTF_8);

        try {
            final Document document = Jsoup.connect(search).get();
            for (Element row : document.select(
                    "div.productCard")) {

                double price = Double.parseDouble(row.select("span.priceCard").text().substring(2)
                        .replace(".", "").replace(",", ".")
                );
                produts.add(
                        new Product(
                                row.select("h2.nameCard").text(),
                                price
                        ));
            }
            System.out.println(produts.stream().filter(x-> x.getPrice() < priceMin).collect(Collectors.toSet()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}