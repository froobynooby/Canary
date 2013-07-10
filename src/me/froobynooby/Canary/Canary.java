package me.froobynooby.Canary;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import me.froobynooby.Canary.Commands.BannedblocksCommand;
import me.froobynooby.Canary.Commands.CanaryCommand;
import me.froobynooby.Canary.Commands.SwearfilterCommand;
import me.froobynooby.Canary.Commands.TogglealertsCommand;
import me.froobynooby.Canary.Listeners.BlockListener;
import me.froobynooby.Canary.Listeners.ChatListener;
import me.froobynooby.Canary.Listeners.EntityListener;
import me.froobynooby.Canary.Listeners.FireListener;
import me.froobynooby.Canary.Listeners.GriefListener;
import me.froobynooby.Canary.Listeners.SignListener;
import me.froobynooby.Canary.Listeners.SpamListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Canary extends JavaPlugin{
  public Logger l = Logger.getLogger("Minecraft");
	public String prefix = ChatColor.YELLOW + "Canary:" + ChatColor.WHITE + " ";
	String noperm = ChatColor.RED + "You don't have permission to use this command.";
	
	 
	public YamlConfiguration alerts = null;
	public File alertsfile = null;
	
	public YamlConfiguration blocks = null;
	public File blocksfile = null;
	
	public YamlConfiguration swears = null;
	public File swearsfile = null;
	
	public YamlConfiguration egrief = null;
	public File egrieffile = null;
	
	public YamlConfiguration log = null;
	public File logfile = null;
	
	public YamlConfiguration fire = null;
	public File firefile = null;
	
	public BlockListener bl = new BlockListener(this);
	public ChatListener cl = new ChatListener(this);
	public GriefListener gl = new GriefListener(this);
	public EntityListener el = new EntityListener(this);
	public SignListener sl = new SignListener(this);
	public SpamListener spl = new SpamListener(this);
	public FireListener fl = new FireListener(this);
	
	public void Alert(String msg)
	{
		ArrayList<String> dna = new ArrayList<String>();
		dna.clear();
		dna.addAll(this.getAlerts().getStringList("Do not alert"));
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(!dna.contains(p.getName().toLowerCase()))
			{
			if(p.hasPermission("canary.alert"))
			{
				p.sendMessage(prefix + msg);
			}
			}
		}
		
		l.info("[Canary] " + msg);
	}
	
	
	
	@Override
	public void onEnable()
	{
	l.info("[Canary] Enabling...");
		
	regCommands();
	regEvents();
	
	this.reloadBlocks();
	this.saveBlocks();
	
	this.reloadEgrief();
	this.saveEgrief();
	
	this.reloadFire();
	this.saveFire();
	
	if(new File(this.getDataFolder(), "config.yml").exists())
	{
		
	}
	else
	{
		this.saveDefaultConfig();
	}
	
	this.reloadConfig();
	this.saveConfig();
	
	
	l.info("[Canary] Enabled.");
	}
	
	
	@Override
	public void onDisable()
	{
		
	}

	
	
	public void regCommands()
	{
		this.getCommand("canary").setExecutor(new CanaryCommand(this));
		this.getCommand("canary").setPermission("canary.canary");
		this.getCommand("canary").setPermissionMessage(noperm);
		
		this.getCommand("togglealerts").setExecutor(new TogglealertsCommand(this));
		this.getCommand("togglealerts").setPermission("canary.togglealerts");
		this.getCommand("togglealerts").setPermissionMessage(noperm);
		
		this.getCommand("bannedblocks").setExecutor(new BannedblocksCommand(this));
		this.getCommand("bannedblocks").setPermission("canary.bannedblocks");
		this.getCommand("bannedblocks").setPermissionMessage(noperm);
		
		this.getCommand("swearfilter").setExecutor(new SwearfilterCommand(this));
		this.getCommand("swearfilter").setPermission("canary.swearfilter");
		this.getCommand("swearfilter").setPermissionMessage(noperm);
		
	}
	
	public void regEvents()
	{
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvents(this.bl, this);
		pm.registerEvents(this.cl, this);
		pm.registerEvents(this.gl, this);
		pm.registerEvents(this.el, this);
		pm.registerEvents(this.sl, this);
		pm.registerEvents(this.spl, this);
		pm.registerEvents(this.fl, this);
	}
	
	
	
	public void reloadAlerts()
	{

		alertsfile = new File(this.getDataFolder(), "alertstoggle.yml");
		alerts = YamlConfiguration.loadConfiguration(alertsfile);
		
		
	}
	
	
	public YamlConfiguration getAlerts()
	{
		if(alerts == null)
		{
			reloadAlerts();
		}
		
		return alerts;
	}
	
	
	public void saveAlerts()
	{
		if(alerts == null)
		{
			reloadAlerts();
		}
		try 
		{
			alerts.save(alertsfile);
		} catch (Exception ex)
		{

		}
	}
	
	
	
	
	
	
	
	
	
	public void reloadBlocks()
	{
		if(!new File(this.getDataFolder(), "Banned Blocks.yml").exists())
		{
			blocksfile = new File(this.getDataFolder(), "Banned Blocks.yml");
			InputStream defaultblocks = this.getResource("Banned Blocks.yml");
			blocks = YamlConfiguration.loadConfiguration(defaultblocks);
			try 
			{
				blocks.save(blocksfile);
			} catch (Exception ex)
			{

			}
		}
		else
		{

		blocksfile = new File(this.getDataFolder(), "Banned Blocks.yml");
		blocks = YamlConfiguration.loadConfiguration(blocksfile);
		}
		
		
	}
	
	
	public YamlConfiguration getBlocks()
	{
		if(blocks == null)
		{
			reloadBlocks();
		}
		
		return blocks;
	}
	
	
	public void saveBlocks()
	{
		if(blocks == null)
		{
			reloadBlocks();
		}
		try 
		{
			blocks.save(blocksfile);
		} catch (Exception ex)
		{

		}
	}
	
	
	
	
	
	
	
	
	
	
	public void reloadSwears()
	{
		if(!new File(this.getDataFolder(), "Swear List.yml").exists())
		{
			swearsfile = new File(this.getDataFolder(), "Swear List.yml");
			InputStream defaultblocks = this.getResource("Swear List.yml");
			swears = YamlConfiguration.loadConfiguration(defaultblocks);
			try 
			{
				swears.save(swearsfile);
			} catch (Exception ex)
			{

			}
		}
		else
		{

		swearsfile = new File(this.getDataFolder(), "Swear List.yml");
		swears = YamlConfiguration.loadConfiguration(swearsfile);
		}
		
		
	}
	
	
	public YamlConfiguration getSwears()
	{
		if(swears == null)
		{
			reloadSwears();
		}
		
		return swears;
	}
	
	
	public void saveSwears()
	{
		if(swears == null)
		{
			reloadSwears();
		}
		try 
		{
			swears.save(swearsfile);
		} catch (Exception ex)
		{

		}
	}
	
	
	
	
	
	
	
	
	public void reloadEgrief()
	{
		if(!new File(this.getDataFolder(), "Explosions and mobs.yml").exists())
		{
			egrieffile = new File(this.getDataFolder(), "Explosions and mobs.yml");
			InputStream defaultblocks = this.getResource("Explosions and mobs.yml");
			egrief = YamlConfiguration.loadConfiguration(defaultblocks);
			try 
			{
				egrief.save(egrieffile);
			} catch (Exception ex)
			{

			}
		}
		else
		{

			egrieffile = new File(this.getDataFolder(), "Explosions and mobs.yml");
		egrief = YamlConfiguration.loadConfiguration(egrieffile);
		}
		
		
	}
	
	
	public YamlConfiguration getEgrief()
	{
		if(egrief == null)
		{
			reloadEgrief();
		}
		
		return egrief;
	}
	
	
	public void saveEgrief()
	{
		if(egrief == null)
		{
			reloadEgrief();
		}
		try 
		{
			egrief.save(egrieffile);
		} catch (Exception ex)
		{

		}
	}
	
	

	
	public void log(String loc, String time, String str)
	{
		logfile = new File(this.getDataFolder(), "log.txt");
		log = YamlConfiguration.loadConfiguration(logfile);
		
		log.set(loc + "." + time, str);
		try {
			log.save(logfile);
		} catch (Exception ex) {
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void reloadFire()
	{
		if(!new File(this.getDataFolder(), "Fire.yml").exists())
		{
			firefile = new File(this.getDataFolder(), "Fire.yml");
			InputStream defaultblocks = this.getResource("Fire.yml");
			fire = YamlConfiguration.loadConfiguration(defaultblocks);
			try 
			{
				fire.save(firefile);
			} catch (Exception ex)
			{

			}
		}
		else
		{

		firefile = new File(this.getDataFolder(), "Fire.yml");
		fire = YamlConfiguration.loadConfiguration(firefile);
		}
		
		
	}
	
	
	public YamlConfiguration getFire()
	{
		if(fire == null)
		{
			reloadFire();
		}
		
		return fire;
	}
	
	
	public void saveFire()
	{
		if(fire == null)
		{
			reloadFire();
		}
		try 
		{
			fire.save(firefile);
		} catch (Exception ex)
		{

		}
	}

}
