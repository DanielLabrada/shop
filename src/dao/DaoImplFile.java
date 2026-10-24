package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import main.Shop;
import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplFile implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");
		ArrayList<Product> inventoryfromFile = new ArrayList<Product>();
		try {
			// wrap in proper classes
			FileReader fr;
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			// read first line
			String line = br.readLine();

			// process and read next line until end of file
			while (line != null) {
				// split in sections
				String[] sections = line.split(";");

				String name = "";
				double wholesalerPrice = 0.0;
				int stock = 0;

				// read each sections
				for (int i = 0; i < sections.length; i++) {
					// split data in key(0) and value(1)
					String[] data = sections[i].split(":");

					switch (i) {
					case 0:
						// format product name
						name = data[1];
						break;

					case 1:
						// format price
						wholesalerPrice = Double.parseDouble(data[1]);
						break;

					case 2:
						// format stock
						stock = Integer.parseInt(data[1]);
						break;

					default:
						break;
					}
				}
				// add product to inventory
				inventoryfromFile.add(new Product(name, new Amount(wholesalerPrice), true, stock));

				// read next line
				line = br.readLine();
			}
			fr.close();
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inventoryfromFile;
	}


	@Override
	public boolean writeInventory(ArrayList<Product> products) {
		String fileName = "inventory_" + LocalDate.now() + ".txt";
		Path filesDirectory = Path.of(System.getProperty("user.dir"), "files");
		Path inventoryFile = filesDirectory.resolve(fileName);

		try {
			Files.createDirectories(filesDirectory);
			try (var writer = Files.newBufferedWriter(inventoryFile, StandardCharsets.UTF_8)) {
				for (Product product : products) {
					writer.write(product.getId() + ";product:" + product.getName() + ";stock:" + product.getStock() + ";");
					writer.newLine();
				}
				writer.write("Total number of products: " + products.size());
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
