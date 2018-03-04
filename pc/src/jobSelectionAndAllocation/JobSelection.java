package jobSelectionAndAllocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class JobSelection {
	
	private BufferedReader brRewards;
	private BufferedReader brItems;
	private BufferedReader brLocation;
	private ArrayList items = new ArrayList<Item>();
	private ArrayList allJobs = new ArrayList<Job>();
	
	public JobSelection(String rewardLocation, String itemsLocation, String jobsLocation) {
		try {
			brRewards = new BufferedReader(new FileReader(rewardLocation));
			brItems = new BufferedReader(new FileReader(itemsLocation));
			brLocation = new BufferedReader(new FileReader(jobsLocation));
			String item;
			while(item = brItems.readLine() != null) {
				String[] itemInfos = item.split(",");
				items.add(new Item(itemInfos[0], itemsInfos[1], itemInfos[2]));
			}
			while(item = brRewards.readLine() != null) {
				String[] rewardsInfos = item.split(",");
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Did not pass filename");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Job nextJob() {
		
	}

}
