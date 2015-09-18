package dice.eu.fleximonkey;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineParameters {
	public static void main(String[] args) {

		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		Option vmShutdown = new Option("r", "randomVM", true,
				"Shutdown random VM within FCO.");
		// Set option r to take maximum of 4 arguments
		vmShutdown.setArgs(4);
		vmShutdown.setArgName("cloudusername, cloudpassword, vmpassword, host");
		options.addOption(vmShutdown);
		
		Option vmStressCPU = new Option("s", "stresscpu", true,
				"Stress VM CPU");
		// Set option s to take maximum of 4 arguments
		vmStressCPU.setArgs(4);
		vmStressCPU.setArgName("cores, stresstime, vmpassword, host");
		options.addOption(vmStressCPU);
		
		Option vmStressMem = new Option("m", "stressmem", true,
				"Stress VM Memory");
		// Set option m to take maximum of 5 arguments
		vmStressMem.setArgs(5);
		vmStressMem.setArgName("cores, stresstime, vmpassword, host,memorytesterloops");
		options.addOption(vmStressMem);
		
		options.addOption("f", "file", true, "Load from properties file");
		options.addOption("h", "help", false, "Shows help");

		try {
			CommandLine commandLine = parser.parse(options, args);

			if (commandLine.hasOption("r")) {

				String[] argument = commandLine.getOptionValues("r");
				String cloudusername = argument[0];
				String cloudpassword = argument[1];
				String cloudUUID = argument[2];
				String cloudapiurl = argument[3];
				// Logger to be added
				FCOListVMs listvms = new FCOListVMs();
				listvms.listvms(cloudusername, cloudpassword, cloudapiurl,
						cloudUUID);
				System.out.println("Executing stop random VM");

			}

			if (commandLine.hasOption("f")) {
				ReadConfig readconfigfile = new ReadConfig();
				readconfigfile.readconfig();
			}
			if (commandLine.hasOption("s")) {
				String[] argument = commandLine.getOptionValues("s");
				String cores = argument[0];
				String stresstime = argument[1];
				String vmpassword = argument[2];
				String host = argument[3];
				System.out.println("Executing CPU stress on VM");
				VMcpuStress cpustress = new VMcpuStress();
				cpustress.stresscpu(cores,stresstime,vmpassword,host);
			}
			if (commandLine.hasOption("m")) {
				String[] argument = commandLine.getOptionValues("s");
				String cores = argument[0];
				String stresstime = argument[1];
				String vmpassword = argument[2];
				String host = argument[3];
				String memorytesterloops = argument[4];
				System.out.println("Executing CPU stress on VM");
				VMmemoryStress vmmemstress = new VMmemoryStress();
				vmmemstress.stressmemory(cores,stresstime,vmpassword,host,memorytesterloops);	
			}

			if (commandLine.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("CommandLineParameters", options);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
